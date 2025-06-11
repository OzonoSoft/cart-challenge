package ar.com.gabriel.cart.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.gabriel.cart.dto.response.ProductDTO;
import ar.com.gabriel.cart.dto.response.ProductResponseDTO;
import ar.com.gabriel.cart.helpers.Mapper;
import ar.com.gabriel.cart.repository.ProductRepository;
import ar.com.gabriel.cart.service.ProductService;

/**
 * @author Gabriel Gonzalez
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponseDTO findAll() {
        List<ProductDTO> products = productRepository.findAll().stream()
                .map(product -> Mapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
        ProductResponseDTO response = new ProductResponseDTO();
        response.setProductList(products);
        return response;
    }
}