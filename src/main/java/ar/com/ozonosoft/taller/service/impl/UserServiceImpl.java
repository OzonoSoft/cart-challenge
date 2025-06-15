package ar.com.ozonosoft.taller.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.ozonosoft.taller.constants.UserConstants;
import ar.com.ozonosoft.taller.domain.model.Usuario;
import ar.com.ozonosoft.taller.domain.model.emun.UserStatus;
import ar.com.ozonosoft.taller.dto.request.UserRequest;
import ar.com.ozonosoft.taller.dto.response.LoginResponseDTO;
import ar.com.ozonosoft.taller.dto.response.UserDTO;
import ar.com.ozonosoft.taller.exception.InternalServerErrorException;
import ar.com.ozonosoft.taller.exception.ResourceValidationException;
import ar.com.ozonosoft.taller.helpers.Mapper;
import ar.com.ozonosoft.taller.repository.UsuarioRepository;
import ar.com.ozonosoft.taller.security.JwtUtil;
import ar.com.ozonosoft.taller.service.UserService;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Gabriel Gonzalez
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
			log.warn("Usuario no encontrado: {}", username);
            throw new UsernameNotFoundException("Usuario no encontrado con username: " + username);
        }

        Usuario user = userOpt.get();

        return new User(user.getUsername(), user.getPassword(), List.of());
    }

    @Override
    @Timed(value = "api.user.login", description = "Tiempo de inicio de sesión de usuario")
    public LoginResponseDTO login(String password, String username) {
        Usuario user = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceValidationException(UserConstants.USER_NOT_FOUND));

        if (!user.getPassword().equals(password)){
			log.warn("Credenciales invalidas para el usuario: {}", username);
            throw new ResourceValidationException(UserConstants.ERROR_INVALID_USER_PASS);
        }

        UserDTO userDTO =  Mapper.map(user, UserDTO.class);

        String jwt = jwtUtil.generateToken(userDTO.getUsername());
        return new LoginResponseDTO(jwt, userDTO);
    
    }

    @Override
    public UserDTO findByUsername(String username){
		Usuario user = this.userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceValidationException(UserConstants.USER_NOT_FOUND));
        return Mapper.map(user, UserDTO.class);
    }

    @Override
    @Timed(value = "api.user.create", description = "Tiempo de creación de usuario")
    public UserDTO create(UserRequest req){
		Usuario result = null;

        Optional<Usuario> usu = this.userRepository.findByUsername(req.getUserName());
        if (usu.isPresent()){
			log.warn("El usuario: {} ya existe", req.getUserName());
            throw new ResourceValidationException(UserConstants.ERROR_USER_ALREADY_REGISTRED);
        }

        try {
            result = saveUser(req);
        } catch (Exception e) {
			log.warn("Error al crear el usuario: {}", req.getUserName());
            throw new InternalServerErrorException(UserConstants.ERROR_INTERNAL);
        }

        return Mapper.map(result, UserDTO.class);
    }

    @Transactional
    private Usuario saveUser(UserRequest req){
        Usuario usuario = new Usuario();

        usuario.setUsername(req.getUserName());
        usuario.setStatus(UserStatus.USER_STATUS_ACTIVE);
        usuario.setPassword(req.getPassword());

        return this.userRepository.save(usuario);
    }

    @Override
    public UserDTO findUserById(String id) {
        Optional<Usuario> user = this.userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ResourceValidationException(UserConstants.USER_NOT_FOUND);
        }
        return Mapper.map(user, UserDTO.class);
    }
}