package ar.com.gabriel.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.gabriel.cart.domain.model.Discount;
import ar.com.gabriel.cart.domain.model.emun.Category;

/**
 * @author Gabriel Gonzalez
 */
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findByCategory(Category category);
}