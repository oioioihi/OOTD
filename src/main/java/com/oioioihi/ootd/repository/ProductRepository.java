package com.oioioihi.ootd.repository;

import com.oioioihi.ootd.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom, ProductRepositoryJdbcTemplate {

    Optional<List<Product>> findAllByBrandId(Long brandId);

    Optional<Product> findByCategoryIdAndBrandId(Long categoryId, Long brandId);

}
