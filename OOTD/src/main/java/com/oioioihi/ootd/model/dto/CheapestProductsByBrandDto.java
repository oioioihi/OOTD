package com.oioioihi.ootd.model.dto;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record CheapestProductsByBrandDto(
        String brand,
        List<ProductDto> productList,
        long totalPrice

) {
}
