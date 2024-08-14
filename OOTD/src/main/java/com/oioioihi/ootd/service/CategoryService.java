package com.oioioihi.ootd.service;

import com.oioioihi.ootd.exception.NotFoundException;
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
}
