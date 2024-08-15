package com.oioioihi.ootd.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oioioihi.ootd.model.dto.ProductDto;
import com.oioioihi.ootd.model.dto.request.ProductCreateDto;
import com.oioioihi.ootd.model.dto.request.ProductUpdateDto;
import com.oioioihi.ootd.service.ProductFacadeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductFacadeService productFacadeService;

    @Test
    @DisplayName("Product 생성")
    void createProduct() throws Exception {
        // Given: ProductCreateDto 객체를 생성합니다.
        ProductCreateDto productCreateDto =
                ProductCreateDto
                        .builder()
                        .category("상의")
                        .brand("A")
                        .price(1000)
                        .build();
        ObjectMapper objectMapper = new ObjectMapper();

        // When: productFacadeService.createProduct 호출 시 기대하는 결과를 설정합니다.
        when(productFacadeService.createProduct(any(ProductCreateDto.class)))
                .thenReturn(ProductDto.builder().build());

        String content = objectMapper.writeValueAsString(productCreateDto);
        this.mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());

        // Then: productFacadeService.createProduct가 한 번 호출되었는지 확인합니다.
        verify(productFacadeService, times(1))
                .createProduct(any(ProductCreateDto.class));
    }

    @Test
    @DisplayName("Product 수정")
    void updateProduct() throws Exception {

        // Given: ProductUpdateDto 객체를 생성합니다.
        ProductUpdateDto productUpdateDto =
                ProductUpdateDto
                        .builder()
                        .oldCategory("상의")
                        .oldBrand("A")
                        .newCategory("아우터")
                        .newBrand("A")
                        .price(1500)
                        .build();
        ObjectMapper objectMapper = new ObjectMapper();

        // When: productFacadeService.updateProduct 호출 시 아무 동작도 하지 않도록 설정합니다.
        doNothing().when(productFacadeService).updateProduct(any(ProductUpdateDto.class));

        String content = objectMapper.writeValueAsString(productUpdateDto);
        this.mockMvc.perform(patch("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());

        verify(productFacadeService, times(1)).updateProduct(any(ProductUpdateDto.class));
    }

    @Test
    @DisplayName("Product 업데이트 - 유효성 검사 실패 케이스")
    void updateProduct_validationFailure() throws Exception {

        // Given: 유효하지 않은 ProductUpdateDto 객체를 생성합니다.
        ProductUpdateDto invalidProductUpdateDto =
                ProductUpdateDto
                        .builder()
                        .oldCategory("")  // 빈 값 -  @NotBlank 위반
                        .oldBrand("")     // 빈 값 - @NotBlank 위반
                        .newCategory("")  // 빈 값 - @NotBlank 위반
                        .newBrand("")     // 빈 값 - @NotBlank 위반
                        .price(-100)      // 음수로 - @PositiveOrZero 위반
                        .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(invalidProductUpdateDto);

        // When -> Then
        this.mockMvc.perform(patch("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MethodArgumentNotValidException.class))
                .andExpect(jsonPath("$.data.newCategory").value("새 카테고리명은 빈 값일 수 없습니다."))
                .andExpect(jsonPath("$.data.price").value("must be greater than or equal to 0"))
                .andExpect(jsonPath("$.data.oldCategory").value("기존 카테고리명은 빈 값일 수 없습니다."))
                .andExpect(jsonPath("$.data.newBrand").value("새 브랜드명은 빈 값일 수 없습니다."))
                .andExpect(jsonPath("$.data.oldBrand").value("기존 브랜드명은 빈 값일 수 없습니다."));  // 각각의 필드에 대해 오류가 발생했는지 확인합니다.

        verify(productFacadeService, times(0)).updateProduct(any(ProductUpdateDto.class));

    }

    @Test
    @DisplayName("Product 삭제")
    void deleteProduct() throws Exception {

        // Given
        String categoryName = "상의";
        String brandName = "A";

        // When
        doNothing().when(productFacadeService).deleteProduct(categoryName, brandName);

        // Then
        this.mockMvc.perform(delete("/api/v1/products/category/{category-name}/brands/{brand-name}", categoryName, brandName))
                .andDo(print())
                .andExpect(status().isOk());

        verify(productFacadeService, times(1)).deleteProduct(categoryName, brandName);
    }

}