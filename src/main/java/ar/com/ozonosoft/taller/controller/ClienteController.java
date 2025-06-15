package ar.com.ozonosoft.taller.controller;

import ar.com.ozonosoft.taller.dto.request.ClienteRequest;
import ar.com.ozonosoft.taller.dto.response.ClienteDTO;
import ar.com.ozonosoft.taller.exception.ErrorDetails;
import ar.com.ozonosoft.taller.helpers.Message;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author OzonoSoft
 */

@Tag(name = "Clientes", description = "Información sobre Clientes")
public interface ClienteController {

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Los clientes se obtuvieron correctamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ClienteDTO.class)))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pudieron obtener los clientes", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener todos los clientes.")
    ResponseEntity<List<ClienteDTO>> findAllRegs();

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El cliente fue consultado correctamente", content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pude obtener el cliente", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener un cliente por id.")
    ResponseEntity<ClienteDTO> findById(@PathVariable String id);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "La lista de clientes se ubtuvo correctamente", content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
			@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "404", description = "No se pude obtener la lista de clientes", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener clientes por taller.")
	ResponseEntity<List<ClienteDTO>> findByTallerId(@PathVariable String id);

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "El cliente fue consultado correctamente", content = @Content(schema = @Schema(implementation = ClienteDTO.class))),
		@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener cliente por Nro. documento.")
	ResponseEntity<ClienteDTO> findByNroDoc(@PathVariable Long nroDoc);

	@Operation(summary = "Registrar Cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El Cliente fue registrado correctamente", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    public ResponseEntity<Message> create(@Valid @RequestBody ClienteRequest clienteRequest);


}