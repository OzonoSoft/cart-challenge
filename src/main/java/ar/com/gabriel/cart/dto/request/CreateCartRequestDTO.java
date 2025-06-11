package ar.com.gabriel.cart.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Gabriel Gonzalez
 */
@Data
public class CreateCartRequestDTO {
	 @NotBlank(message = "Debe especificar el ID de usuario")
    private String userId;
}
