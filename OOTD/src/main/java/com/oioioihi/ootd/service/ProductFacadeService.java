package com.oioioihi.ootd.service;

import com.oioioihi.ootd.exception.ProductAlreadyExistException;
import com.oioioihi.ootd.model.dao.ProductDao;
import com.oioioihi.ootd.model.dto.CheapestProductListDto;
import com.oioioihi.ootd.model.dto.CheapestProductsByBrandDto;
import com.oioioihi.ootd.model.dto.MinAndMaxPriceProductByCategoryDto;
import com.oioioihi.ootd.model.dto.ProductDto;
import com.oioioihi.ootd.model.dto.request.ProductCreateDto;
import com.oioioihi.ootd.model.dto.request.ProductUpdateDto;
import com.oioioihi.ootd.model.entity.Brand;
import com.oioioihi.ootd.model.entity.Category;
import com.oioioihi.ootd.model.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor
public class ProductFacadeService {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    @Transactional(readOnly = true)
    public CheapestProductListDto getMinPriceProducts() {

        AtomicReference<Long> totalPrice = new AtomicReference<>(0L);
        List<ProductDao> minPriceProducts = productService.getMinPriceProducts();

        return CheapestProductListDto.builder()
                .productList(minPriceProducts
                        .stream()
                        .map(p -> {
                            totalPrice.updateAndGet(v -> v + p.getPrice());
                            return ProductDto.builder()
                                    .price(p.getPrice())
                                    .brand(p.getBrand())
                                    .category(p.getCategory())
                                    .build();
                        }).toList())
                .totalPrice(totalPrice.get())
                .build();
    }

    @Transactional(readOnly = true)
    public CheapestProductsByBrandDto getCheapestProductsByBrand() {

        ProductDao minPriceProductAndBrand = productService.getMinPriceProductAndBrand();
        List<Product> products = productService.findAllByIdBrandId(minPriceProductAndBrand.getBrandId());

        return CheapestProductsByBrandDto.builder()
                .brand(minPriceProductAndBrand.getBrand())
                .productList(products.stream().map(p -> {
                    return ProductDto.builder()
                            .price(p.getPrice())
                            .category(p.getCategory().getName())
                            .build();
                }).toList())
                .totalPrice(minPriceProductAndBrand.getPrice())
                .build();
    }


    @Transactional(readOnly = true)
    public MinAndMaxPriceProductByCategoryDto findMinAndMaxPriceProductByCategoryName(String categoryName) {

        Category category = categoryService.findCategoryByName(categoryName);
        Pair<List<ProductDao>, List<ProductDao>> products = productService.findMinAndMaxProductByCategoryName(category.getId());

        return MinAndMaxPriceProductByCategoryDto.builder()
                .cheapestProduct(products.getFirst()
                        .stream()
                        .map(p -> ProductDto.builder()
                                .price(p.getPrice())
                                .brand(p.getBrand())
                                .build()).toList())
                .mostExpensiveProduct(products.getSecond()
                        .stream()
                        .map(p ->
                                ProductDto.builder()
                                        .price(p.getPrice())
                                        .brand(p.getBrand())
                                        .build()).toList())
                .categoryName(category.getName())
                .build();
    }

    @Transactional
    public ProductDto createProduct(final ProductCreateDto productCreateDto) {

        Category category = categoryService.findCategoryByName(productCreateDto.getCategory());
        Brand brand = brandService.findBrandByName(productCreateDto.getBrand());


        if (productService.isExist(category.getId(), brand.getId())
        ) {
            throw new ProductAlreadyExistException("이미 존재하는 상품입니다.");
        }


        Product product = ProductDto.builder()
                .categoryId(category.getId())
                .brandId(brand.getId())
                .price(productCreateDto.getPrice())
                .build()
                .toEntity();

        Product savedProduct = productService.createProduct(product);
        return ProductDto.createInstance(savedProduct, category.getName(), brand.getName());
    }

    @Transactional
    public void updateProduct(final ProductUpdateDto productUpdateDto) {
//TODO 맞는 로직인지 확인필요 ..
        Category category = categoryService.findCategoryByName(productUpdateDto.getNewCategory());
        Brand brand = brandService.findBrandByName(productUpdateDto.getNewBrand());
        Product savedProduct = productService.findProductById(category.getId(), brand.getId());

        Product newProduct = ProductDto.builder()
                .categoryId(category.getId())
                .brandId(brand.getId())
                .price(productUpdateDto.getPrice())
                .build()
                .toEntity();

        productService.updateProduct(savedProduct, newProduct);
        deleteProduct(productUpdateDto.getOldCategory(), productUpdateDto.getOldBrand());
    }


    @Transactional
    public void deleteProduct(String categoryName, String brandName) {
        Category category = categoryService.findCategoryByName(categoryName);
        Brand brand = brandService.findBrandByName(brandName);

        Product product = productService.findProductById(category.getId(), brand.getId());
        productService.deleteProduct(product);

    }
}
