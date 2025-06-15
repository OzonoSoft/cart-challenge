package ar.com.ozonosoft.taller.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.ozonosoft.taller.dto.request.PeritajeRequest;
import ar.com.ozonosoft.taller.dto.response.PeritajeEmpresaResponseDTO;
import ar.com.ozonosoft.taller.dto.response.PeritajeResponseDTO;
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

/**
 * @author OzonoSoft
 */

@Tag(name = "Peritajes", description = "Información sobre Peritajes")
public interface PeritajeController {

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Los Peritajes se obtuvieron correctamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PeritajeResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pudieron obtener los peritajes", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener todos los peritajes.")
    ResponseEntity<List<PeritajeResponseDTO>> findAllRegs();

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El peritaje fue consultado correctamente", content = @Content(schema = @Schema(implementation = PeritajeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pude obtener el peritaje", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener un peritaje por id.")
    ResponseEntity<PeritajeResponseDTO> findById(@PathVariable String id);

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Los Peritajes se obtuvieron correctamente", content = @Content(schema = @Schema(implementation = PeritajeResponseDTO.class))),
		@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener todos los peritajes por estado.")
	List<PeritajeResponseDTO> findByEstadoId(@PathVariable Integer id);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Los Peritajes se obtuvieron correctamente", content = @Content(schema = @Schema(implementation = PeritajeResponseDTO.class))),
			@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener todos los peritajes por taller.")
	ResponseEntity<List<PeritajeResponseDTO>> findByTallerId(@PathVariable String tallerId);

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "Los Peritajes se obtuvieron correctamente", content = @Content(schema = @Schema(implementation = PeritajeResponseDTO.class))),
		@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener todos los peritajes por empresa.")
	List<PeritajeEmpresaResponseDTO> findByEmpresaId(@PathVariable String id);

	@Operation(summary = "Crear Peritaje.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El Peritaje fue generado correctamente", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	ResponseEntity<Message> create(@Valid @RequestBody PeritajeRequest peritajeRequest);


}