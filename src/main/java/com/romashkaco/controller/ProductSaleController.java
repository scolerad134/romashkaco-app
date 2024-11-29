package com.romashkaco.controller;

import com.romashkaco.dto.ProductRequestDto;
import com.romashkaco.model.ProductSale;
import com.romashkaco.model.ProductSupply;
import com.romashkaco.service.ProductSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для работы с маппингом "/sales".
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/sales")
public class ProductSaleController {

    private final ProductSaleService productSaleService;

    /**
     * Получает список продаж товаров.
     *
     * @return список продаж товаров.
     */
    @GetMapping
    public List<ProductSale> getAllSaleProducts() {
        log.debug("GET-request, getAllSupplyProducts - start");
        List<ProductSale> supplyProducts = productSaleService.getAllSaleProducts();
        log.debug("GET-request, getAllSupplyProducts - end");
        return supplyProducts;
    }

    /**
     * Создает продажу товара.
     *
     * @return продажа товара.
     */
    @PostMapping
    public ResponseEntity<ProductSale> createSale(@RequestBody @Valid ProductRequestDto productRequestDto) {
        log.debug("POST-request, createProduct - start, sale = {}", productRequestDto);
        ProductSale created = productSaleService.createSale(productRequestDto);
        log.debug("POST-request, createProduct - end, sale = {}", productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Обновляет продажу товара.
     *
     * @return продажа товара.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductSale> updateSale(@PathVariable Long id, @RequestBody @Valid ProductRequestDto productRequestDto) {
        log.debug("PUT-request, updateSale - start, id = {}, sale = {}", id, productRequestDto);
        ProductSale updated = productSaleService.updateSale(id, productRequestDto);
        log.debug("PUT-request, updateSale - start, id = {}, sale = {}", id, productRequestDto);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Удаляет продажу товара.
     *
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        log.debug("DELETE-request, deleteSale - start, id = {}", id);
        return productSaleService.deleteSale(id) ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

    /**
     * Получает продажу товара.
     *
     * @return продажа товара.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductSale> getSaleById(@PathVariable Long id) {
        log.debug("GET-request, getSaleById - start, id = {}", id);
        ProductSale productSale = productSaleService.getSaleById(id);
        log.debug("GET-request, getSaleById - end, id = {}", id);
        return productSale != null ? ResponseEntity.ok(productSale) : ResponseEntity.notFound().build();
    }
}
