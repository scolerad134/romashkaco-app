package com.romashkaco.controller;

import com.romashkaco.dto.ProductRequestDto;
import com.romashkaco.model.ProductSale;
import com.romashkaco.service.ProductSaleService;
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
class ProductSaleControllerTest {

    @Mock
    private ProductSaleService productSaleService;

    @InjectMocks
    private ProductSaleController productSaleController;

    private ProductSale productSale;
    private ProductRequestDto saleRequestDto;

    @BeforeEach
    void setUp() {
        productSale = new ProductSale();
        productSale.setId(1L);
        productSale.setDocumentName("Test Sale Document");
        productSale.setQuantity(5);

        saleRequestDto = new ProductRequestDto();
        saleRequestDto.setDocumentName("Test Sale Document");
        saleRequestDto.setProductId(1L);
        saleRequestDto.setQuantity(5);
    }

    @Test
    void testGetAllSaleProducts() {
        List<ProductSale> sales = List.of(
            new ProductSale(1L, "Sale 1", null, 10, 0.0),
            new ProductSale(2L, "Sale 2", null, 20, 0.0)
        );

        when(productSaleService.getAllSaleProducts()).thenReturn(sales);

        List<ProductSale> response = productSaleController.getAllSaleProducts();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Sale 1", response.get(0).getDocumentName());
        assertEquals(10, response.get(0).getQuantity());
        verify(productSaleService, times(1)).getAllSaleProducts();
    }

    @Test
    void testGetSaleById_Found() {
        when(productSaleService.getSaleById(1L)).thenReturn(productSale);

        ResponseEntity<ProductSale> response = productSaleController.getSaleById(1L);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Sale Document", response.getBody().getDocumentName());
        assertEquals(5, response.getBody().getQuantity());
        assertEquals(200, response.getStatusCodeValue());
        verify(productSaleService, times(1)).getSaleById(1L);
    }

    @Test
    void testGetSaleById_NotFound() {
        when(productSaleService.getSaleById(1L)).thenReturn(null);

        ResponseEntity<ProductSale> response = productSaleController.getSaleById(1L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productSaleService, times(1)).getSaleById(1L);
    }

    @Test
    void testCreateSale() {
        when(productSaleService.createSale(any(ProductRequestDto.class))).thenReturn(productSale);

        ResponseEntity<ProductSale> response = productSaleController.createSale(saleRequestDto);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Sale Document", response.getBody().getDocumentName());
        assertEquals(201, response.getStatusCodeValue());
        verify(productSaleService, times(1)).createSale(any(ProductRequestDto.class));
    }

    @Test
    void testUpdateSale_Found() {
        when(productSaleService.updateSale(eq(1L), any(ProductRequestDto.class))).thenReturn(productSale);

        ResponseEntity<ProductSale> response = productSaleController.updateSale(1L, saleRequestDto);

        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Sale Document", response.getBody().getDocumentName());
        assertEquals(200, response.getStatusCodeValue());
        verify(productSaleService, times(1)).updateSale(eq(1L), any(ProductRequestDto.class));
    }

    @Test
    void testUpdateSale_NotFound() {
        when(productSaleService.updateSale(eq(1L), any(ProductRequestDto.class))).thenReturn(null);

        ResponseEntity<ProductSale> response = productSaleController.updateSale(1L, saleRequestDto);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(productSaleService, times(1)).updateSale(eq(1L), any(ProductRequestDto.class));
    }

    @Test
    void testDeleteSale_Found() {
        when(productSaleService.deleteSale(1L)).thenReturn(true);

        ResponseEntity<Void> response = productSaleController.deleteSale(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(productSaleService, times(1)).deleteSale(1L);
    }

    @Test
    void testDeleteSale_NotFound() {
        when(productSaleService.deleteSale(1L)).thenReturn(false);

        ResponseEntity<Void> response = productSaleController.deleteSale(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(productSaleService, times(1)).deleteSale(1L);
    }
}
