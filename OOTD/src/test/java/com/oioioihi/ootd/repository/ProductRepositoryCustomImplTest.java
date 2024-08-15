package com.oioioihi.ootd.repository;

import com.oioioihi.ootd.model.dao.ProductDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@Sql(scripts = {"/test_sql/test.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class ProductRepositoryCustomImplTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void getCheapestProducts() {

        List<ProductDao> cheapestProducts = productRepository.getMinPriceProducts();
        System.out.println(cheapestProducts);

    }

    @Test
    public void getMinPriceProductAndBrand() {

        Optional<ProductDao> minPriceProductAndBrand = productRepository.getMinPriceProductAndBrand();
        System.out.println(minPriceProductAndBrand.get());

    }

    @Test
    public void findTopByCategoryIdOrderByPriceDesc() {

        Optional<List<ProductDao>> top = productRepository.findMostExpensiveProductByCategoryId(6L);
        Optional<List<ProductDao>> cheap = productRepository.findMostCheapestProductByCategoryId(6L);
        System.out.println(top);
        System.out.println(cheap);

    }
}