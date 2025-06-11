package ar.com.gabriel.cart.controller;


import org.springframework.http.ResponseEntity;

import ar.com.gabriel.cart.dto.response.ProductResponseDTO;
import ar.com.gabriel.cart.exception.ErrorDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Gabriel Gonzalez
 */

@Tag(name = "Productos", description = "Interface de Productos")
public interface ProductController {

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El lsitado de productos fue consultado correctamente", content = @Content(schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la peticion", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Listar todos los productos.")
    ResponseEntity<ProductResponseDTO> findAll();
}