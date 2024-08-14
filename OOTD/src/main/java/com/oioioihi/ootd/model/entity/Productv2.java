//package com.oioioihi.ootd.model.entity;
//
//
//import com.oioioihi.ootd.model.dto.ProductDto;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@Builder
//@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Productv2 {
//
//    @EmbeddedId
//    private ProductId id;
//
//    @Column(name = "price")
//    private Long price;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "category_id", insertable = false, updatable = false)
//    private Category category;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
//    private Brand brand;
//
//    private Productv2(ProductId id, Long price, Category category, Brand brand) {
//        this.id = id;
//        this.price = price;
//        this.category = category;
//        this.brand = brand;
//    }
//
//    public static Productv2 createInstance(ProductDto productDto) {
//        ProductId productId = ProductId.createInstance(productDto);
//        return Productv2.builder()
//                .id(productId)
//                .price(productDto.getPrice())
//                .build();
//    }
//
//}
