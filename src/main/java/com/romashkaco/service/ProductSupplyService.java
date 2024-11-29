package com.romashkaco.service;

import com.romashkaco.dto.ProductRequestDto;
import com.romashkaco.model.ProductSupply;

import java.util.List;

/**
 * API сервиса для работы с {@link ProductSupply}.
 */
public interface ProductSupplyService {

    /**
     * Получает список поставок товаров.
     *
     * @return список поставок товаров.
     */
    List<ProductSupply> getAllSupplyProducts();

    /**
     * Создает поставку товара.
     *
     * @return созданная поставка товара.
     */
    ProductSupply createSupply(ProductRequestDto supply);

    /**
     * Обновляет поставку товара.
     *
     * @return обновленная поставка товара.
     */
    ProductSupply updateSupply(Long id, ProductRequestDto supply);

    /**
     * Удаляет поставку товара.
     *
     */
    Boolean deleteSupply(Long id);

    /**
     * Получает отдельную поставку товара.
     *
     * @return отдельная поставка товара.
     */
    ProductSupply getSupplyById(Long id);
}
