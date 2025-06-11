package ar.com.gabriel.cart;

import ar.com.gabriel.cart.domain.model.Product;
import ar.com.gabriel.cart.dto.response.ProductDTO;
import ar.com.gabriel.cart.dto.response.ProductResponseDTO;
import ar.com.gabriel.cart.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ar.com.gabriel.cart.service.impl.ProductServiceImpl;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_ShouldReturnMappedProductList() {
        // Arrange
        Product product1 = new Product(1L, "Product 1", 100.0, null);
        Product product2 = new Product(2L, "Product 2", 200.0, null);
        List<Product> products = Arrays.asList(product1, product2);

        when(productRepository.findAll()).thenReturn(products);

        // Act
        ProductResponseDTO response = productService.findAll();

        // Assert
        assertNotNull(response);
        assertNotNull(response.getProductList());
        assertEquals(2, response.getProductList().size());

        ProductDTO dto1 = response.getProductList().get(0);
        assertEquals("Product 1", dto1.getName());
        assertEquals(100.0, dto1.getPrice());

        ProductDTO dto2 = response.getProductList().get(1);
        assertEquals("Product 2", dto2.getName());
        assertEquals(200.0, dto2.getPrice());

        verify(productRepository, times(1)).findAll();
    }
}
