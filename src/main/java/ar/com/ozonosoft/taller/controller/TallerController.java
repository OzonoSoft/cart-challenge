package ar.com.ozonosoft.taller.controller;

import ar.com.ozonosoft.taller.dto.request.TallerRequest;
import ar.com.ozonosoft.taller.dto.response.TallerDTO;
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

@Tag(name = "Talleres", description = "Información sobre Talleres")
public interface TallerController {

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Los Taleres se obtuvieron correctamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TallerDTO.class)))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pudieron obtener los talleres", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener todos los peritajes.")
    ResponseEntity<List<TallerDTO>> findAllRegs();

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El Taller fue consultado correctamente", content = @Content(schema = @Schema(implementation = TallerDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pude obtener el Taller", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener un taller por id.")
    ResponseEntity<TallerDTO> findById(@PathVariable String id);

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "El Taller fue consultado correctamente", content = @Content(schema = @Schema(implementation = TallerDTO.class))),
		@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener un Taller por codigo.")
	ResponseEntity<TallerDTO> findByCode(@PathVariable String code);

	@Operation(summary = "Registrar Taller.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El Taller fue generado correctamente", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    public ResponseEntity<Message> create(@Valid @RequestBody TallerRequest tallerRequest);


}