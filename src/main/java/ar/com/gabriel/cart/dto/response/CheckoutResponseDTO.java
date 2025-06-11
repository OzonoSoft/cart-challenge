package ar.com.gabriel.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckoutResponseDTO {
    private String message;
    private String cartId;
}
