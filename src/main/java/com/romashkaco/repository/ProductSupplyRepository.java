package com.romashkaco.repository;

import com.romashkaco.model.Product;
import com.romashkaco.model.ProductSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с ProductSupply.
 */
@Repository
public interface ProductSupplyRepository extends JpaRepository<ProductSupply, Long> {
    ProductSupply findByProduct(Product product);
}