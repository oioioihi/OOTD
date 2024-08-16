package com.oioioihi.ootd.service;

import com.oioioihi.ootd.exception.NotFoundException;
import com.oioioihi.ootd.exception.ProductAlreadyExistException;
import com.oioioihi.ootd.model.dao.ProductDao;
import com.oioioihi.ootd.model.entity.Product;
import com.oioioihi.ootd.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.oioioihi.util.Messages.*;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductDao> getMinPriceProducts() {

        return productRepository.getMinPriceProducts();
    }

    @Transactional(readOnly = true)
    public ProductDao getCheapestProductsByBrand() {

        return productRepository.getMinPriceProductAndBrand()
                .orElseThrow(() -> new NotFoundException(PRODUCT_CONDITION_NOT_EXIST));

    }

    @Transactional(readOnly = true)
    public List<Product> findAllByBrandId(Long brandId) {
        return productRepository.findAllByBrandId(brandId)
                .orElseThrow(() -> new NotFoundException(BRAND_NOT_EXIST.formatted(brandId)));
    }


    @Transactional(readOnly = true)
    public Pair<List<ProductDao>, List<ProductDao>> findMinAndMaxProductByCategoryName(Long categoryId) {

        List<ProductDao> expensiveProduct = productRepository.findMostExpensiveProductByCategoryId(categoryId)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_EXIST_BY_CATEGORY.formatted(categoryId)));
        List<ProductDao> cheapestProduct = productRepository.findMostCheapestProductByCategoryId(categoryId)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_EXIST_BY_CATEGORY.formatted(categoryId)));

        if (expensiveProduct.size() == 0) {
            throw new NotFoundException(PRODUCT_NOT_EXIST_CATEGORY);
        }
        return Pair.of(expensiveProduct, cheapestProduct);
    }

    @Transactional
    public Product createProduct(Product product) {

        try {
            return productRepository.save(product);
        } catch (Exception e) {
            throw new ProductAlreadyExistException(PRODUCT_ALREADY_EXIST);
        }
    }

    @Transactional
    public void updateProduct(Product savedProduct, Product newProduct) {
        Product product = savedProduct.update(newProduct);
        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public boolean isExist(Long categoryId, Long brandId) {
        return productRepository.findByCategoryIdAndBrandId(categoryId, brandId).isPresent();
    }

    @Transactional(readOnly = true)
    public Product findProductById(Long categoryId, Long brandId) {
        return productRepository.findByCategoryIdAndBrandId(categoryId, brandId)
                .orElseThrow(() -> new NotFoundException(PRODUCT_NOT_EXIST));
    }
}
