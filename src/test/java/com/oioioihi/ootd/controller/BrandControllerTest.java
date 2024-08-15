package com.oioioihi.ootd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oioioihi.ootd.model.dto.BrandDto;
import com.oioioihi.ootd.model.entity.Brand;
import com.oioioihi.ootd.service.BrandService;
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

@WebMvcTest(controllers = BrandController.class)
class BrandControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BrandService brandService;
    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;
    @Captor
    ArgumentCaptor<BrandDto> brandDtoArgumentCaptor;

    @Test
    @DisplayName("Brand 조회")
    void getBrand() throws Exception {

        // Given
        String brandName = "A";
        Brand brand = Brand.createInstance(brandName);
        BrandDto brandDto = BrandDto.createInstance(brand);

        // When
        when(brandService.findBrandByName(brandName)).thenReturn(brand);

        // Then
        this.mockMvc.perform(get("/api/v1/brands/{brand-name}", brandName))
                .andDo(print())
                .andExpect(status().isOk());

        verify(brandService, times(1)).findBrandByName(brandName);
    }

    @Test
    @DisplayName("Brand 등록")
    void createBrand() throws Exception {

        // Given
        BrandDto brandDto = BrandDto.builder().name("A").build();
        Brand brand = new Brand("A");
        when(brandService.createBrand(any(BrandDto.class))).thenReturn(brand);

        // When
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(brandDto);

        //Then
        this.mockMvc.perform(post("/api/v1/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());

        verify(brandService, times(1)).createBrand(any(BrandDto.class));
    }

    @Test
    @DisplayName("Brand 수정")
    void updateBrand() throws Exception {

        // Given
        long brandId = 1L;
        BrandDto brandDto = BrandDto.builder()
                .name("A")
                .build();
        doNothing().when(brandService).updateBrand(longArgumentCaptor.capture(), brandDtoArgumentCaptor.capture());

        // Whens
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(brandDto);

        //Then
        this.mockMvc.perform(patch("/api/v1/brands/{brandId}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk());

        verify(brandService, times(1)).updateBrand(longArgumentCaptor.getValue(), brandDtoArgumentCaptor.getValue());
    }

    @Test
    @DisplayName("Brand 업데이트 - 유효성 검사 실패 케이스")
    void updateBrand_validationFailure() throws Exception {
        long brandId = 1L;
        BrandDto brandDto = BrandDto.builder()
                .name("")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(brandDto);

        // When -> Then
        this.mockMvc.perform(patch("/api/v1/brands/{brandId}", brandId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(MethodArgumentNotValidException.class))
                .andExpect(jsonPath("$.data.name").value("브랜드명은 빈 값일 수 없습니다."));

        verify(brandService, times(0)).updateBrand(longArgumentCaptor.capture(), brandDtoArgumentCaptor.capture());

    }


    @Test
    @DisplayName("Brand 삭제")
    void deleteBrand() throws Exception {

        // Given
        long brandId = 1L;

        // When
        doNothing().when(brandService).deleteBrand(brandId);

        // Then
        this.mockMvc.perform(delete("/api/v1/brands/{brandId}", brandId))
                .andDo(print())
                .andExpect(status().isOk());

        verify(brandService, times(1)).deleteBrand(brandId);
    }


}