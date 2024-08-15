package com.oioioihi.ootd.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oioioihi.ootd.model.entity.Brand;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandDto {

    private Long id;
    @NotBlank(message = "브랜드명은 빈 값일 수 없습니다.")
    private String name;

    public static BrandDto createInstance(Brand brand) {
        return new BrandDto(
                brand.getId(),
                brand.getName());
    }

    public Brand toEntity() {
        return Brand.createInstance(this.name);
    }
}
