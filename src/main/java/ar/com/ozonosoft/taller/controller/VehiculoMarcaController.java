package ar.com.ozonosoft.taller.controller;


import ar.com.ozonosoft.taller.dto.request.VehiculoMarcaRequest;
import ar.com.ozonosoft.taller.dto.response.VehiculoMarcaDTO;
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

@Tag(name = "Marcas", description = "Información sobre Marcas de Vehiculos")
public interface VehiculoMarcaController {

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las marcas se obtuvieron correctamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VehiculoMarcaDTO.class)))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pudieron obtener las marcas", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener todos las marcas.")
    ResponseEntity<List<VehiculoMarcaDTO>> findAllRegs();

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El vehiculo se obtuvo correctamente", content = @Content(schema = @Schema(implementation = VehiculoMarcaDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pude obtener el vehiculo", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener una marca de vehiculo por id.")
    ResponseEntity<VehiculoMarcaDTO> findById(@PathVariable Integer id);

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "La marca se obtuvo correctamente", content = @Content(schema = @Schema(implementation = VehiculoMarcaDTO.class))),
		@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
		@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener marca por descripcion.")
	ResponseEntity<VehiculoMarcaDTO> findByDescripcion(@PathVariable String descripcion);

	@Operation(summary = "Registrar Marca.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La marca fue registrada correctamente", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    public ResponseEntity<Message> create(@Valid @RequestBody VehiculoMarcaRequest vehiculoMarcaRequest);


}