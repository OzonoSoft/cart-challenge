package ar.com.gabriel.cart.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gabriel.cart.domain.model.AppUser;
import ar.com.gabriel.cart.domain.model.Cart;
import ar.com.gabriel.cart.domain.model.Product;
import ar.com.gabriel.cart.domain.model.emun.CartStatus;
import ar.com.gabriel.cart.dto.request.AddItemToCartRequestDTO;
import ar.com.gabriel.cart.dto.request.CheckoutCartRequestDTO;
import ar.com.gabriel.cart.dto.request.CreateCartRequestDTO;
import ar.com.gabriel.cart.dto.request.RemoveItemFromCartRequestDTO;
import ar.com.gabriel.cart.dto.response.CartDTO;
import ar.com.gabriel.cart.dto.response.CheckoutResponseDTO;
import ar.com.gabriel.cart.event.CheckoutRequestedEvent;
import ar.com.gabriel.cart.helpers.CartMapper;
import ar.com.gabriel.cart.repository.AppUserRepository;
import ar.com.gabriel.cart.repository.CartRepository;
import ar.com.gabriel.cart.repository.ProductRepository;
import ar.com.gabriel.cart.security.SecurityUtils;
import ar.com.gabriel.cart.service.CartOperationTemplate;
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
    private final CartOperationTemplate cartOps;


    @Override
    @Timed(value = "api.cart.create", description = "Tiempo de creación de carrito")
    public CartDTO createCart(CreateCartRequestDTO req) {
        String username = SecurityUtils.getCurrentUsername();
        AppUser user = appUserRepository.findByUsername(username)
            .orElseThrow(() -> new EntityNotFoundException(
                "No se encontro el usuario: " + username));
        Cart cart = cartOps.createOrGetPendingCart(user);

        return cartMapper.toCartDTO(cart);
    }

    // Manejo de adición de un item al carrito
    @Override
    @Timed(value = "api.cart.addItem", description = "Tiempo de additem al carrito")
    public CartDTO addItemToCart(AddItemToCartRequestDTO req) {
        return cartOps.execute(req.getCartId(), cart -> {
            Product product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Producto no encontrado: " + req.getProductId()));
            int qty = (req.getQuantity() != null && req.getQuantity() > 0) ? req.getQuantity() : 1;
            cart.addOrIncrementItem(product, qty);
            return cartMapper.toCartDTO(cart);
        });
    }

    // Manejo de eliminación de un item del carrito
    @Override
    @Timed(value = "api.cart.removeItem", description = "Tiempo de removeItem del carrito")
    public CartDTO removeItemFromCart(RemoveItemFromCartRequestDTO req) {
        return cartOps.execute(req.getCartId(), cart -> {
            // Remueve todos los items cuyo productId coincida
            cart.getItems().removeIf(item ->
                item.getProduct().getId().toString().equals(req.getProductId())
            );
            return cartMapper.toCartDTO(cart);
        });
    }

    // Manejo de decremento de cantidad de un item en el carrito
    @Override
    @Timed(value = "api.cart.decrementItem", description = "Tiempo de decrementitem en carrito")
    public CartDTO decrementItemFromCart(RemoveItemFromCartRequestDTO req) {
        return cartOps.execute(req.getCartId(), cart -> {
            cart.decrementOrRemoveItem(UUID.fromString(req.getProductId()));
            return cartMapper.toCartDTO(cart);
        });
    }

    // Manejo de checkout del carrito
    @Override
    @Timed(value = "api.cart.checkout", description = "Tiempo de checkout del carrito")
    public CheckoutResponseDTO checkoutCart(CheckoutCartRequestDTO req) {
        return cartOps.execute(req.getCartId(), cart -> {
            if (cart.getItems().isEmpty()) {
                throw new IllegalStateException("El carrito está vacío.");
            }

            cart.setStatus(CartStatus.CART_STATUS_PROCESSING);

            /* Disparamos el evento que sera escuchado por un listener, el cual luego invoca un job con el debido proceso async 
            * esto es para simular un proceso de checkout asincrono.
            * Soluciona un problema previo de concurrencia, ya que el checkout se procesa en un hilo separado.
            * Pudiendo la transaccion del metodo async leer datos desactualizados ya que la transaccion del checkout
            * no se ha completado.
            */
            log.info("Publicamos evento de checkout para el carrito ID: {}", req.getCartId());
            eventPublisher.publishEvent(new CheckoutRequestedEvent(
                req.getCartId(),
                req.getPaymentMethod(),
                req.getShippingAddress()
            ));

            return new CheckoutResponseDTO("Estamos procesando su orden", req.getCartId());
        });
    }
    
    // Manejo de obtención de carrito por ID
    @Override
    @Transactional(readOnly = true)
    public CartDTO getCartById(String cartId) {
        Cart cart = cartRepository.findById(UUID.fromString(cartId))
                .orElseThrow(() -> new EntityNotFoundException("No se encontro el carrito con ID: " + cartId));
        return cartMapper.toCartDTO(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartDTO> getCartByUserId(String userId) {
        List<Cart> carts = cartRepository.findByUser_Id(UUID.fromString(userId));
        return carts.stream().map(cartMapper::toCartDTO).collect(Collectors.toList());
    }
}
