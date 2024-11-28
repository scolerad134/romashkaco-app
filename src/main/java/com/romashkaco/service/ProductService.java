package com.romashkaco.service;

import com.romashkaco.model.Product;

import java.util.List;

/**
 * API сервиса для работы с {@link Product}.
 */
public interface ProductService {

    /**
     * Получает список товаров.
     *
     * @return список товаров.
     */
    List<Product> getAllProducts();

    /**
     * Получает отдельный товар.
     *
     * @return отдельный товар.
     */
    Product getProductById(Long id);

    /**
     * Создает товар.
     *
     * @return созданный товар.
     */
    Product createProduct(Product product);

    /**
     * Обновляет товар.
     *
     * @return обновленный товар.
     */
    Product updateProduct(Long id, Product product);

    /**
     * Удаляет товар.
     *
     */
    Boolean deleteProduct(Long id);
}
