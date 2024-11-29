package com.romashkaco.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDto {

    @NotBlank(message = "Название документа не может быть пустым")
    @Size(max = 255, message = "Название документа не может превышать 255 символов")
    private String documentName;

    @NotNull(message = "ID товара не может быть пустым")
    private Long productId;

    @Positive(message = "Количество поставленного товара должно быть больше 0")
    private int quantity;
}

