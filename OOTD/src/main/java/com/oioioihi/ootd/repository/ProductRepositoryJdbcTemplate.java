package com.oioioihi.ootd.repository;

import com.oioioihi.ootd.model.dao.ProductDao;

import java.util.List;

public interface ProductRepositoryJdbcTemplate {
    List<ProductDao> getMinPriceProducts();
}
