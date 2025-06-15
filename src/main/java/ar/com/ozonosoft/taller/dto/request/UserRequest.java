package ar.com.ozonosoft.taller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Gabriel Gonzalez
 */
@Data
public class UserRequest {
	@NotBlank(message = "Debe especificar el nombre de usuario")
    private String userName;

    @NotBlank(message = "Debe especificar una contraseña")
    private String password;
}
