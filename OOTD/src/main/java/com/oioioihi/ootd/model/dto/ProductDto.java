package com.oioioihi.ootd.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oioioihi.ootd.model.entity.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    Long categoryId;
    Long brandId;
    @NotBlank(message = "카테고리명은 빈 값일 수 없습니다.")
    String category;
    @NotBlank(message = "브랜드명은 빈 값일 수 없습니다.")
    String brand;
    @NotBlank(message = "가격은 빈 값일 수 없습니다.")
    long price;

    public static ProductDto createInstance(Product product, String categoryName, String brandName) {
        return ProductDto.builder()
                .category(categoryName)
                .brand(brandName)
                .price(product.getPrice())
                .build();
    }

    public Product toEntity() {
        return Product.createInstance(this);
    }
}
