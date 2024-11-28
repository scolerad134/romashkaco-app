package com.romashkaco.service.impl;

import com.romashkaco.model.Product;
import com.romashkaco.repository.ProductRepository;
import com.romashkaco.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * Получает список товаров.
     *
     * @return список товаров.
     */
    @Override
    public List<Product> getAllProducts() {
        log.debug("getAllProducts - start");
        List<Product> products = productRepository.findAll();
        log.debug("getAllProducts - end, products = {}", products);
        return products;
    }

    /**
     * Получает отдельный товар.
     *
     * @return отдельный товар.
     */
    @Override
    public Product getProductById(Long id) {
        log.debug("getProductById - start, id = {}", id);
        Product product = productRepository.findById(id).orElse(null);
        log.debug("getProductById - end, id = {}", id);
        return product;
    }

    /**
     * Создает товар.
     *
     * @return созданный товар.
     */
    @Override
    public Product createProduct(Product product) {
        log.debug("createProduct - start, product = {}", product);
        productRepository.save(product);
        log.debug("createProduct - end, product = {}", product);
        return product;
    }

    /**
     * Обновляет товар.
     *
     * @return обновленный товар.
     */
    @Override
    public Product updateProduct(Long id, Product product) {

        log.debug("updateProduct - start, id = {}, product = {}", id, product);

        if (productRepository.findById(id).isPresent()) {
            product.setId(id);
            productRepository.save(product);

            log.debug("updateProduct - end, id = {}, product = {}", id, product);

            return product;
        }

        log.debug("updateProduct - end, id = {}, product = {}", id, product);

        return null;
    }

    /**
     * Удаляет товар.
     *
     */
    @Override
    public Boolean deleteProduct(Long id) {

        log.debug("deleteProduct - start, id = {}", id);

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            productRepository.deleteById(id);
            log.debug("deleteProduct - end, id = {}", id);
            return true;
        }

        log.debug("deleteProduct - end, id = {}", id);

        return false;
    }
}
