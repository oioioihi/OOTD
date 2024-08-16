package com.oioioihi.ootd.service;

import com.oioioihi.ootd.exception.BrandAlreadyExistException;
import com.oioioihi.ootd.exception.NotFoundException;
import com.oioioihi.ootd.model.dto.BrandDto;
import com.oioioihi.ootd.model.entity.Brand;
import com.oioioihi.ootd.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.oioioihi.util.Messages.*;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    @Transactional
    public Brand createBrand(final BrandDto brandDto) {
        if (brandRepository.findByName(brandDto.getName()).isPresent()) {
            throw new BrandAlreadyExistException(BRAND_ALREADY_EXIST.formatted(brandDto.getName()));
        }
        Brand brand = brandDto.toEntity();
        return brandRepository.save(brand);
    }

    @Transactional
    public void updateBrand(final long brandId, final BrandDto brandDto) {
        brandRepository.findByName(brandDto.getName())
                .ifPresentOrElse(findBrand -> {
                            throw new BrandAlreadyExistException(BRAND_ALREADY_EXIST.formatted(findBrand.getName()));
                        },
                        () -> {
                            Brand brand = brandRepository.findById(brandId).orElseThrow(() ->
                                    new NotFoundException(BRAND_NOT_EXIST.formatted(brandId)));
                            brandRepository.save(brand.update(brandDto));
                        });

    }

    @Transactional
    public void deleteBrand(long brandId) {
        Brand brand = brandRepository.findById(brandId).orElseThrow(() ->
                new NotFoundException(BRAND_NOT_EXIST.formatted(brandId)));
        brandRepository.delete(brand);
    }

    @Transactional(readOnly = true)
    public Brand findBrandByName(String brandName) {
        return brandRepository.findByName(brandName).orElseThrow(() ->
                new NotFoundException(BRAND_NAME_NOT_EXIST.formatted(brandName)));
    }

}
