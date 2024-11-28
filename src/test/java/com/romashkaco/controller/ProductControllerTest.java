package com.romashkaco.controller;

import com.romashkaco.model.Product;
import com.romashkaco.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductServiceImpl productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(100.0);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        List<Product> products = Arrays.asList(product);
        when(productService.getAllProducts()).thenReturn(products);

        // Act
        List<Product> result = productController.getAllProducts();

        // Assert
        assertEquals(1, result.size());
        assertEquals(product.getName(), result.get(0).getName());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Found() {
        // Arrange
        when(productService.getProductById(1L)).thenReturn(product);

        // Act
        ResponseEntity<Product> response = productController.getProductById(1L);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Product", response.getBody().getName());
        assertEquals(100.0, response.getBody().getPrice());
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        // Arrange
        when(productService.getProductById(1L)).thenReturn(null);

        // Act
        ResponseEntity<Product> response = productController.getProductById(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testCreateProduct() {
        // Arrange
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        // Act
        ResponseEntity<Product> response = productController.createProduct(product);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Product", response.getBody().getName());
        assertEquals(201, response.getStatusCodeValue());
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void testUpdateProduct_Found() {
        // Arrange
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(product);

        // Act
        ResponseEntity<Product> response = productController.updateProduct(1L, product);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Product", response.getBody().getName());
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Arrange
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(null);

        // Act
        ResponseEntity<Product> response = productController.updateProduct(1L, product);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void testDeleteProduct_Found() {
        // Arrange
        when(productService.deleteProduct(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = productController.deleteProduct(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Arrange
        when(productService.deleteProduct(1L)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = productController.deleteProduct(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(productService, times(1)).deleteProduct(1L);
    }
}
