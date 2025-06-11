package ar.com.gabriel.cart.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gabriel.cart.domain.model.AppUser;
import ar.com.gabriel.cart.domain.model.Cart;
import ar.com.gabriel.cart.domain.model.CartItem;
import ar.com.gabriel.cart.domain.model.Product;
import ar.com.gabriel.cart.domain.model.emun.CartStatus;
import ar.com.gabriel.cart.domain.spec.CartMustBePendingSpec;
import ar.com.gabriel.cart.domain.spec.ProductMustExistSpec;
import ar.com.gabriel.cart.dto.request.AddItemToCartRequestDTO;
import ar.com.gabriel.cart.dto.request.CheckoutCartRequestDTO;
import ar.com.gabriel.cart.dto.request.CreateCartRequestDTO;
import ar.com.gabriel.cart.dto.request.RemoveItemFromCartRequestDTO;
import ar.com.gabriel.cart.dto.request.UpdateCartItemQuantityRequestDTO;
import ar.com.gabriel.cart.dto.response.CartDTO;
import ar.com.gabriel.cart.dto.response.CartItemDTO;
import ar.com.gabriel.cart.dto.response.CheckoutResponseDTO;
import ar.com.gabriel.cart.event.CheckoutRequestedEvent;
import ar.com.gabriel.cart.helpers.CartMapper;
import ar.com.gabriel.cart.repository.AppUserRepository;
import ar.com.gabriel.cart.repository.CartRepository;
import ar.com.gabriel.cart.repository.ProductRepository;
import ar.com.gabriel.cart.security.JwtUtil;
import ar.com.gabriel.cart.service.CartService;
import io.micrometer.core.annotation.Timed;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final AppUserRepository appUserRepository;
    private final CartMapper cartMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final JwtUtil jwtUtil;

    private final CartMustBePendingSpec cartPendingSpec = new CartMustBePendingSpec();

    private static final Integer DEFAULT_QTY = 1;



    @Retryable(
        value = { DataIntegrityViolationException.class },
        maxAttempts = 3, backoff = @Backoff(delay = 100))
    @Override
    @Transactional
    @Timed(value = "api.cart.create", description = "Tiempo de creación de carrito")
    public CartDTO createCart(CreateCartRequestDTO req) {
        //Validamos que exista el usuario solicitante y que no tenga un carrito pendiente.
        String currentUser = jwtUtil.getUserName();
        AppUser user = appUserRepository.findByUsername(currentUser)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el usuario : " + currentUser));

        try {
            
            List<Cart> existingCarts = cartRepository.findByUser_IdAndStatus(user.getId(), CartStatus.CART_STATUS_PENDING);
            if (!existingCarts.isEmpty()) {
                return cartMapper.toCartDTO(existingCarts.get(0));
            }

            //Creamos un nuevo carrito si no existe uno pendiente.
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setStatus(CartStatus.CART_STATUS_PENDING);
            cartRepository.save(cart);
            return cartMapper.toCartDTO(cart); 

        } catch (DataIntegrityViolationException ex) {
            // Este error se puede dar por temas de concurrencia, si otra transacción creo un carrito para el mismo usuario.
            log.error("Error al crear el carrito: {}", ex.getMessage());
            List<Cart> fallback = cartRepository.findByUser_IdAndStatus(user.getId(), CartStatus.CART_STATUS_PENDING);
            if (!fallback.isEmpty()) {
                log.info("Se recuperó un carrito existente tras conflicto para el usuario: {}", currentUser);
                return cartMapper.toCartDTO(fallback.get(0));
            }
            throw new IllegalStateException("Conflicto al crear carrito y no se pudo recuperar el existente", ex);
        }
    }

    @Override
    @Retryable(
        value = ObjectOptimisticLockingFailureException.class,
        maxAttempts = 4,
        backoff = @Backoff(delay = 30, multiplier = 2))
    @Transactional
    @Timed(value = "api.cart.addItem", description = "Tiempo de additem al carrito")
    public CartDTO addItemToCart(AddItemToCartRequestDTO req) {
        // Validamos que exista el carrito y que este en estado pendiente.
        String currentUser = jwtUtil.getUserName();
    
        AppUser user = appUserRepository.findByUsername(currentUser)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el usuario : " + currentUser));

        Cart cart = cartRepository.findByIdAndUser_Id(
                UUID.fromString(req.getCartId()), user.getId())
        .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado para el usuario actual"));

        cartPendingSpec.check(cart,
                new IllegalStateException("No se pueden agregar productos a un carrito finalizado."));

        // Validamos que exista el producto.
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el producto con ID: " + req.getProductId()));

        // Si la cantidad es nula o menor a 1, se asigna 1 por defecto.
        int quantity = (req.getQuantity() == null || req.getQuantity() <= 0) ? DEFAULT_QTY : req.getQuantity();

        // Verificamos si el producto ya existe en el carrito.
        // Si existe, actualizamos la cantidad, si no, lo agregamos.
        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(req.getProductId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQty(existingItem.getQty() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQty(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }
        log.info("Actualizamos el carrito {}", req.getCartId());
        cartRepository.save(cart);

        return cartMapper.toCartDTO(cart); 
    }

    @Override
    @Retryable(
        value = ObjectOptimisticLockingFailureException.class,
        maxAttempts = 4,
        backoff = @Backoff(delay = 30, multiplier = 2))
    @Transactional
    @Timed(value = "api.cart.removeItem", description = "Tiempo de removeitem del carrito")
    public CartDTO removeItemFromCart(RemoveItemFromCartRequestDTO req) {
        //validamos que exista el carrito y que este en estado pendiente.
        String currentUser = jwtUtil.getUserName();
    
        AppUser user = appUserRepository.findByUsername(currentUser)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el usuario : " + currentUser));

        Cart cart = cartRepository.findByIdAndUser_Id(
                UUID.fromString(req.getCartId()), user.getId())
        .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado para el usuario actual"));

        cartPendingSpec.check(cart,
                new IllegalStateException("No se pueden eliminar productos de un carrito finalizado."));

        cart.getItems().removeIf(item -> item.getProduct().getId().toString().equals(req.getProductId()));
        cartRepository.save(cart);
        return cartMapper.toCartDTO(cart); 
    }

    @Override
    @Retryable(
        value = ObjectOptimisticLockingFailureException.class,
        maxAttempts = 4,
        backoff = @Backoff(delay = 30, multiplier = 2))
    @Transactional
    @Timed(value = "api.cart.decrementItem", description = "Tiempo de decrementitem en carrito")
    public CartDTO decrementItemFromCart(RemoveItemFromCartRequestDTO req) {
        //validamos que exista el carrito y que este en estado pendiente.
        String currentUser = jwtUtil.getUserName();
    
        AppUser user = appUserRepository.findByUsername(currentUser)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el usuario : " + currentUser));

        Cart cart = cartRepository.findByIdAndUser_Id(
                UUID.fromString(req.getCartId()), user.getId())
        .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado para el usuario actual"));

        cartPendingSpec.check(cart,
                new IllegalStateException("No se pueden eliminar productos de un carrito finalizado."));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().toString().equals(req.getProductId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el item en el carrito."));
        if (item.getQty() <= 1) {
            // Si la cantidad es 1 o menos, eliminamos el item del carrito.
            cart.getItems().remove(item);
        } else {
            // Decrementamos la cantidad del item.
            item.setQty(item.getQty() - 1);
        }
        cartRepository.save(cart);
        return cartMapper.toCartDTO(cart);
    }

    @Override
    @Retryable(
        value = ObjectOptimisticLockingFailureException.class,
        maxAttempts = 4,
        backoff = @Backoff(delay = 30, multiplier = 2))
    @Transactional
    @Timed(value = "api.cart.checkout", description = "Tiempo de checkout del carrito")
    public CheckoutResponseDTO checkoutCart(CheckoutCartRequestDTO cartRequest) {

        String currentUser = jwtUtil.getUserName();
    
        AppUser user = appUserRepository.findByUsername(currentUser)
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el usuario : " + currentUser));

        Cart cart = cartRepository.findByIdAndUser_Id(
                UUID.fromString(cartRequest.getCartId()), user.getId())
        .orElseThrow(() -> new EntityNotFoundException("Carrito no encontrado para el usuario actual"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío.");
        }

        cartPendingSpec.check(cart,
                new IllegalStateException("No se puede hacer checkout de productos de un carrito finalizado."));

        cart.setStatus(CartStatus.CART_STATUS_PROCESSING);
        cartRepository.save(cart);

        /* Disparamos el evento que sera escuchado por un listener, el cual luego invoca un job con el debido proceso async 
         * esto es para simular un proceso de checkout asincrono.
         * Soluciona un problema previo de concurrencia, ya que el checkout se procesa en un hilo separado.
         * Pudiendo la transaccion del metodo async leer datos desactualizados ya que la transaccion del checkout
         * no se ha completado.
         */
        log.info("Publicamos evento de checkout para el carrito ID: {}", cartRequest.getCartId());
        eventPublisher.publishEvent(
            new CheckoutRequestedEvent(cartRequest.getCartId(), cartRequest.getPaymentMethod(), cartRequest.getShippingAddress()));

        return new CheckoutResponseDTO("Estamos procesando su orden", cartRequest.getCartId());
    }

    @Override
    @Retryable(
        value = ObjectOptimisticLockingFailureException.class,
        maxAttempts = 4,
        backoff = @Backoff(delay = 30, multiplier = 2))
    @Transactional
    public CartDTO updateCartItemQuantity(UpdateCartItemQuantityRequestDTO req) {
        //Validamos la cantidad a modificar.
        if (req.getQty() == null || req.getQty() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        //Validamos que exista el carrito y que este en estado pendiente.
        Cart cart = cartRepository.findById(UUID.fromString(req.getCartId()))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el carrito con ID: " + req.getCartId()));

        cartPendingSpec.check(cart,
                new IllegalStateException("No se puede actualizar la cantidad de un producto a un carrito finalizado."));

        new ProductMustExistSpec(req.getProductId()).check(cart,
                new EntityNotFoundException("No se encontro el producto con ID en el carrito: " + req.getProductId())
        );

        //Validamos que exista el producto en el carrito y lo recuperamos, no deberia pasar que no exista.
        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().toString().equals(req.getProductId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el item en el carrito."));

        item.setQty(req.getQty());
        cartRepository.save(cart);
         return cartMapper.toCartDTO(cart); 
    }

    @Override
    @Transactional
    public CartDTO updateTotal(String cartId) {
        Cart cart = cartRepository.findById(UUID.fromString(cartId))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el carrito con ID: " + cartId));
         return cartMapper.toCartDTO(cart); 
    }

    @Override
    @Transactional(readOnly = true)
    public CartDTO getCartById(String cartId) {
        Cart cart = cartRepository.findById(UUID.fromString(cartId))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el carrito con ID: " + cartId));
        return cartMapper.toCartDTO(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartItemDTO> getCartItems(String cartId) {
        Cart cart = cartRepository.findById(UUID.fromString(cartId))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el carrito con ID: " + cartId));
        return cart.getItems().stream()
                .map(cartMapper::toCartItemDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void clearCart(String cartId) {
        Cart cart = cartRepository.findById(UUID.fromString(cartId))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el carrito con ID: " + cartId));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public double getCartTotal(String cartId) {
        return getCartItems(cartId).stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQty())
                .sum();
    }

    @Override
    @Transactional(readOnly = true)
    public int getCartItemCount(String cartId) {
        return getCartItems(cartId).stream()
                .mapToInt(CartItemDTO::getQty)
                .sum();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartDTO> getCartByUserId(String userId) {
        List<Cart> carts = cartRepository.findByUser_Id(UUID.fromString(userId));
        return carts.stream().map(cartMapper::toCartDTO).collect(Collectors.toList());
    }

    @Transactional
    public Cart checkoutCartTransaction(CheckoutCartRequestDTO cartRequest) {
        Cart cart = cartRepository.findById(UUID.fromString(cartRequest.getCartId()))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el carrito con ID: " + cartRequest.getCartId()));

        if (cart.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito esta vacio.");
        }

        cart.setStatus(CartStatus.CART_STATUS_FINISHED);
        return cartRepository.save(cart);
        
    }

    @Recover
    public CartDTO recover(DataIntegrityViolationException ex, CreateCartRequestDTO req) {
        String currentUser = jwtUtil.getUserName();
        AppUser user = appUserRepository.findByUsername(currentUser).orElseThrow();
        List<Cart> fallback = cartRepository.findByUser_IdAndStatus(user.getId(), CartStatus.CART_STATUS_PENDING);
        if (!fallback.isEmpty()) {
            return cartMapper.toCartDTO(fallback.get(0));
        }
        throw new IllegalStateException("No se pudo recuperar un carrito luego del error", ex);
    }

    @Recover
    public CartDTO recover(RuntimeException ex, AddItemToCartRequestDTO req) {
        log.error("RECOVERY: Falló agregar producto tras reintentos. Cart ID: {}, Error: {}", req.getCartId(), ex.getClass().getName() + ": " + ex.getMessage());
        throw new IllegalStateException("No se pudo agregar el item al carrito luego de múltiples intentos", ex);
    }
}
