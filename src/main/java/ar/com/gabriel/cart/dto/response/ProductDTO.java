package ar.com.gabriel.cart.dto.response;


import ar.com.gabriel.cart.domain.model.emun.Category;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private Category category;
}
