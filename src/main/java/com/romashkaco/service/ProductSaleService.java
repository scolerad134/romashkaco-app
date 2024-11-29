package com.romashkaco.service;

import com.romashkaco.dto.ProductRequestDto;
import com.romashkaco.model.ProductSale;

import java.util.List;

/**
 * API сервиса для работы с {@link ProductSale}.
 */
public interface ProductSaleService {

    /**
     * Получает список продаж товаров.
     *
     * @return список продаж товаров.
     */
    List<ProductSale> getAllSaleProducts();

    /**
     * Создает продажу товара.
     *
     * @return созданная продажа товара.
     */
    ProductSale createSale(ProductRequestDto sale);

    /**
     * Обновляет продажу товара.
     *
     * @return обновленная продажа товара.
     */
    ProductSale updateSale(Long id, ProductRequestDto sale);

    /**
     * Удаляет продажу товара.
     *
     */
    Boolean deleteSale(Long id);

    /**
     * Получает отдельную продажу товара.
     *
     * @return отдельная продажа товара.
     */
    ProductSale getSaleById(Long id);
}
