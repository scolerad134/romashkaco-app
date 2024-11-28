package com.romashkaco.service.impl;

import com.romashkaco.model.Product;
import com.romashkaco.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Map<Long, Product> products = new HashMap<>();
    private long currentId = 1;

    /**
     * Получает список товаров.
     *
     * @return список товаров.
     */
    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    /**
     * Получает отдельный товар.
     *
     * @return отдельный товар.
     */
    @Override
    public Product getProductById(Long id) {
        return products.get(id);
    }

    /**
     * Создает товар.
     *
     * @return созданный товар.
     */
    @Override
    public Product createProduct(Product product) {
        product.setId(currentId++);
        products.put(product.getId(), product);
        return product;
    }

    /**
     * Обновляет товар.
     *
     * @return обновленный товар.
     */
    @Override
    public Product updateProduct(Long id, Product product) {
        if (products.containsKey(id)) {
            product.setId(id);
            products.put(id, product);
            return product;
        }
        return null;
    }

    /**
     * Удаляет товар.
     *
     */
    @Override
    public Boolean deleteProduct(Long id) {
        return products.remove(id) != null;
    }
}
