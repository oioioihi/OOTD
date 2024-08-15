package com.oioioihi.ootd.service;

import com.oioioihi.ootd.exception.CategoryAlreadyExistException;
import com.oioioihi.ootd.exception.NotFoundException;
import com.oioioihi.ootd.model.dto.CategoryDto;
import com.oioioihi.ootd.model.entity.Category;
import com.oioioihi.ootd.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category findCategoryByName(String categoryName) {

        return categoryRepository.findByName(categoryName).orElseThrow(() ->
                new NotFoundException("%s 카테고리가 없습니다.".formatted(categoryName)));
    }

    @Transactional
    public Category createCategory(final CategoryDto categoryDto) {
        if (categoryRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new CategoryAlreadyExistException("%s은 이미 존재하는 카테고리입니다.".formatted(categoryDto.getName()));
        }
        Category category = categoryDto.toEntity();
        return categoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(final long categoryId, final CategoryDto categoryDto) {
        categoryRepository.findByName(categoryDto.getName())
                .ifPresentOrElse(findCategory -> {
                            throw new CategoryAlreadyExistException("%s은 이미 존재하는 카테고리입니다.".formatted(findCategory.getName()));
                        },
                        () -> {
                            Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                                    new NotFoundException("%s번 카테고리가 존재하지 않습니다.".formatted(categoryId)));
                            categoryRepository.save(category.update(categoryDto));
                        });

    }

    @Transactional
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException("%s번 카테고리가 존재하지 않습니다.".formatted(categoryId)));
        categoryRepository.delete(category);
    }

}
