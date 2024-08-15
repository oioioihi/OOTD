package com.oioioihi.ootd.controller;

import com.oioioihi.ootd.exception.ApiResponse;
import com.oioioihi.ootd.model.dto.CategoryDto;
import com.oioioihi.ootd.model.entity.Category;
import com.oioioihi.ootd.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categorys")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;


    @GetMapping("/{category-name}")
    ApiResponse<CategoryDto> getCategory(@PathVariable("category-name") final String categoryName) {
        Category category = categoryService.findCategoryByName(categoryName);
        return ApiResponse.createSuccess(CategoryDto.createInstance(category));
    }

    @PostMapping
    ApiResponse<CategoryDto> createCategory(@Valid @RequestBody final CategoryDto categoryDto) {
        Category category = categoryService.createCategory(categoryDto);
        return ApiResponse.createSuccess(CategoryDto.createInstance(category));
    }

    @PatchMapping("/{categoryId}")
    ApiResponse<?> updateCategory(@PathVariable final long categoryId,
                                  @Valid @RequestBody final CategoryDto categoryDto) {
        categoryService.updateCategory(categoryId, categoryDto);
        return ApiResponse.createSuccessWithNoContent();
    }

    @DeleteMapping("/{categoryId}")
    ApiResponse<?> deleteCategory(@PathVariable final long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.createSuccessWithNoContent();
    }
}
