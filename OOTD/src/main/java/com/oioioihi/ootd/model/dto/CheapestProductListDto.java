package com.oioioihi.ootd.model.dto;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record CheapestProductListDto(
        List<ProductDto> productList,
        long totalPrice

) {
}
