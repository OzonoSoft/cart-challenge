package ar.com.gabriel.cart.service;

import ar.com.gabriel.cart.dto.request.UserRequest;
import ar.com.gabriel.cart.dto.response.LoginResponseDTO;
import ar.com.gabriel.cart.dto.response.UserDTO;

/**
 * @author Gabriel Gonzalez
 */
public interface UserService {
    UserDTO create(UserRequest user);
    UserDTO findUserById(String id);
    UserDTO findByUsername(String username);
    LoginResponseDTO login(String password, String codigo);
}