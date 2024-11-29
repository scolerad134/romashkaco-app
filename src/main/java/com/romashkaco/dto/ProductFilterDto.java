package com.romashkaco.dto;

import java.math.BigDecimal;

public record ProductFilterDto(
    String name,
    Double minPrice,
    Double maxPrice,
    Boolean inStock,
    String sortBy,
    String sortOrder,
    Integer limit
) {}
