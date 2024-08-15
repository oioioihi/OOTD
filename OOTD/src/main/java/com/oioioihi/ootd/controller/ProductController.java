package com.oioioihi.ootd.controller;

import com.oioioihi.ootd.exception.ApiResponse;
import com.oioioihi.ootd.model.dto.CheapestProductListDto;
import com.oioioihi.ootd.model.dto.CheapestProductsByBrandDto;
import com.oioioihi.ootd.model.dto.MinAndMaxPriceProductByCategoryDto;
import com.oioioihi.ootd.model.dto.ProductDto;
import com.oioioihi.ootd.model.dto.request.ProductCreateDto;
import com.oioioihi.ootd.model.dto.request.ProductUpdateDto;
import com.oioioihi.ootd.service.ProductFacadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductFacadeService productFacadeService;

    @PostMapping
    ApiResponse<ProductDto> createProduct(@Valid @RequestBody final ProductCreateDto productCreateDto) {
        ProductDto product = productFacadeService.createProduct(productCreateDto);
        return ApiResponse.createSuccess(product);
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

    @GetMapping("/cheapest-products")
    ApiResponse<CheapestProductListDto> getCheapestProducts() {

        return ApiResponse.createSuccess(productFacadeService.getMinPriceProducts());
    }

    @GetMapping("/cheapest-products/brands")
    ApiResponse<CheapestProductsByBrandDto> getCheapestProductsByBrand() {

        return ApiResponse.createSuccess(productFacadeService.getCheapestProductsByBrand());
    }

    @GetMapping("/cheapest-products/category/{category-name}")
    ApiResponse<MinAndMaxPriceProductByCategoryDto> findMinAndMaxPriceProductByCategoryName(@PathVariable("category-name") String categoryName) {

        return ApiResponse.createSuccess(productFacadeService.findMinAndMaxPriceProductByCategoryName(categoryName));
    }


}
