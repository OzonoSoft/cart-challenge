package ar.com.gabriel.cart.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Gabriel Gonzalez
 */
@Data
@Setter
@Getter
public class UsuarioLoginRequest {
	@NotBlank(message = "Debe especificar el codigo de usuario")
	private String codigo;
    @NotBlank(message = "Debe especificar una contraseña")
	private String password;
}
