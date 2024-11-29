package com.romashkaco.service.impl;

import com.romashkaco.dto.ProductRequestDto;
import com.romashkaco.model.Product;
import com.romashkaco.model.ProductSale;
import com.romashkaco.model.ProductSupply;
import com.romashkaco.repository.ProductRepository;
import com.romashkaco.repository.ProductSaleRepository;
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
class ProductSaleServiceImplTest {

    @Mock
    private ProductSaleRepository productSaleRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductSupplyRepository productSupplyRepository;

    @InjectMocks
    private ProductSaleServiceImpl productSaleService;

    private ProductRequestDto saleRequestDto;
    private Product product;
    private ProductSupply productSupply;
    private ProductSale productSale;

    @BeforeEach
    void setUp() {
        saleRequestDto = new ProductRequestDto();
        saleRequestDto.setProductId(1L);
        saleRequestDto.setDocumentName("Sale Document");
        saleRequestDto.setQuantity(5);

        product = new Product(1L, "Product 1", "Description 1", 100.0, true);

        productSupply = new ProductSupply();
        productSupply.setId(1L);
        productSupply.setProduct(product);
        productSupply.setQuantity(10);

        productSale = new ProductSale();
        productSale.setId(1L);
        productSale.setDocumentName("Sale Document");
        productSale.setProduct(product);
        productSale.setQuantity(5);
        productSale.setPurchasePrice(500.0);
    }

    @Test
    void testGetAllSaleProducts() {
        List<ProductSale> sales = List.of(
            new ProductSale(1L, "Sale 1", product, 5, 500.0),
            new ProductSale(2L, "Sale 2", product, 3, 300.0)
        );
        when(productSaleRepository.findAll()).thenReturn(sales);

        List<ProductSale> result = productSaleService.getAllSaleProducts();

        assertEquals(2, result.size());
        assertEquals("Sale 1", result.get(0).getDocumentName());
        verify(productSaleRepository, times(1)).findAll();
    }

    @Test
    void testGetSaleById_Exists() {
        when(productSaleRepository.findById(1L)).thenReturn(Optional.of(productSale));

        ProductSale result = productSaleService.getSaleById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productSaleRepository, times(1)).findById(1L);
    }

    @Test
    void testGetSaleById_NotExists() {
        when(productSaleRepository.findById(1L)).thenReturn(Optional.empty());

        ProductSale result = productSaleService.getSaleById(1L);

        assertNull(result);
        verify(productSaleRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateSale_ProductExistsInStock() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productSupplyRepository.findByProduct(product)).thenReturn(productSupply);
        when(productSaleRepository.save(any(ProductSale.class))).thenReturn(productSale);

        ProductSale result = productSaleService.createSale(saleRequestDto);

        assertNotNull(result);
        assertEquals("Sale Document", result.getDocumentName());
        assertEquals(500.0, result.getPurchasePrice());
        verify(productRepository, times(1)).findById(1L);
        verify(productSupplyRepository, times(1)).findByProduct(product);
        verify(productSaleRepository, times(1)).save(any(ProductSale.class));
    }

    @Test
    void testCreateSale_ProductNotInStock() {
        product.setInStock(false);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> productSaleService.createSale(saleRequestDto));

        assertEquals("Товара нет в наличии", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
        verify(productSupplyRepository, never()).findByProduct(any(Product.class));
        verify(productSaleRepository, never()).save(any(ProductSale.class));
    }

    @Test
    void testCreateSale_NotEnoughStock() {
        productSupply.setQuantity(3);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productSupplyRepository.findByProduct(product)).thenReturn(productSupply);

        Exception exception = assertThrows(IllegalArgumentException.class,
            () -> productSaleService.createSale(saleRequestDto));

        assertEquals("Недостаточно товара на складе", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
        verify(productSupplyRepository, times(1)).findByProduct(product);
        verify(productSaleRepository, never()).save(any(ProductSale.class));
    }

    @Test
    void testUpdateSale_Exists() {
        when(productSaleRepository.findById(1L)).thenReturn(Optional.of(productSale));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productSaleRepository.save(any(ProductSale.class))).thenReturn(productSale);

        ProductSale result = productSaleService.updateSale(1L, saleRequestDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Sale Document", result.getDocumentName());
        verify(productSaleRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).findById(1L);
        verify(productSaleRepository, times(1)).save(any(ProductSale.class));
    }

    @Test
    void testUpdateSale_NotExists() {
        when(productSaleRepository.findById(1L)).thenReturn(Optional.empty());

        ProductSale result = productSaleService.updateSale(1L, saleRequestDto);

        assertNull(result);
        verify(productSaleRepository, times(1)).findById(1L);
        verify(productRepository, never()).findById(anyLong());
        verify(productSaleRepository, never()).save(any(ProductSale.class));
    }

    @Test
    void testDeleteSale_Exists() {
        when(productSaleRepository.findById(1L)).thenReturn(Optional.of(productSale));
        doNothing().when(productSaleRepository).deleteById(1L);

        boolean result = productSaleService.deleteSale(1L);

        assertTrue(result);
        verify(productSaleRepository, times(1)).findById(1L);
        verify(productSaleRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteSale_NotExists() {
        when(productSaleRepository.findById(1L)).thenReturn(Optional.empty());

        boolean result = productSaleService.deleteSale(1L);

        assertFalse(result);
        verify(productSaleRepository, times(1)).findById(1L);
        verify(productSaleRepository, never()).deleteById(anyLong());
    }
}
