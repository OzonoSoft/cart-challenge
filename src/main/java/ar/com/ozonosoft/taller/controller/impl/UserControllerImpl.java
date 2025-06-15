package ar.com.ozonosoft.taller.controller.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ozonosoft.taller.controller.UserController;
import ar.com.ozonosoft.taller.dto.request.UserRequest;
import ar.com.ozonosoft.taller.dto.request.UsuarioLoginRequest;
import ar.com.ozonosoft.taller.dto.response.LoginResponseDTO;
import ar.com.ozonosoft.taller.dto.response.UserDTO;
import ar.com.ozonosoft.taller.service.UserService;


/**
 * @author Gabriel Gonzalez
 */

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    @Override
    @GetMapping("{userName}")
    public ResponseEntity<UserDTO> findByUserName(String userName) {
        return new ResponseEntity<>(this.userService.findByUsername(userName), HttpStatus.OK);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(UsuarioLoginRequest req) {
        LoginResponseDTO body = userService.login(req.getPassword(), req.getCodigo());
        return ResponseEntity.ok(body);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<UserDTO> create(UserRequest userRequest){
        UserDTO user = this.userService.create(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

}
