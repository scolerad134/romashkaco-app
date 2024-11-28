package com.romashkaco.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс представляет собой модель продуктов в системе.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Entity
public class Product {

    /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название товара.
     */
    @NotBlank(message = "Название товара обязательно")
    @Size(max = 255, message = "Название товара не должно превышать 255 символов")
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    /**
     * Описание товара.
     */
    @Size(max = 4096, message = "Описание товара не должно превышать 4096 символов")
    @Column(name = "description", length = 4096)
    private String description;

    /**
     * Цена товара.
     */
    @Min(value = 0, message = "Цена товара не может быть меньше 0")
    @Column(name = "price", nullable = false)
    private double price = 0.0;

    /**
     * В наличии ли товар.
     */
    @Column(name = "in_stock", nullable = false)
    private Boolean inStock = false;

}