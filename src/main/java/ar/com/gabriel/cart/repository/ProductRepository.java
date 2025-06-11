package ar.com.gabriel.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.com.gabriel.cart.domain.model.Product;
import ar.com.gabriel.cart.domain.model.emun.Category;

/**
 * @author Gabriel Gonzalez
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}