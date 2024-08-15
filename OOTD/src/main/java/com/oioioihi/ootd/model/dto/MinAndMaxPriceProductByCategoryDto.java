package com.oioioihi.ootd.model.dto;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record MinAndMaxPriceProductByCategoryDto(
        String categoryName,
        List<ProductDto> cheapestProduct,
        List<ProductDto> mostExpensiveProduct

) {
}
