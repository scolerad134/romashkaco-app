package com.romashkaco.service.impl;

import com.romashkaco.model.Product;
import com.romashkaco.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testGetAllProducts_NoFilter() {
        List<Product> products = Arrays.asList(
            new Product(1L, "Product 1", "Description 1", 10.0, true),
            new Product(2L, "Product 2", "Description 2", 20.0, false)
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts(null);

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetAllProducts_EmptyResult() {
        when(productRepository.findAll()).thenReturn(List.of());

        List<Product> result = productService.getAllProducts(null);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Exists() {
        Product product = new Product(1L, "Product 1", "Description 1", 10.0, true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product result = productService.getProductById(1L);

        assertNull(result);
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product(null, "Product 1", "Description 1", 10.0, true);
        Product savedProduct = new Product(1L, "Product 1", "Description 1", 10.0, true);
        when(productRepository.save(product)).thenReturn(savedProduct);

        Product result = productService.createProduct(product);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct_Exists() {
        Product existingProduct = new Product(1L, "Product 1", "Description 1", 10.0, true);
        Product updatedProduct = new Product(null, "Updated Product", "Updated Description", 15.0, false);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.updateProduct(1L, updatedProduct);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Updated Product", result.getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(updatedProduct);
    }

    @Test
    void testUpdateProduct_NotExists() {
        Product updatedProduct = new Product(null, "Updated Product", "Updated Description", 15.0, false);

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Product result = productService.updateProduct(1L, updatedProduct);

        assertNull(result);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_Exists() {
        Product product = new Product(1L, "Product 1", "Description 1", 10.0, true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(1L);

        boolean result = productService.deleteProduct(1L);

        assertTrue(result);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_NotExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = productService.deleteProduct(1L);

        assertFalse(result);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, never()).deleteById(1L);
    }
}
