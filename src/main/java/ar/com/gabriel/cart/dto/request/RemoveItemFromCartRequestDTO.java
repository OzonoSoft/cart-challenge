package ar.com.gabriel.cart.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Gabriel Gonzalez
 */
@Data
public class RemoveItemFromCartRequestDTO {
    @NotBlank(message = "Debe especificar el ID del carrito")
    private String cartId;

    @NotBlank(message = "Debe especificar el ID del producto")
    private String productId;
}
