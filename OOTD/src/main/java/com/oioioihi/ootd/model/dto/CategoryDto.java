package com.oioioihi.ootd.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.oioioihi.ootd.model.entity.Category;
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
public class CategoryDto {

    private Long id;
    @NotBlank(message = "카테고리명은 빈 값일 수 없습니다.")
    private String name;

    public static CategoryDto createInstance(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName());
    }

    public Category toEntity() {
        return Category.createInstance(this.name);
    }
}
