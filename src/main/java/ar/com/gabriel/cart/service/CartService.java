package ar.com.gabriel.cart.service;

import java.util.List;

import ar.com.gabriel.cart.dto.request.AddItemToCartRequestDTO;
import ar.com.gabriel.cart.dto.request.CheckoutCartRequestDTO;
import ar.com.gabriel.cart.dto.request.CreateCartRequestDTO;
import ar.com.gabriel.cart.dto.request.RemoveItemFromCartRequestDTO;
import ar.com.gabriel.cart.dto.request.UpdateCartItemQuantityRequestDTO;
import ar.com.gabriel.cart.dto.response.CartDTO;
import ar.com.gabriel.cart.dto.response.CartItemDTO;
import ar.com.gabriel.cart.dto.response.CheckoutResponseDTO;

public interface CartService {

    CartDTO createCart(CreateCartRequestDTO request);
    CartDTO addItemToCart(AddItemToCartRequestDTO req);
    //Elimina la totalidad del item del carrito, no solo la cantidad
    CartDTO removeItemFromCart(RemoveItemFromCartRequestDTO req);
    //Decrementa la cantidad de un item en el carrito, si la cantidad llega a 0, lo elimina
    CartDTO decrementItemFromCart(RemoveItemFromCartRequestDTO req); 
    List<CartItemDTO> getCartItems(String cartId);
    List<CartDTO> getCartByUserId(String userId);
    CheckoutResponseDTO checkoutCart(CheckoutCartRequestDTO cartRequest); //Hasta aqui, lo solicitado en el challenge

    CartDTO updateCartItemQuantity(UpdateCartItemQuantityRequestDTO req);
    CartDTO updateTotal(String cartId);

    CartDTO getCartById(String cartId);
    
    void clearCart(String cartId);
    double getCartTotal(String cartId);
    int getCartItemCount(String cartId);
}
