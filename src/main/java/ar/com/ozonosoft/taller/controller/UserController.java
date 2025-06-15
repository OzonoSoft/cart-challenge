package ar.com.ozonosoft.taller.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.ozonosoft.taller.dto.request.UserRequest;
import ar.com.ozonosoft.taller.dto.request.UsuarioLoginRequest;
import ar.com.ozonosoft.taller.dto.response.LoginResponseDTO;
import ar.com.ozonosoft.taller.dto.response.UserDTO;
import ar.com.ozonosoft.taller.exception.ErrorDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * @author Gabriel Gonzalez
 */

@Tag(name = "Usuarios", description = "Interface de Usuarios")
public interface UserController {

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El usuario fue consultado correctamente", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pude obtener el usuario", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la peticion", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener un usuario por nombre de usuario.")
    ResponseEntity<UserDTO> findByUserName(@PathVariable String userName);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "El usuario fue logueado correctamente", content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "404", description = "No se pude obtener el usuario", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "500", description = "Error interno al procesar la peticion", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Login de usuario.")
	ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody UsuarioLoginRequest usuarioLoginRequest);
    

	@Operation(summary = "Registrar Usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El Usuario fue generado correctamente", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la peticion", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	ResponseEntity<UserDTO> create(@Valid @RequestBody UserRequest userRequest);


}