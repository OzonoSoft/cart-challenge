package ar.com.gabriel.cart.service;

import java.util.List;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import lombok.RequiredArgsConstructor;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gabriel.cart.domain.model.AppUser;
import ar.com.gabriel.cart.domain.model.Cart;
import ar.com.gabriel.cart.domain.model.emun.CartStatus;
import ar.com.gabriel.cart.domain.spec.CartMustBePendingSpec;
import ar.com.gabriel.cart.repository.AppUserRepository;
import ar.com.gabriel.cart.repository.CartRepository;
import ar.com.gabriel.cart.security.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartOperationTemplate {

    private final JwtUtil jwtUtil;
    private final CartRepository cartRepository;
    private final AppUserRepository userRepo;
    private final CartRepository cartRepo;
    private final CartMustBePendingSpec cartPendingSpec;

     @Retryable(
        value       = DataIntegrityViolationException.class,
        maxAttempts = 4,
        backoff     = @Backoff(delay = 100, multiplier = 2)
    )
    @Transactional
    public Cart createOrGetPendingCart(AppUser user) {
        List<Cart> existing = cartRepository
            .findByUser_IdAndStatus(user.getId(), CartStatus.CART_STATUS_PENDING);
        if (!existing.isEmpty()) {
            return existing.get(0);
        }
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setStatus(CartStatus.CART_STATUS_PENDING);
        return cartRepository.save(cart);
    }

    @Retryable(
        value       = ObjectOptimisticLockingFailureException.class,
        maxAttempts = 4,
        backoff     = @Backoff(delay = 30, multiplier = 2)
    )
    @Transactional
    public <R> R execute(String cartId, Function<Cart, ? extends R> action) {

        String username = jwtUtil.getUserName();

        UUID userId = userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Usuario no encontrado: " + username))
                .getId();

        Cart cart = cartRepo.findByIdAndUser_Id(UUID.fromString(cartId), userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Carrito no encontrado para el usuario actual"));

        cartPendingSpec.check(cart,
                new IllegalStateException("El carrito ya fue finalizado."));

        return action.apply(cart);
    }

    @Recover
    public <R> R recover(ObjectOptimisticLockingFailureException ex,
                            String cartId,
                            Function<Cart, ? extends R> action) {
            log.error("RECOVER: operación sobre carrito {} falló tras múltiples intentos", cartId, ex);
            throw new IllegalStateException(
                    "No se pudo completar la operación tras múltiples intentos", ex);
    }

    @Recover
    public Cart recoverCreate(DataIntegrityViolationException ex, AppUser user) {
        List<Cart> fallback = cartRepository
            .findByUser_IdAndStatus(user.getId(), CartStatus.CART_STATUS_PENDING);
        if (!fallback.isEmpty()) {
            return fallback.get(0);
        }
        throw new IllegalStateException(
            "Conflicto al crear carrito y no se pudo recuperar el existente", ex);
    }
}