package ar.com.gabriel.cart.pricing;

import org.springframework.stereotype.Component;

import ar.com.gabriel.cart.domain.model.Cart;

/**
 * @author Gabriel Gonzalez
 */
@Component
public class TenPercentOver200 implements DiscountStrategy {
    @Override
    public double apply(Cart cart) {
        double subtotal = cart.getItems()
                              .stream()
                              .mapToDouble(i -> i.getProduct().getPrice() * i.getQty())
                              .sum();
        return subtotal >= 200 ? subtotal * 0.10 : 0;
    }
}