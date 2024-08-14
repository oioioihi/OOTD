package com.oioioihi.ootd.model.entity;


import com.oioioihi.ootd.model.dto.ProductDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @EmbeddedId
    private ProductId id;

    @Column(name = "price")
    private Long price;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    private Brand brand;

    private Product(ProductId id, Long price, Category category, Brand brand) {
        this.id = id;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }

    public static Product createInstance(ProductDto productDto) {
        ProductId productId = ProductId.createInstance(productDto);
        return Product.builder()
                .id(productId)
                .price(productDto.getPrice())
                .build();
    }

}
