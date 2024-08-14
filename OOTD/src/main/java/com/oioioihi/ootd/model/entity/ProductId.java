package com.oioioihi.ootd.model.entity;


import com.oioioihi.ootd.model.dto.ProductDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class ProductId implements Serializable {

    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "brand_id")
    private Long brandId;

    private ProductId(Long categoryId, Long brandId) {
        this.categoryId = categoryId;
        this.brandId = brandId;
    }

    public static ProductId createInstance(ProductDto productDto) {
        return ProductId.builder()
                .categoryId(productDto.getCategoryId())
                .brandId(productDto.getBrandId())
                .build();
    }
}
