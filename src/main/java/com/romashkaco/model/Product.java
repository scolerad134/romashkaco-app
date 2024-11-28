package com.romashkaco.model;

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
public class Product {

    /**
     * ID.
     */
    private Long id;

    /**
     * Название товара.
     */
    @NotBlank(message = "Название товара обязательно")
    @Size(max = 255, message = "Название товара не должно превышать 255 символов")
    private String name;

    /**
     * Описание товара.
     */
    @Size(max = 4096, message = "Описание товара не должно превышать 4096 символов")
    private String description;

    /**
     * Цена товара.
     */
    @Min(value = 0, message = "Цена товара не может быть меньше 0")
    private double price = 0.0;

    /**
     * В наличии ли товар.
     */
    private Boolean inStock = false;

}