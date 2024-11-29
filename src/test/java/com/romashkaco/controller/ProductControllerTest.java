package com.romashkaco.controller;

import com.romashkaco.dto.ProductFilterDto;
import com.romashkaco.model.Product;
import com.romashkaco.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
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
    void testGetAllProducts_NoFilter() {
        List<Product> products = List.of(
            new Product(1L, "Product 1", "Description 1", 100.0, true),
            new Product(2L, "Product 2", "Description 2", 200.0, true)
        );

        when(productService.getAllProducts(null)).thenReturn(products);

        List<Product> response = productController.getAllProducts(null);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Product 1", response.get(0).getName());
        assertEquals("Description 1", response.get(0).getDescription());
        assertEquals(true, response.get(0).getInStock());
        assertEquals(100.0, response.get(0).getPrice());
        assertEquals("Product 2", response.get(1).getName());
        assertEquals("Description 2", response.get(1).getDescription());
        assertEquals(true, response.get(1).getInStock());
        assertEquals(200.0, response.get(1).getPrice());
        verify(productService, times(1)).getAllProducts(null);
    }

    @Test
    void testGetAllProducts_WithFilter() {
        ProductFilterDto filterDto = new ProductFilterDto(null, 750.0, 1500.0,
            null, null, null, null);

        List<Product> products = List.of(
            new Product(1L, "Laptop", "Description 1", 1500.0, true),
            new Product(2L, "Smartphone", "Description 2", 800.0, true)
        );

        when(productService.getAllProducts(filterDto)).thenReturn(products);

        List<Product> response = productController.getAllProducts(filterDto);

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Laptop", response.get(0).getName());
        assertEquals("Description 1", response.get(0).getDescription());
        assertEquals(true, response.get(0).getInStock());
        assertEquals(1500.0, response.get(0).getPrice());
        assertEquals("Smartphone", response.get(1).getName());
        assertEquals("Description 2", response.get(1).getDescription());
        assertEquals(true, response.get(1).getInStock());
        assertEquals(800.0, response.get(1).getPrice());
        verify(productService, times(1)).getAllProducts(filterDto);
    }

    @Test
    void testGetAllProducts_EmptyResult() {
        when(productService.getAllProducts(null)).thenReturn(new ArrayList<>());

        List<Product> response = productController.getAllProducts(null);

        assertNotNull(response);
        assertTrue(response.isEmpty());
        verify(productService, times(1)).getAllProducts(null);
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
