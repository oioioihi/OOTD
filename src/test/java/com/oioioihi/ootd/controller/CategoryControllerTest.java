package com.oioioihi.ootd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oioioihi.ootd.model.dto.CategoryDto;
import com.oioioihi.ootd.model.entity.Category;
import com.oioioihi.ootd.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
class CategoryControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CategoryService categoryService;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    ArgumentCaptor<CategoryDto> categoryDtoArgumentCaptor;

    @Test
    @DisplayName("Category 조회")
    void getCategory() throws Exception {

        // Given
        String categoryName = "상의";
        Category category = Category.createInstance(categoryName);
        CategoryDto categoryDto = CategoryDto.createInstance(category);

        // When
        when(categoryService.findCategoryByName(categoryName)).thenReturn(category);

        // Then
        this.mockMvc.perform(get("/api/v1/categorys/{category-name}", categoryName))
                .andDo(print())
                .andExpect(status().isOk());

        verify(categoryService, times(1)).findCategoryByName(categoryName);
    }

    @Test
    @DisplayName("Category 등록")
    void createCategory() throws Exception {

        // Given
        CategoryDto categoryDto = CategoryDto.builder().name("A").build();
        Category category = new Category("A");
        when(categoryService.createCategory(any(CategoryDto.class))).thenReturn(category);

        // When
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(categoryDto);

        //Then
        this.mockMvc.perform(post("/api/v1/categorys")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());

        verify(categoryService, times(1)).createCategory(any(CategoryDto.class));
    }

    @Test
    @DisplayName("Category 수정")
    void updateCategory() throws Exception {

        // Given
        long categoryId = 1L;
        CategoryDto categoryDto = CategoryDto.builder()
                .name("상의")
                .build();
        doNothing().when(categoryService).updateCategory(longArgumentCaptor.capture(), categoryDtoArgumentCaptor.capture());

        // Whens
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(categoryDto);

        //Then
        this.mockMvc.perform(patch("/api/v1/categorys/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());

        verify(categoryService, times(1)).updateCategory(longArgumentCaptor.getValue(), categoryDtoArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Category 업데이트 - 유효성 검사 실패 케이스")
    void updateCategory_validationFailure() throws Exception {
        long categoryId = 1L;
        CategoryDto categoryDto = CategoryDto.builder()
                .name("")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(categoryDto);

        // When -> Then
        this.mockMvc.perform(patch("/api/v1/categorys/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MethodArgumentNotValidException.class))
                .andExpect(jsonPath("$.data.name").value("카테고리명은 빈 값일 수 없습니다."));

        verify(categoryService, times(0)).updateCategory(longArgumentCaptor.capture(), categoryDtoArgumentCaptor.capture());

    }


    @Test
    @DisplayName("Category 삭제")
    void deleteCategory() throws Exception {

        // Given
        long categoryId = 1L;

        // When
        doNothing().when(categoryService).deleteCategory(categoryId);

        // Then
        this.mockMvc.perform(delete("/api/v1/categorys/{categoryId}", categoryId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategory(categoryId);
    }


}