package com.oioioihi.ootd.service;

import com.oioioihi.ootd.exception.NotFoundException;
import com.oioioihi.ootd.model.dto.BrandDto;
import com.oioioihi.ootd.model.entity.Brand;
import com.oioioihi.ootd.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public Brand createBrand(final BrandDto brandDto) {
        Brand brand = brandDto.toEntity();
        return brandRepository.save(brand);
    }

    @Transactional
    public void updateBrand(final long brandId, final BrandDto brandDto) {

        Brand brand = brandRepository.findById(brandId).orElseThrow(() ->
                new NotFoundException("%번 브랜드가 존재하지 않습니다.".formatted(brandId)));
        brandRepository.save(brand.update(brandDto));
    }

    @Transactional
    public void deleteBrand(long brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() ->
                new NotFoundException("%번 브랜드가 존재하지 않습니다.".formatted(brandId)));
        brandRepository.delete(brand);
    }

    @Transactional(readOnly = true)
    public Brand findBrandByName(String brandName) {
        return brandRepository.findByName(brandName).orElseThrow(() ->
                new NotFoundException("%s 브랜드가 없습니다.".formatted(brandName)));
    }
}
