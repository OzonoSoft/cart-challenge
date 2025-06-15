package ar.com.ozonosoft.taller.controller;

import ar.com.ozonosoft.taller.dto.request.EmpresaRequest;
import ar.com.ozonosoft.taller.dto.response.EmpresaDTO;
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

@Tag(name = "Empresas", description = "Información sobre Empresas")
public interface EmpresaController {

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Las Empresas se obtuvieron correctamente", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpresaDTO.class)))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pudieron obtener las empresas", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener todos las empresas.")
    ResponseEntity<List<EmpresaDTO>> findAllRegs();

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La empresa fue consultada correctamente", content = @Content(schema = @Schema(implementation = EmpresaDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pude obtener la empresa", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener ua empresa por id.")
    ResponseEntity<EmpresaDTO> findById(@PathVariable String id);

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "La empresa fue consultada correctamente", content = @Content(schema = @Schema(implementation = EmpresaDTO.class))),
			@ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "404", description = "No se pude obtener la empresa", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
			@ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	@Operation(summary = "Obtener ua empresa por codigo.")
	ResponseEntity<EmpresaDTO> findByCodigo(@PathVariable String codigo);

	@Operation(summary = "Registrar Empresa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La empresa fue registrada correctamente", content = @Content(schema = @Schema(implementation = Message.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la respuesta", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    public ResponseEntity<Message> create(@Valid @RequestBody EmpresaRequest empresaRequest);


}