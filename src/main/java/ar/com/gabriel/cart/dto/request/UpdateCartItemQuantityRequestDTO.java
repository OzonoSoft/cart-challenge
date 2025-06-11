package ar.com.gabriel.cart.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateCartItemQuantityRequestDTO {
    @NotBlank(message = "Debe especificar el ID del carrito")
    private String cartId;
    @NotBlank(message = "Debe especificar el ID del producto")
    private String productId;
    @NotBlank(message = "Debe especificar la cantidad")
    private Integer qty;
}
