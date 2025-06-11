package ar.com.gabriel.cart.dto.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO implements Serializable {
    private String token;
    private UserDTO user;
}