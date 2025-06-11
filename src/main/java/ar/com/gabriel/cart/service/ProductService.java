package ar.com.gabriel.cart.service;

import ar.com.gabriel.cart.dto.response.ProductResponseDTO;

/**
 * @author Gabriel Gonzalez
 */
public interface ProductService {
    ProductResponseDTO findAll();
}