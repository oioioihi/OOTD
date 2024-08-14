package com.oioioihi.ootd.model.dto;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record ProductByCategoryDto(
        List<ProductDto> min,
        List<ProductDto> max,
        String categoryName

) {
}
