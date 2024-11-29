package com.romashkaco.service.impl;

import com.romashkaco.dto.ProductRequestDto;
import com.romashkaco.model.Product;
import com.romashkaco.model.ProductSale;
import com.romashkaco.model.ProductSupply;
import com.romashkaco.repository.ProductRepository;
import com.romashkaco.repository.ProductSaleRepository;
import com.romashkaco.repository.ProductSupplyRepository;
import com.romashkaco.service.ProductSaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSaleServiceImpl implements ProductSaleService {

    private final ProductSaleRepository productSaleRepository;
    private final ProductRepository productRepository;
    private final ProductSupplyRepository productSupplyRepository;

    /**
     * Получает список продаж товаров.
     *
     * @return список продаж товаров.
     */
    @Override
    public List<ProductSale> getAllSaleProducts() {
        log.debug("getAllSaleProducts - start");
        List<ProductSale> products = productSaleRepository.findAll();
        log.debug("getAllSaleProducts - end, products = {}", products);
        return products;
    }

    /**
     * Создает продажу товара.
     *
     * @return созданная продажа товара.
     */
    @Override
    public ProductSale createSale(ProductRequestDto sale) {
        log.debug("createSale - start, sale = {}", sale);

        Product product = productRepository.findById(sale.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));

        if (!product.getInStock()) {
            throw new IllegalArgumentException("Товара нет в наличии");
        }

        ProductSupply productSupply = productSupplyRepository.findByProduct(product);

        if (productSupply.getQuantity() < sale.getQuantity()) {
            throw new IllegalArgumentException("Недостаточно товара на складе");
        }

        if (productSupply.getQuantity() - sale.getQuantity() == 0) {
            productSupplyRepository.deleteById(productSupply.getId());
            product.setInStock(false);
            productRepository.save(product);
        }

        ProductSale productSale = this.toProductSale(sale, product);

        productSale.setPurchasePrice(sale.getQuantity() * product.getPrice());

        return productSaleRepository.save(productSale);
    }

    /**
     * Обновляет продажу товара.
     *
     * @return обновленная продажа товара.
     */
    @Override
    public ProductSale updateSale(Long id, ProductRequestDto sale) {
        log.debug("updateSale - start, id = {}, sale = {}", id, sale);

        if (productSaleRepository.findById(id).isEmpty()) {
            log.debug("updateSupply - end, id = {}, sale = {}", id, sale);
            return null;
        }

        Product product = productRepository.findById(sale.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));

        ProductSale productSale = this.toProductSale(sale, product);
        productSale.setId(id);

        return productSaleRepository.save(productSale);
    }

    /**
     * Удаляет продажу товара.
     *
     */
    @Override
    public Boolean deleteSale(Long id) {
        log.debug("deleteSale - start, id = {}", id);

        Optional<ProductSale> productSale = productSaleRepository.findById(id);

        if (productSale.isPresent()) {
            productSaleRepository.deleteById(id);
            log.debug("deleteSale - end, id = {}", id);
            return true;
        }

        log.debug("deleteSale - end, id = {}", id);

        return false;
    }

    /**
     * Получает отдельную продажу товара.
     *
     * @return отдельная продажа товара.
     */
    @Override
    public ProductSale getSaleById(Long id) {
        log.debug("getSaleById - start, id = {}", id);
        ProductSale productSale = productSaleRepository.findById(id).orElse(null);
        log.debug("getSaleById - end, id = {}", id);
        return productSale;
    }

    private ProductSale toProductSale(ProductRequestDto productRequestDto, Product product) {
        if (productRequestDto == null) return null;

        ProductSale productSale = new ProductSale();

        productSale.setDocumentName(productRequestDto.getDocumentName());
        productSale.setProduct(product);
        productSale.setQuantity(productRequestDto.getQuantity());

        return productSale;
    }
}
