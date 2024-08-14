package com.oioioihi.ootd.model.dto;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record LowestProductsDto(
        List<ProductDto> productList,
        long totalPrice

) {
}
