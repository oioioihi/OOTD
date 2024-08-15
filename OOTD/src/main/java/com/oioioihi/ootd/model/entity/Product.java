package com.oioioihi.ootd.model.entity;


import com.oioioihi.ootd.model.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "PRODUCT", uniqueConstraints = {
        @UniqueConstraint(
                name = "CATEGORY_BRAND_UNIQUE",
                columnNames = {"category_id", "brand_id"}
        )})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "brand_id")
    private Long brandId;
    @Column(name = "price")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Brand brand;


    public static Product createInstance(ProductDto productDto) {
        return Product.builder()
                .categoryId(productDto.getCategoryId())
                .brandId(productDto.getBrandId())
                .price(productDto.getPrice())
                .build();
    }

    public Product update(Product newProduct) {
        this.categoryId = newProduct.getCategoryId();
        this.brandId = newProduct.getBrandId();
        this.price = newProduct.price;
        return this;
    }
}
