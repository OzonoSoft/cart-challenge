package ar.com.ozonosoft.taller.controller;

import ar.com.ozonosoft.taller.dto.request.CreateTurnoRequest;
import ar.com.ozonosoft.taller.dto.response.TurnoResponseDTO;
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

@Tag(name = "Turnos", description = "Información sobre Turnos")
public interface TurnoController {

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Los turnos se obtuvieron correctamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TurnoResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pudieron obtener los turnos", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener todos los turnos.")
    ResponseEntity<List<TurnoResponseDTO>> findAllRegs();

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El turno fue consultado correctamente", content = @Content(schema = @Schema(implementation = TurnoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pude obtener el turno", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener un turno por id.")
    ResponseEntity<TurnoResponseDTO> findById(@PathVariable String id);

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Los Peritajes se obtuvieron correctamente", content = @Content(schema = @Schema(implementation = TurnoResponseDTO.class))),
		@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener todos los turnos por estado y taller.")
	List<TurnoResponseDTO> findByEstadoAndTallerId(@PathVariable Integer id, @PathVariable String tallerId);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Los Peritajes se obtuvieron correctamente", content = @Content(schema = @Schema(implementation = TurnoResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener todos los turnos por taller.")
	ResponseEntity<List<TurnoResponseDTO>> findByTallerId(@PathVariable String tallerId);

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Los Peritajes se obtuvieron correctamente", content = @Content(schema = @Schema(implementation = TurnoResponseDTO.class))),
		@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener todos los peritajes por cliente.")
	List<TurnoResponseDTO> findByClienteId(@PathVariable String id);

	@Operation(summary = "Crear Turno.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El Turno fue generado correctamente", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    public ResponseEntity<Message> create(@Valid @RequestBody CreateTurnoRequest createTurnoRequest);


}