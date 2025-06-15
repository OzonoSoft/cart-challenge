package ar.com.ozonosoft.taller.service;

import ar.com.ozonosoft.taller.dto.request.UserRequest;
import ar.com.ozonosoft.taller.dto.response.LoginResponseDTO;
import ar.com.ozonosoft.taller.dto.response.UserDTO;

/**
 * @author Gabriel Gonzalez
 */
public interface UserService {
    UserDTO create(UserRequest user);
    UserDTO findUserById(String id);
    UserDTO findByUsername(String username);
    LoginResponseDTO login(String password, String codigo);
}