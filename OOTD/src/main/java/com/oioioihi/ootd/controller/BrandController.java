package com.oioioihi.ootd.controller;

import com.oioioihi.ootd.exception.ApiResponse;
import com.oioioihi.ootd.model.dto.BrandDto;
import com.oioioihi.ootd.model.entity.Brand;
import com.oioioihi.ootd.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;


    @GetMapping("/{brand-name}")
    ApiResponse<BrandDto> getBrand(@PathVariable("brand-name") final String brandName) {
        Brand brand = brandService.findBrandByName(brandName);
        return ApiResponse.createSuccess(BrandDto.createInstance(brand));
    }

    @PostMapping
    ApiResponse<BrandDto> createBrand(@Valid @RequestBody final BrandDto brandDto) {
        Brand brand = brandService.createBrand(brandDto);
        return ApiResponse.createSuccess(BrandDto.createInstance(brand));
    }

    @PatchMapping("/{brandId}")
    ApiResponse<?> updateBrand(@PathVariable final long brandId,
                               @Valid @RequestBody final BrandDto brandDto) {
        brandService.updateBrand(brandId, brandDto);
        return ApiResponse.createSuccessWithNoContent();
    }

    @DeleteMapping("/{brandId}")
    ApiResponse<?> deleteBrand(@PathVariable final long brandId) {
        brandService.deleteBrand(brandId);
        return ApiResponse.createSuccessWithNoContent();
    }
}
