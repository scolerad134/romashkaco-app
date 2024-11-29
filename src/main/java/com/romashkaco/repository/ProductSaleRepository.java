package com.romashkaco.repository;

import com.romashkaco.model.ProductSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с ProductSale.
 */
@Repository
public interface ProductSaleRepository extends JpaRepository<ProductSale, Long> {
}