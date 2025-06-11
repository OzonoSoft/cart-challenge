package ar.com.gabriel.cart.dto.response;

import lombok.Data;

/**
 * @author Gabriel Gonzalez
 */
@Data
public class CartItemDTO {
    private Long id;
    private String cartId;
    private ProductDTO product;
    private Integer qty;
}
