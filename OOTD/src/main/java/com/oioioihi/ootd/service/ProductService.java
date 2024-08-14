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

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductDao> getMinPriceProducts() {

        return productRepository.getMinPriceProducts();
    }

    @Transactional(readOnly = true)
    public ProductDao getMinPriceProductAndBrand() {

        return productRepository.getMinPriceProductAndBrand();
    }

    @Transactional(readOnly = true)
    public List<Product> findAllByIdBrandId(Long brandId) {
        return productRepository.findAllByBrandId(brandId)
                .orElseThrow(() -> new NotFoundException("%s번 브랜드가 없습니다.".formatted(brandId)));
    }


    @Transactional(readOnly = true)
    public Pair<List<ProductDao>, List<ProductDao>> findMinAndMaxProductByCategoryName(Long categoryId) {

        List<ProductDao> expensiveProduct = productRepository.findMostExpensiveProductByCategoryId(categoryId)
                .orElseThrow(() -> new NotFoundException("%s번 카테고리가 없습니다.".formatted(categoryId)));
        List<ProductDao> cheapestProduct = productRepository.findMostCheapestProductByCategoryId(categoryId)
                .orElseThrow(() -> new NotFoundException("%s번 카테고리가 없습니다.".formatted(categoryId)));

        return Pair.of(expensiveProduct, cheapestProduct);
    }

    @Transactional
    public Product createProduct(Product product) {

        try {
            Product save = productRepository.save(product);
            System.out.println(save);
            return save;
        } catch (Exception e) {
            throw new ProductAlreadyExistException("이미 존재하는 상품입니다.");
        }
    }

    @Transactional
    public Product updateProduct(Product product) {
        return null;
    }

    @Transactional
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    @Transactional(readOnly = true)
    public Product findProductById(Long categoryId, Long brandId) {
        return productRepository.findByCategoryIdAndBrandId(categoryId, brandId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 상품입니다.")
        );
    }
}
