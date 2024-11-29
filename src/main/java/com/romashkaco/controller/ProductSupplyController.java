package com.romashkaco.controller;

import com.romashkaco.dto.ProductRequestDto;
import com.romashkaco.model.ProductSupply;
import com.romashkaco.service.ProductSupplyService;
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
 * Контроллер для работы с маппингом "/supplies".
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/supplies")
public class ProductSupplyController {

    private final ProductSupplyService productSupplyService;

    /**
     * Получает список поставок товаров.
     *
     * @return список поставок товаров.
     */
    @GetMapping
    public List<ProductSupply> getAllSupplyProducts() {
        log.debug("GET-request, getAllSupplyProducts - start");
        List<ProductSupply> supplyProducts = productSupplyService.getAllSupplyProducts();
        log.debug("GET-request, getAllSupplyProducts - end");
        return supplyProducts;
    }

    /**
     * Создает поставку товара.
     *
     * @return поставка товара.
     */
    @PostMapping
    public ResponseEntity<ProductSupply> createSupply(@RequestBody @Valid ProductRequestDto supplyRequestDto) {
        log.debug("POST-request, createSupply - start, supply = {}", supplyRequestDto);
        ProductSupply created = productSupplyService.createSupply(supplyRequestDto);
        log.debug("POST-request, createSupply - start, supply = {}", supplyRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Обновляет поставку товара.
     *
     * @return поставка товара.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductSupply> updateSupply(@PathVariable Long id, @RequestBody ProductRequestDto supply) {
        log.debug("PUT-request, updateSupply - start, id = {}, supply = {}", id, supply);
        ProductSupply updated = productSupplyService.updateSupply(id, supply);
        log.debug("PUT-request, updateSupply - start, id = {}, supply = {}", id, supply);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Удаляет поставку товара.
     *
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupply(@PathVariable Long id) {
        log.debug("DELETE-request, deleteSupply - start, id = {}", id);
        return productSupplyService.deleteSupply(id) ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }

    /**
     * Получает поставку товара.
     *
     * @return поставка товара.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductSupply> getSupplyById(@PathVariable Long id) {
        log.debug("GET-request, getSupplyById - start, id = {}", id);
        ProductSupply productSupply = productSupplyService.getSupplyById(id);
        log.debug("GET-request, getSupplyById - end, id = {}", id);
        return productSupply != null ? ResponseEntity.ok(productSupply) : ResponseEntity.notFound().build();
    }
}
