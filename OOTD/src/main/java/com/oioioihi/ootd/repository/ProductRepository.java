package com.oioioihi.ootd.repository;

import com.oioioihi.ootd.model.entity.Product;
import com.oioioihi.ootd.model.entity.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, ProductId>, ProductRepositoryCustom, ProductRepositoryJdbcTemplate {

    Optional<List<Product>> findAllByIdBrandId(Long brandId);


}
