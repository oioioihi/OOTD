package com.oioioihi.ootd.repository;

import com.oioioihi.ootd.model.dao.ProductDao;
import com.oioioihi.ootd.model.entity.Product;
import com.oioioihi.ootd.model.entity.QBrand;
import com.oioioihi.ootd.model.entity.QProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {

    QProduct product = QProduct.product;
    QBrand brand = QBrand.brand;

    public ProductRepositoryCustomImpl() {
        super(Product.class);
    }

    @Override
    public ProductDao getMinPriceProductAndBrand() {


        return from(product)
                .select(Projections.constructor(ProductDao.class,
                        brand.id.as("brandId"),
                        brand.name.as("brand"),
                        product.price.sum().as("price")))
                .innerJoin(brand)
                .on(brand.id.eq(product.brandId))
                .groupBy(product.brandId)
                .orderBy(product.price.sum().asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public Optional<List<ProductDao>> findMostExpensiveProductByCategoryId(Long categoryId) {
        return Optional.ofNullable(
                from(product)
                        .select(Projections.constructor(ProductDao.class,
                                brand.name.as("brand"),
                                product.price.as("price")))
                        .innerJoin(brand)
                        .on(brand.id.eq(product.brandId))
                        .where(product.price.eq(
                                        JPAExpressions.select(product.price.max())
                                                .from(product)
                                                .where(product.categoryId.eq(categoryId))
                                )
                                .and(product.categoryId.eq(categoryId)))
                        .fetch()

        );
    }

    @Override
    public Optional<List<ProductDao>> findMostCheapestProductByCategoryId(Long categoryId) {
        return Optional.ofNullable(
                from(product)
                        .select(Projections.constructor(ProductDao.class,
                                brand.name.as("brand"),
                                product.price.as("price")))
                        .innerJoin(brand)
                        .on(brand.id.eq(product.brandId))
                        .where(product.price.eq(
                                        JPAExpressions.select(product.price.min())
                                                .from(product)
                                                .where(product.categoryId.eq(categoryId)))
                                .and(product.categoryId.eq(categoryId)))
                        .fetch()

        );
    }
}
