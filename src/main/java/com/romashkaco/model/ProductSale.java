package com.romashkaco.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс представляет собой модель продажи товара в системе.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_sale")
@Entity
public class ProductSale {

    /**
     * ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название документа.
     */
    @NotBlank(message = "Название документа не может быть пустым")
    @Size(max = 255, message = "Название документа не может превышать 255 символов")
    private String documentName;

    /**
     * Товар, который был продан.
     */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Количество проданного товара.
     */
    @Positive(message = "Количество проданного товара должно быть больше 0")
    private int quantity;

    /**
     * Стоимость покупки.
     */
    @PositiveOrZero(message = "Количество товара не может быть меньше 0")
    private double purchasePrice;
}
