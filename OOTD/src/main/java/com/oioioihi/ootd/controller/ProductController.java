package com.oioioihi.ootd.controller;

import com.oioioihi.ootd.exception.ApiResponse;
import com.oioioihi.ootd.model.dto.LowestProductsDto;
import com.oioioihi.ootd.model.dto.LowestProductsOneBrandDto;
import com.oioioihi.ootd.model.dto.ProductByCategoryDto;
import com.oioioihi.ootd.model.dto.ProductDto;
import com.oioioihi.ootd.model.dto.request.ProductCreateDto;
import com.oioioihi.ootd.model.dto.request.ProductUpdateDto;
import com.oioioihi.ootd.model.entity.Product;
import com.oioioihi.ootd.service.ProductFacadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductFacadeService productFacadeService;

    @GetMapping("/cheapest-products")
    ApiResponse<LowestProductsDto> getCheapestProducts() {

        return ApiResponse.createSuccess(productFacadeService.getMinPriceProducts());
    }

    @GetMapping("/cheapest-products/brands")
    ApiResponse<LowestProductsOneBrandDto> getCheapestProductsByBrand() {

        return ApiResponse.createSuccess(productFacadeService.getMinPriceProductAndBrand());
    }

    @GetMapping("/cheapest-products/category/{category-name}")
    ApiResponse<ProductByCategoryDto> findMinAndMaxPriceProductByCategoryName(@PathVariable("category-name") String categoryName) {

        return ApiResponse.createSuccess(productFacadeService.findMinAndMaxPriceProductByCategoryName(categoryName));
    }

    @PostMapping
    ApiResponse<ProductDto> createProduct(@Valid @RequestBody final ProductCreateDto productCreateDto) {
        Product product = productFacadeService.createProduct(productCreateDto);
        return ApiResponse.createSuccess(ProductDto.createInstance(product));
    }

    @PatchMapping
    ApiResponse<?> updateProduct(@Valid @RequestBody final ProductUpdateDto productUpdateDto) {
        productFacadeService.updateProduct(productUpdateDto);
        return ApiResponse.createSuccessWithNoContent();
    }

    @DeleteMapping("category/{category-name}/brands/{brand-name}")
    ApiResponse<?> deleteProduct(@PathVariable("category-name") final String categoryName,
                                 @PathVariable("brand-name") final String brandName) {
        productFacadeService.deleteProduct(categoryName, brandName);
        return ApiResponse.createSuccessWithNoContent();
    }
}
