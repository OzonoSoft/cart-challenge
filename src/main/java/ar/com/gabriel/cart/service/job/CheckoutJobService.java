package ar.com.gabriel.cart.service.job;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.gabriel.cart.domain.model.Cart;
import ar.com.gabriel.cart.domain.model.emun.CartStatus;
import ar.com.gabriel.cart.event.CheckoutRequestedEvent;
import ar.com.gabriel.cart.pricing.DiscountEngine;
import ar.com.gabriel.cart.repository.CartRepository;
import ar.com.gabriel.cart.service.impl.PaymentGatewaySimulator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutJobService {

    private final CartRepository cartRepository;
    private final DiscountEngine discountEngine;

    // Este servicio se encarga de procesar el checkout de un carrito.
    // Asumo que el proceso de checkout consta de algunas validaciones previas
    // y luego realizar el calculo del total, aplicar descuentos y procesar el pago.
    // El método process es transaccional, lo que significa que si algo falla,
    // se revertirán todos los cambios y se podra re-procesar.
    @Transactional
    public void process(CheckoutRequestedEvent evt) {

        Cart cart = cartRepository.findByIdForCheckout(UUID.fromString(evt.cartId()))
            .orElseThrow(() -> new EntityNotFoundException(
            "No se encontro el carrito ID: " + evt.cartId()));

        // Evitar procesar dos veces si el evento se duplica
        if (cart.getStatus() != CartStatus.CART_STATUS_PROCESSING) {
            log.warn("El carrito {} no está en estado PROCESSING. Estado actual: {}", cart.getId(), cart.getStatus());
            return;
        }

        // Calculamos el total, los descuentos y aplicamos.
        double subtotal = cart.getItems().stream()
                              .mapToDouble(i -> i.getProduct().getPrice() * i.getQty())
                              .sum();
        double discount = discountEngine.totalDiscount(cart);
        double total    = subtotal - discount;

        //Simulamos el pago
        applyTotals(cart);
        boolean ok = PaymentGatewaySimulator.charge(evt.paymentMethod(), cart.getTotal());

        if (!ok) {
            log.error("Fallo el cobro del carrito {}, método de pago: {}", cart.getId(), evt.paymentMethod());
            cart.setStatus(CartStatus.CART_STATUS_PENDING); 
            cartRepository.save(cart);
            return;
        }

        cart.setSubtotal(subtotal);
        cart.setDiscount(discount);
        cart.setTotal(total);
        cart.setStatus(CartStatus.CART_STATUS_FINISHED);
        cartRepository.save(cart);
    }

    private void applyTotals(Cart cart) {
        double subtotal = cart.getItems().stream()
            .mapToDouble(i -> i.getProduct().getPrice() * i.getQty())
            .sum();
        double discount = discountEngine.totalDiscount(cart);
        double total = subtotal - discount;

        cart.setSubtotal(subtotal);
        cart.setDiscount(discount);
        cart.setTotal(total);
    }
}