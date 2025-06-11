package ar.com.gabriel.cart.pricing;

import ar.com.gabriel.cart.domain.model.Cart;

/**
 * @author Gabriel Gonzalez
 */
public interface DiscountStrategy {
    double apply(Cart cart);
}
