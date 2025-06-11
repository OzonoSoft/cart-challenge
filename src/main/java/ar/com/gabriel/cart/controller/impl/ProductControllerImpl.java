package ar.com.gabriel.cart.controller.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.gabriel.cart.controller.ProductController;
import ar.com.gabriel.cart.dto.response.ProductResponseDTO;
import ar.com.gabriel.cart.service.ProductService;

/**
 * @author Gabriel Gonzalez
 */

@RestController
@RequestMapping("/api/v1/products")
public class ProductControllerImpl implements ProductController {

    @Autowired
    private ProductService productService;

    @Override
    @GetMapping()
    public ResponseEntity<ProductResponseDTO> findAll() {
        return new ResponseEntity<>(this.productService.findAll(), HttpStatus.OK);
    }

}
