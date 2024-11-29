package com.romashkaco.service.impl;

import com.romashkaco.dto.ProductRequestDto;
import com.romashkaco.model.Product;
import com.romashkaco.model.ProductSupply;
import com.romashkaco.repository.ProductRepository;
import com.romashkaco.repository.ProductSupplyRepository;
import com.romashkaco.service.ProductSupplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSupplyServiceImpl implements ProductSupplyService {

    private final ProductSupplyRepository productSupplyRepository;
    private final ProductRepository productRepository;

    /**
     * Получает список поставок товаров.
     *
     * @return список поставок товаров.
     */
    @Override
    public List<ProductSupply> getAllSupplyProducts() {
        log.debug("getAllSupplyProducts - start");
        List<ProductSupply> products = productSupplyRepository.findAll();
        log.debug("getAllSupplyProducts - end, products = {}", products);
        return products;
    }

    /**
     * Создает поставку товара.
     *
     * @return созданная поставка товара.
     */
    @Override
    public ProductSupply createSupply(ProductRequestDto supply) {
        log.debug("createSupply - start, supply = {}", supply);

        Product product = productRepository.findById(supply.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));

        if (!product.getInStock()) {
            product.setInStock(true);
            productRepository.save(product);
        }

        ProductSupply productSupply = this.toProductSupply(supply, product);

        return productSupplyRepository.save(productSupply);
    }

    /**
     * Обновляет поставку товара.
     *
     * @return обновленная поставка товара.
     */
    @Override
    public ProductSupply updateSupply(Long id, ProductRequestDto supply) {
        log.debug("updateSupply - start, id = {}, supply = {}", id, supply);

        if (productSupplyRepository.findById(id).isEmpty()) {
            log.debug("updateSupply - end, id = {}, supply = {}", id, supply);
            return null;
        }

        Product product = productRepository.findById(supply.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));

        ProductSupply productSupply = this.toProductSupply(supply, product);
        productSupply.setId(id);

        return productSupplyRepository.save(productSupply);
    }

    /**
     * Удаляет поставку товара.
     *
     */
    @Override
    public Boolean deleteSupply(Long id) {
        log.debug("deleteSupply - start, id = {}", id);

        Optional<ProductSupply> productSupply = productSupplyRepository.findById(id);

        if (productSupply.isPresent()) {
            productSupplyRepository.deleteById(id);
            log.debug("deleteSupply - end, id = {}", id);
            return true;
        }

        log.debug("deleteSupply - end, id = {}", id);

        return false;
    }

    /**
     * Получает отдельную поставку товара.
     *
     * @return отдельная поставка товара.
     */
    @Override
    public ProductSupply getSupplyById(Long id) {
        log.debug("getSupplyById - start, id = {}", id);
        ProductSupply productSupply = productSupplyRepository.findById(id).orElse(null);
        log.debug("getSupplyById - end, id = {}", id);
        return productSupply;
    }

    private ProductSupply toProductSupply(ProductRequestDto productSupplyRequestDto, Product product) {

        if (productSupplyRequestDto == null) return null;

        ProductSupply productSupply = new ProductSupply();

        productSupply.setDocumentName(productSupplyRequestDto.getDocumentName());
        productSupply.setProduct(product);
        productSupply.setQuantity(productSupplyRequestDto.getQuantity());

        return productSupply;
    }
}
