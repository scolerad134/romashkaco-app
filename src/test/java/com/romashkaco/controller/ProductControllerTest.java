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
        List<Product> products = Arrays.asList(product);
        when(productService.getAllProducts()).thenReturn(products);

        List<Product> result = productController.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(product.getName(), result.get(0).getName());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Found() {
        when(productService.getProductById(1L)).thenReturn(product);

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Product", response.getBody().getName());
        assertEquals(100.0, response.getBody().getPrice());
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        when(productService.getProductById(1L)).thenReturn(null);

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testCreateProduct() {
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Product", response.getBody().getName());
        assertEquals(201, response.getStatusCodeValue());
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void testUpdateProduct_Found() {
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.updateProduct(1L, product);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Product", response.getBody().getName());
        assertEquals(200, response.getStatusCodeValue());
        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void testUpdateProduct_NotFound() {
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(null);

        ResponseEntity<Product> response = productController.updateProduct(1L, product);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void testDeleteProduct_Found() {
        when(productService.deleteProduct(1L)).thenReturn(true);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(productService, times(1)).deleteProduct(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productService.deleteProduct(1L)).thenReturn(false);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(productService, times(1)).deleteProduct(1L);
    }
}
