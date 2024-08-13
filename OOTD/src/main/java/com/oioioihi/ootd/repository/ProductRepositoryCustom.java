package com.oioioihi.ootd.repository;

import com.oioioihi.ootd.model.dao.ProductDao;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryCustom {
    ProductDao getMinPriceProductAndBrand();

    Optional<List<ProductDao>> findMostExpensiveProductByCategoryId(Long categoryId);

    Optional<List<ProductDao>> findMostCheapestProductByCategoryId(Long categoryId);


}
