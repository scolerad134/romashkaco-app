package com.romashkaco.controller;

import com.romashkaco.dto.ProductRequestDto;
import com.romashkaco.model.ProductSupply;
import com.romashkaco.service.ProductSupplyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductSupplyControllerTest {

    @Mock
    private ProductSupplyService productSupplyService;

    @InjectMocks
    private ProductSupplyController productSupplyController;

    private ProductSupply productSupply;
    private ProductRequestDto supplyRequestDto;

    @BeforeEach
    void setUp() {
        productSupply = new ProductSupply();
        productSupply.setId(1L);
        productSupply.setDocumentName("Test Supply Document");
        productSupply.setQuantity(100);

        supplyRequestDto = new ProductRequestDto();
        supplyRequestDto.setDocumentName("Test Supply Document");
        supplyRequestDto.setProductId(1L);
        supplyRequestDto.setQuantity(100);
    }

    @Test
    void testGetAllSupplyProducts() {
        List<ProductSupply> supplies = List.of(
            new ProductSupply(1L, "Supply 1", null, 50),
            new ProductSupply(2L, "Supply 2", null, 100)
        );

        when(productSupplyService.getAllSupplyProducts()).thenReturn(supplies);

        List<ProductSupply> response = productSupplyController.getAllSupplyProducts();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Supply 1", response.get(0).getDocumentName());
        assertEquals(50, response.get(0).getQuantity());
        verify(productSupplyService, times(1)).getAllSupplyProducts();
    }

    @Test
    void testGetSupplyById_Found() {
        when(productSupplyService.getSupplyById(1L)).thenReturn(productSupply);

        ResponseEntity<ProductSupply> response = productSupplyController.getSupplyById(1L);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Supply Document", response.getBody().getDocumentName());
        assertEquals(100, response.getBody().getQuantity());
        assertEquals(200, response.getStatusCodeValue());
        verify(productSupplyService, times(1)).getSupplyById(1L);
    }

    @Test
    void testGetSupplyById_NotFound() {
        when(productSupplyService.getSupplyById(1L)).thenReturn(null);

        ResponseEntity<ProductSupply> response = productSupplyController.getSupplyById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productSupplyService, times(1)).getSupplyById(1L);
    }

    @Test
    void testCreateSupply() {
        when(productSupplyService.createSupply(any(ProductRequestDto.class))).thenReturn(productSupply);

        ResponseEntity<ProductSupply> response = productSupplyController.createSupply(supplyRequestDto);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Supply Document", response.getBody().getDocumentName());
        assertEquals(201, response.getStatusCodeValue());
        verify(productSupplyService, times(1)).createSupply(any(ProductRequestDto.class));
    }

    @Test
    void testUpdateSupply_Found() {
        when(productSupplyService.updateSupply(eq(1L), any(ProductRequestDto.class))).thenReturn(productSupply);

        ResponseEntity<ProductSupply> response = productSupplyController.updateSupply(1L, supplyRequestDto);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Supply Document", response.getBody().getDocumentName());
        assertEquals(200, response.getStatusCodeValue());
        verify(productSupplyService, times(1)).updateSupply(eq(1L), any(ProductRequestDto.class));
    }

    @Test
    void testUpdateSupply_NotFound() {
        when(productSupplyService.updateSupply(eq(1L), any(ProductRequestDto.class))).thenReturn(null);

        ResponseEntity<ProductSupply> response = productSupplyController.updateSupply(1L, supplyRequestDto);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productSupplyService, times(1)).updateSupply(eq(1L), any(ProductRequestDto.class));
    }

    @Test
    void testDeleteSupply_Found() {
        when(productSupplyService.deleteSupply(1L)).thenReturn(true);

        ResponseEntity<Void> response = productSupplyController.deleteSupply(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(productSupplyService, times(1)).deleteSupply(1L);
    }

    @Test
    void testDeleteSupply_NotFound() {
        when(productSupplyService.deleteSupply(1L)).thenReturn(false);

        ResponseEntity<Void> response = productSupplyController.deleteSupply(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(productSupplyService, times(1)).deleteSupply(1L);
    }
}
