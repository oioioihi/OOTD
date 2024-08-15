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
    public Optional<ProductDao> getMinPriceProductAndBrand() {


        return Optional.ofNullable(from(product)
                .select(Projections.constructor(ProductDao.class,
                        brand.id.as("brandId"),
                        brand.name.as("brand"),
                        product.price.sum().as("price")))
                .innerJoin(brand)
                .on(brand.id.eq(product.brandId))
                .groupBy(product.brandId)
                .orderBy(product.price.sum().asc())
                .limit(1)
                .fetchOne());
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

    //
//    select b1_0.name,p1_0.price from product p1_0 join brand b1_0 on b1_0.id=p1_0.brand_id where p1_0.price=(select min(p2_0.price) from product p2_0 where p2_0.category_id=1) and p1_0.category_id=1
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
