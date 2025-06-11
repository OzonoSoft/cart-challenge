package ar.com.gabriel.cart.dto.response;


import java.io.Serializable;

import ar.com.gabriel.cart.domain.model.emun.UserStatus;
import lombok.Data;

/**
 * @author Gabriel Gonzalez
 */
@Data
public class UserDTO implements Serializable {
	private String id;
	private String username;
	private UserStatus status;
}
