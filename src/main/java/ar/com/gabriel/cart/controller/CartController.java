package ar.com.gabriel.cart.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.gabriel.cart.dto.request.AddItemToCartRequestDTO;
import ar.com.gabriel.cart.dto.request.CheckoutCartRequestDTO;
import ar.com.gabriel.cart.dto.request.CreateCartRequestDTO;
import ar.com.gabriel.cart.dto.request.RemoveItemFromCartRequestDTO;
import ar.com.gabriel.cart.dto.response.CartDTO;
import ar.com.gabriel.cart.dto.response.CheckoutResponseDTO;
import ar.com.gabriel.cart.exception.ErrorDetails;
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

@Tag(name = "Cart", description = "Interface de Carrito")
public interface CartController {

	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El carrito fue consultado correctamente", content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pude obtener el carrito", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la peticion", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener un carrito por ID.")
    ResponseEntity<CartDTO> findById(@PathVariable String id);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El carrito fue consultado correctamente", content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "401", description = "Token inválido", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "404", description = "No se pude obtener el carrito", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la peticion", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
    @Operation(summary = "Obtener un carrito por ID de usuario.")
    ResponseEntity<List<CartDTO>> getCartByUserId(@PathVariable String userId);
    
	@Operation(summary = "Crear Carrito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El Carrito fue creado correctamente", content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la peticion", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	ResponseEntity<CartDTO> create(@Valid @RequestBody CreateCartRequestDTO cartRequest);

    @Operation(summary = "Agregar item al Carrito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El item fue agregado correctamente", content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la peticion", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	ResponseEntity<CartDTO> addItemToCart(@Valid @RequestBody AddItemToCartRequestDTO addItemRequest);

    @Operation(summary = "Eliminar item del Carrito.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El item fue eliminado correctamente", content = @Content(schema = @Schema(implementation = CartDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la peticion", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	ResponseEntity<CartDTO>  removeItemFromCart(@Valid @RequestBody RemoveItemFromCartRequestDTO removeItemRequest);

    @Operation(summary = "Finalizar Compra.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La compra fue finalizada correctamente", content = @Content(schema = @Schema(implementation = CheckoutResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "La petición es inválida", content = @Content(schema = @Schema(implementation = ErrorDetails.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al procesar la peticion", content = @Content(schema = @Schema(implementation = ErrorDetails.class)))})
	ResponseEntity<CheckoutResponseDTO> checkout(@Valid @RequestBody CheckoutCartRequestDTO checkoutRequest);

}