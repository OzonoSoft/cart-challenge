package ar.com.gabriel.cart.controller.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gabriel.cart.controller.CartController;
import ar.com.gabriel.cart.dto.request.AddItemToCartRequestDTO;
import ar.com.gabriel.cart.dto.request.CheckoutCartRequestDTO;
import ar.com.gabriel.cart.dto.request.CreateCartRequestDTO;
import ar.com.gabriel.cart.dto.request.RemoveItemFromCartRequestDTO;
import ar.com.gabriel.cart.dto.response.CartDTO;
import ar.com.gabriel.cart.dto.response.CheckoutResponseDTO;
import ar.com.gabriel.cart.service.CartService;

/**
 * @author Gabriel Gonzalez
 */

@RestController
@RequestMapping("/api/v1/carts")
public class CartControllerImpl implements CartController {

    @Autowired
    private CartService cartService;

    @Override
    @GetMapping("{id}")
    public ResponseEntity<CartDTO> findById(@PathVariable String id) {
        return new ResponseEntity<>(this.cartService.getCartById(id), HttpStatus.OK);
    }

    @Override
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartDTO>> getCartByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(this.cartService.getCartByUserId(userId), HttpStatus.OK);
    }


    @Override
    @PostMapping("/create")
    public ResponseEntity<CartDTO> create(CreateCartRequestDTO cartRequest){
        CartDTO carrito = this.cartService.createCart(cartRequest);
        return new ResponseEntity<>(carrito, HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/add")
    public ResponseEntity<CartDTO> addItemToCart(AddItemToCartRequestDTO cartRequest){
        CartDTO carrito = this.cartService.addItemToCart(cartRequest);
        return new ResponseEntity<>(carrito, HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/remove")
    public ResponseEntity<CartDTO> removeItemFromCart(RemoveItemFromCartRequestDTO cartRequest){
        CartDTO carrito = this.cartService.removeItemFromCart(cartRequest);
        return new ResponseEntity<>(carrito, HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/checkout")
    public ResponseEntity<CheckoutResponseDTO> checkout(CheckoutCartRequestDTO cartRequest){
        CheckoutResponseDTO carrito = this.cartService.checkoutCart(cartRequest);
        return new ResponseEntity<>(carrito, HttpStatus.CREATED);
    }

}
