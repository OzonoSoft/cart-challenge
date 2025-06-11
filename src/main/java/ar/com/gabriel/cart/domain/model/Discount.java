package ar.com.gabriel.cart.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;

import ar.com.gabriel.cart.domain.model.emun.Category;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gabriel Gonzalez
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Category category;
    private Double percentage;

}