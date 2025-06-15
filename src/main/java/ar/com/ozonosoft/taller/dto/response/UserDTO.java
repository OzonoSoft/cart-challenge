package ar.com.ozonosoft.taller.dto.response;


import java.io.Serializable;

import ar.com.ozonosoft.taller.domain.model.emun.UserStatus;
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
