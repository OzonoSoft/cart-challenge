package ar.com.gabriel.cart.domain.spec;

import ar.com.gabriel.cart.domain.model.Cart;
import ar.com.gabriel.cart.domain.model.emun.CartStatus;

/**
 * @author Gabriel Gonzalez
 */
public class CartMustBePendingSpec implements Specification<Cart> {
    @Override
    public boolean isSatisfiedBy(Cart cart) {
        return CartStatus.CART_STATUS_PENDING.equals(cart.getStatus());
    }
}