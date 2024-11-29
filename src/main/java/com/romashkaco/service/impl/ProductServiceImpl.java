package com.romashkaco.service.impl;

import com.romashkaco.dto.ProductFilterDto;
import com.romashkaco.model.Product;
import com.romashkaco.repository.ProductRepository;
import com.romashkaco.service.ProductService;
import com.romashkaco.specification.ProductSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Product> getAllProducts(ProductFilterDto filterDto) {
        log.debug("getAllProducts - start");
        List<Product> products = filterDto == null ? productRepository.findAll() : this.getFilteredProducts(filterDto);
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
        Product saveProduct = productRepository.save(product);
        log.debug("createProduct - end, product = {}", product);
        return saveProduct;
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

    public List<Product> getFilteredProducts(ProductFilterDto filterDto) {
        log.debug("getFilteredProducts - start, filterDto = {}", filterDto);

        Specification<Product> spec = Specification.where(null);

        if (filterDto.name() != null && !filterDto.name().isEmpty()) {
            spec = spec.and(ProductSpecifications.nameContains(filterDto.name()));
        }
        if (filterDto.minPrice() != null) {
            spec = spec.and(ProductSpecifications.priceGreaterThanOrEqualTo(filterDto.minPrice()));
        }
        if (filterDto.maxPrice() != null) {
            spec = spec.and(ProductSpecifications.priceLessThanOrEqualTo(filterDto.maxPrice()));
        }
        if (filterDto.inStock() != null) {
            spec = spec.and(ProductSpecifications.isInStock(filterDto.inStock()));
        }

        int limit = filterDto.limit() == null ? productRepository.findAll().size() : filterDto.limit();

        String sortBy = filterDto.sortBy() != null ? filterDto.sortBy() : "id";
        String sortOrder = filterDto.sortOrder() != null ? filterDto.sortOrder() : "asc";

        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        List<Product> products = productRepository.findAll(spec, PageRequest.of(0, limit, sort)).getContent();

        log.debug("getFilteredProducts - end");

        return products;
    }

}
