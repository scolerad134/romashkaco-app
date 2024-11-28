package com.romashkaco.controller;

import com.romashkaco.model.Product;
import com.romashkaco.service.ProductService;
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
 * Контроллер для работы с маппингом "/products".
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Получает список товаров.
     *
     * @return список товаров.
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Получает отдельный товар.
     *
     * @return отдельный товар.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.debug("GET-request, getProductById - start, id = {}", id);
        Product product = productService.getProductById(id);
        log.debug("GET-request, getProductById - end, id = {}", id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    /**
     * Создает товар.
     *
     * @return созданный товар.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        log.debug("POST-request, createProduct - start, product = {}", product);
        Product created = productService.createProduct(product);
        log.debug("POST-request, createProduct - end, product = {}", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Обновляет товар.
     *
     * @return обновленный товар.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
        @PathVariable Long id,
        @RequestBody @Valid Product product) {
        log.debug("PUT-request, updateProduct - start, id = {}, product = {}", id, product);
        Product updated = productService.updateProduct(id, product);
        log.debug("PUT-request, updateProduct - start, id = {}, product = {}", id, product);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Удаляет товар.
     *
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.debug("DELETE-request, updateProduct - start, id = {}", id);
        return productService.deleteProduct(id) ? ResponseEntity.noContent().build()
            : ResponseEntity.notFound().build();
    }
}
