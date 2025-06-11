package ar.com.gabriel.cart.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

/**
 * @author Gabriel Gonzalez
 */
@Value
@Builder
public class CheckoutCartRequestDTO {
    @NotBlank(message = "Debe especificar el ID del carrito")
    String cartId;

    @NotBlank(message = "Debe especificar el método de pago")
    String paymentMethod;

    @NotBlank(message = "Debe especificar la dirección de envío")
    String shippingAddress;
}
