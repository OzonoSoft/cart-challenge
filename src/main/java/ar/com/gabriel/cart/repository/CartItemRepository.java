package ar.com.gabriel.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.gabriel.cart.domain.model.Cart;
import ar.com.gabriel.cart.domain.model.CartItem;
import ar.com.gabriel.cart.domain.model.Product;

/**
 * @author Gabriel Gonzalez
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);
    CartItem findByCartAndProduct(Cart cart, Product product);
}