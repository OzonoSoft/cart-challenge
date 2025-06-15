package ar.com.ozonosoft.taller.controller;

import ar.com.ozonosoft.taller.dto.request.TipoClienteRequest;
import ar.com.ozonosoft.taller.dto.response.TipoClienteDTO;
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

@Tag(name = "Tipos Clientes", description = "Información sobre los Tipos de Clientes")
public interface TipoClienteController {

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las Tipos de Clientes se obtuvieron correctamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TipoClienteDTO.class)))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pudieron obtener los Tipos de Clientes", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener todos los Tipos de Clientes.")
    ResponseEntity<List<TipoClienteDTO>> findAllRegs();

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El Tipo de Cliente se obtuvo correctamente", content = @Content(schema = @Schema(implementation = TipoClienteDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pude obtener el Tipo de Cliente", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener un Tipo de Cliente por id.")
    ResponseEntity<TipoClienteDTO> findById(@PathVariable Integer id);

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "El Tipo de Cliente se obtuvo correctamente", content = @Content(schema = @Schema(implementation = TipoClienteDTO.class))),
		@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener un Tipo de Cliente por descripcion.")
	ResponseEntity<TipoClienteDTO> findByDescripcion(@PathVariable String descripcion);

	@Operation(summary = "Registrar Tipo Cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El tipo de cliente fue registrado correctamente", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	ResponseEntity<Message> create(@Valid @RequestBody TipoClienteRequest tipoClienteRequest);


}