package ar.com.gabriel.cart.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private List<ProductDTO> productList;
}
