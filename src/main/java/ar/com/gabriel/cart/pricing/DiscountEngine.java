package ar.com.gabriel.cart.pricing;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.com.gabriel.cart.domain.model.Cart;
import lombok.RequiredArgsConstructor;

/**
 * @author Gabriel Gonzalez
 */
@Component
@RequiredArgsConstructor
public class DiscountEngine {

    private final List<DiscountStrategy> strategies;

    public double totalDiscount(Cart cart) {
        return strategies.stream()
                         .mapToDouble(s -> s.apply(cart))
                         .sum();
    }
}
