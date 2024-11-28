package com.romashkaco.repository;

import com.romashkaco.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с Product.
 */

public interface ProductRepository extends JpaRepository<Product, Long> {

}
