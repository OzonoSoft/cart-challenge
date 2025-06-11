package ar.com.gabriel.cart.dto.request;

import lombok.Builder;
import lombok.Value;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


/**
 * @author Gabriel Gonzalez
 */
@Value
@Builder
public class AddItemToCartRequestDTO {

    @NotBlank(message = "Debe especificar el ID del carrito")
    String cartId;

    @NotNull(message = "Debe especificar el ID del producto")
    Long productId;

    @NotNull(message = "Debe especificar la cantidad")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    Integer quantity;
}
