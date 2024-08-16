package com.oioioihi.ootd.service;

import com.oioioihi.ootd.exception.CategoryAlreadyExistException;
import com.oioioihi.ootd.exception.NotFoundException;
import com.oioioihi.ootd.model.dto.CategoryDto;
import com.oioioihi.ootd.model.entity.Category;
import com.oioioihi.ootd.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oioioihi.util.Messages.*;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Category findCategoryByName(String categoryName) {

        return categoryRepository.findByName(categoryName).orElseThrow(() ->
                new NotFoundException(CATEGORY_NAME_NOT_EXIST.formatted(categoryName)));
    }

    @Transactional
    public Category createCategory(final CategoryDto categoryDto) {
        if (categoryRepository.findByName(categoryDto.getName()).isPresent()) {
            throw new CategoryAlreadyExistException(CATEGORY_ALREADY_EXIST.formatted(categoryDto.getName()));
        }
        Category category = categoryDto.toEntity();
        return categoryRepository.save(category);
    }

    @Transactional
    public void updateCategory(final long categoryId, final CategoryDto categoryDto) {
        categoryRepository.findByName(categoryDto.getName())
                .ifPresentOrElse(findCategory -> {
                            throw new CategoryAlreadyExistException(CATEGORY_ALREADY_EXIST.formatted(findCategory.getName()));
                        },
                        () -> {
                            Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                                    new NotFoundException(CATEGORY_NOT_EXIST.formatted(categoryId)));
                            categoryRepository.save(category.update(categoryDto));
                        });

    }

    @Transactional
    public void deleteCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException(CATEGORY_NOT_EXIST.formatted(categoryId)));
        categoryRepository.delete(category);
    }

}
