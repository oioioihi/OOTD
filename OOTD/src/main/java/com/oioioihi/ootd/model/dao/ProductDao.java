package com.oioioihi.ootd.model.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductDao {
    Long categoryId;
    Long brandId;
    String category;
    String brand;
    long price;

    public ProductDao(Long brandId, String brand, long price) {
        this.brandId = brandId;
        this.brand = brand;
        this.price = price;
    }

    public ProductDao(String brand, long price) {

        this.brand = brand;
        this.price = price;
    }
}
