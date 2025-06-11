package ar.com.gabriel.cart.dto.response;

import java.util.List;

import ar.com.gabriel.cart.domain.model.emun.CartStatus;
import lombok.Data;

/**
 * @author Gabriel Gonzalez
 */
@Data
public class CartDTO {
    private String cartId;
    private List<CartItemDTO> items;
    private double total;
    private CartStatus status;
}
