package com.oioioihi.ootd.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class ProductCreateDto {


    @NotBlank(message = "카테고리명은 빈 값일 수 없습니다.")
    String category;
    @NotBlank(message = "브랜드명은 빈 값일 수 없습니다.")
    String brand;
    @NotNull(message = "가격은 빈 값일 수 없습니다.")
    @PositiveOrZero
    long price;

}
