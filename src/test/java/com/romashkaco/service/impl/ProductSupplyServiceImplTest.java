package com.romashkaco.service.impl;

import com.romashkaco.dto.ProductRequestDto;
import com.romashkaco.model.Product;
import com.romashkaco.model.ProductSupply;
import com.romashkaco.repository.ProductRepository;
import com.romashkaco.repository.ProductSupplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductSupplyServiceImplTest {

    @Mock
    private ProductSupplyRepository productSupplyRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductSupplyServiceImpl productSupplyService;

    private ProductRequestDto supplyRequestDto;
    private Product product;
    private ProductSupply productSupply;

    @BeforeEach
    void setUp() {
        supplyRequestDto = new ProductRequestDto();
        supplyRequestDto.setProductId(1L);
        supplyRequestDto.setDocumentName("Supply Document");
        supplyRequestDto.setQuantity(10);

        product = new Product(1L, "Product 1", "Description 1", 10.0, false);

        productSupply = new ProductSupply();
        productSupply.setId(1L);
        productSupply.setDocumentName("Supply Document");
        productSupply.setProduct(product);
        productSupply.setQuantity(10);
    }

    @Test
    void testGetAllSupplyProducts() {
        List<ProductSupply> supplies = List.of(
            new ProductSupply(1L, "Supply 1", product, 10),
            new ProductSupply(2L, "Supply 2", product, 20)
        );
        when(productSupplyRepository.findAll()).thenReturn(supplies);

        List<ProductSupply> result = productSupplyService.getAllSupplyProducts();

        assertEquals(2, result.size());
        assertEquals("Supply 1", result.get(0).getDocumentName());
        verify(productSupplyRepository, times(1)).findAll();
    }

    @Test
    void testGetSupplyById_Exists() {
        when(productSupplyRepository.findById(1L)).thenReturn(Optional.of(productSupply));

        ProductSupply result = productSupplyService.getSupplyById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productSupplyRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSupplyById_NotExists() {
        when(productSupplyRepository.findById(1L)).thenReturn(Optional.empty());

        ProductSupply result = productSupplyService.getSupplyById(1L);

        assertNull(result);
        verify(productSupplyRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateSupply_ProductExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productSupplyRepository.save(any(ProductSupply.class))).thenReturn(productSupply);

        ProductSupply result = productSupplyService.createSupply(supplyRequestDto);

        assertNotNull(result);
        assertEquals("Supply Document", result.getDocumentName());
        verify(productRepository, times(1)).findById(1L);
        verify(productSupplyRepository, times(1)).save(any(ProductSupply.class));
    }

    @Test
    void testCreateSupply_ProductNotExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> productSupplyService.createSupply(supplyRequestDto));

        assertEquals("Товар не найден", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
        verify(productSupplyRepository, never()).save(any(ProductSupply.class));
    }

    @Test
    void testUpdateSupply_Exists() {
        when(productSupplyRepository.findById(1L)).thenReturn(Optional.of(productSupply));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productSupplyRepository.save(any(ProductSupply.class))).thenReturn(productSupply);

        ProductSupply result = productSupplyService.updateSupply(1L, supplyRequestDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productSupplyRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(1L);
        verify(productSupplyRepository, times(1)).save(any(ProductSupply.class));
    }

    @Test
    void testUpdateSupply_NotExists() {
        when(productSupplyRepository.findById(1L)).thenReturn(Optional.empty());

        ProductSupply result = productSupplyService.updateSupply(1L, supplyRequestDto);

        assertNull(result);
        verify(productSupplyRepository, times(1)).findById(1L);
        verify(productRepository, never()).findById(anyLong());
        verify(productSupplyRepository, never()).save(any(ProductSupply.class));
    }

    @Test
    void testDeleteSupply_Exists() {
        when(productSupplyRepository.findById(1L)).thenReturn(Optional.of(productSupply));
        doNothing().when(productSupplyRepository).deleteById(1L);

        boolean result = productSupplyService.deleteSupply(1L);

        assertTrue(result);
        verify(productSupplyRepository, times(1)).findById(1L);
        verify(productSupplyRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSupply_NotExists() {
        when(productSupplyRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = productSupplyService.deleteSupply(1L);

        assertFalse(result);
        verify(productSupplyRepository, times(1)).findById(1L);
        verify(productSupplyRepository, never()).deleteById(anyLong());
    }
}
