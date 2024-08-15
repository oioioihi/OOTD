package com.oioioihi.ootd.intergrationTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Sql(scripts = {"/test_sql/test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class IntegrationTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    public void testHello() throws Exception {
        mockMvc.perform(get("/api/v1/products/cheapest-products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.data.productList[0].category").value("모자"))
                .andExpect(jsonPath("$.data.productList[0].brand").value("D"))
                .andExpect(jsonPath("$.data.productList[0].price").value(1500))
                .andExpect(jsonPath("$.data.productList[1].category").value("양말"))
                .andExpect(jsonPath("$.data.productList[1].brand").value("I"))
                .andExpect(jsonPath("$.data.productList[1].price").value(1700))
                .andExpect(jsonPath("$.data.productList[2].category").value("액세서리"))
                .andExpect(jsonPath("$.data.productList[2].brand").value("F"))
                .andExpect(jsonPath("$.data.productList[2].price").value(1900))
                .andExpect(jsonPath("$.data.productList[3].category").value("가방"))
                .andExpect(jsonPath("$.data.productList[3].brand").value("A"))
                .andExpect(jsonPath("$.data.productList[3].price").value(2000))
                .andExpect(jsonPath("$.data.productList[4].category").value("바지"))
                .andExpect(jsonPath("$.data.productList[4].brand").value("D"))
                .andExpect(jsonPath("$.data.productList[4].price").value(3000))
                .andExpect(jsonPath("$.data.productList[5].category").value("아우터"))
                .andExpect(jsonPath("$.data.productList[5].brand").value("E"))
                .andExpect(jsonPath("$.data.productList[5].price").value(5000))
                .andExpect(jsonPath("$.data.productList[6].category").value("스니커즈"))
                .andExpect(jsonPath("$.data.productList[6].brand").value("G"))
                .andExpect(jsonPath("$.data.productList[6].price").value(9000))
                .andExpect(jsonPath("$.data.productList[7].category").value("상의"))
                .andExpect(jsonPath("$.data.productList[7].brand").value("C"))
                .andExpect(jsonPath("$.data.productList[7].price").value(10000))
                .andExpect(jsonPath("$.data.totalPrice").value(34100))
                .andDo(print());
    }

    @Test
    @DisplayName("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    public void getCheapestProductsByBrand() throws Exception {
        mockMvc.perform(get("/api/v1/products/cheapest-products/brands")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value(""))
                .andExpect(jsonPath("$.data.brand").value("D"))
                .andExpect(jsonPath("$.data.productList.length()").value(8))
                .andExpect(jsonPath("$.data.productList[0].category").value("상의"))
                .andExpect(jsonPath("$.data.productList[0].price").value(10100))
                .andExpect(jsonPath("$.data.productList[1].category").value("아우터"))
                .andExpect(jsonPath("$.data.productList[1].price").value(5100))
                .andExpect(jsonPath("$.data.productList[2].category").value("바지"))
                .andExpect(jsonPath("$.data.productList[2].price").value(3000))
                .andExpect(jsonPath("$.data.productList[3].category").value("스니커즈"))
                .andExpect(jsonPath("$.data.productList[3].price").value(9500))
                .andExpect(jsonPath("$.data.productList[4].category").value("가방"))
                .andExpect(jsonPath("$.data.productList[4].price").value(2500))
                .andExpect(jsonPath("$.data.productList[5].category").value("모자"))
                .andExpect(jsonPath("$.data.productList[5].price").value(1500))
                .andExpect(jsonPath("$.data.productList[6].category").value("양말"))
                .andExpect(jsonPath("$.data.productList[6].price").value(2400))
                .andExpect(jsonPath("$.data.productList[7].category").value("액세서리"))
                .andExpect(jsonPath("$.data.productList[7].price").value(2000))
                .andExpect(jsonPath("$.data.totalPrice").value(36100))
                .andDo(print());
    }


    @Test
    @DisplayName("category별 가장 저렴한 상품 조회")
    public void cheapestAndMostExpensiveProductsByCategory() throws Exception {
        // Given
        String categoryName = "상의";

        // When & Then
        mockMvc.perform(get("/api/v1/products/cheapest-products/category/{category-name}", categoryName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value(""))
                .andExpect(jsonPath("$.data.categoryName").value(categoryName))
                .andExpect(jsonPath("$.data.cheapestProduct[0].brand").value("C"))
                .andExpect(jsonPath("$.data.cheapestProduct[0].price").value(10000))
                .andExpect(jsonPath("$.data.mostExpensiveProduct[0].brand").value("I"))
                .andExpect(jsonPath("$.data.mostExpensiveProduct[0].price").value(11400))
                .andDo(print());
    }
}
