package com.oioioihi.ootd.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class ProductUpdateDto {

    @NotBlank(message = "기존 카테고리명은 빈 값일 수 없습니다.")
    String oldCategory;
    @NotBlank(message = "기존 브랜드명은 빈 값일 수 없습니다.")
    String oldBrand;
    @NotBlank(message = "새 카테고리명은 빈 값일 수 없습니다.")
    String newCategory;
    @NotBlank(message = "새 브랜드명은 빈 값일 수 없습니다.")
    String newBrand;
    @NotNull(message = "가격은 빈 값일 수 없습니다.")
    @PositiveOrZero
    long price;

}
