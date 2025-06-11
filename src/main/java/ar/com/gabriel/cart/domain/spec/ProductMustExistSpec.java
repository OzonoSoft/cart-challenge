package ar.com.gabriel.cart.domain.spec;

import ar.com.gabriel.cart.domain.model.Cart;

/**
 * @author Gabriel Gonzalez
 */
public class ProductMustExistSpec implements Specification<Cart> {

    private final Long productId;

    public ProductMustExistSpec(String productId) {
        this.productId = Long.valueOf(productId);
    }

    @Override
    public boolean isSatisfiedBy(Cart cart) {
        return cart.getItems().stream()
                   .anyMatch(i -> i.getProduct().getId().equals(productId));
    }
}