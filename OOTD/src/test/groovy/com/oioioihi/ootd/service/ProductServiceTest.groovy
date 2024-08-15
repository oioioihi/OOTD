package com.oioioihi.ootd.service

import com.oioioihi.ootd.exception.NotFoundException
import com.oioioihi.ootd.exception.ProductAlreadyExistException
import com.oioioihi.ootd.model.dao.ProductDao
import com.oioioihi.ootd.model.entity.Product
import com.oioioihi.ootd.repository.ProductRepository
import spock.lang.Specification
import spock.lang.Subject

class ProductServiceTest extends Specification {

    def productRepository = Mock(ProductRepository)

    @Subject
    ProductService stub = new ProductService(productRepository)


    def "가장 저렴한 제품 목록 조회"() {
        given:
        def productDao = new ProductDao(price: 100, brand: "BrandA", category: "CategoryA")
        when:
        def result = stub.getMinPriceProducts()

        then:
        1 * productRepository.getMinPriceProducts() >> [productDao]
        result.size() == 1
        result[0].price == 100
        result[0].brand == "BrandA"
        result[0].category == "CategoryA"
    }

    def "가장 저렴한 제품들과 해당 브랜드를 가져와야 한다"() {
        given:
        def productDao = new ProductDao(price: 100, brand: "BrandA", brandId: 1)

        when:
        def result = stub.getCheapestProductsByBrand()

        then:
        1 * productRepository.getMinPriceProductAndBrand() >> Optional.of(productDao)
        result.price == 100
        result.brand == "BrandA"
    }

    def "주어진 브랜드 ID로 모든 제품을 가져와야 한다"() {
        given:

        when:
        def result = stub.findAllByBrandId(1)

        then:
        1 * productRepository.findAllByBrandId(_ as Long) >> Optional.of([new Product()])

    }

    def "카테고리 ID로 가장 비싼 제품과 가장 저렴한 제품을 가져와야 한다"() {
        given:
        def expensiveProductDao = new ProductDao(price: 150, brand: "BrandB")
        def cheapestProductDao = new ProductDao(price: 50, brand: "BrandA")

        when:
        def result = stub.findMinAndMaxProductByCategoryName(1)

        then:
        1 * productRepository.findMostExpensiveProductByCategoryId(_ as Long) >> Optional.of([expensiveProductDao])
        1 * productRepository.findMostCheapestProductByCategoryId(_ as Long) >> Optional.of([cheapestProductDao])
        result.getFirst().size() == 1
        result.getFirst()[0].price == 150
        result.getSecond().size() == 1
        result.getSecond()[0].price == 50
    }

    def "Product 생성 : 이미 존재하는 상품인 경우 예외 발생 ( Unique 제약 )"() {
        given:
        def product = new Product(price: 100)

        when:
        stub.createProduct(product)

        then:
        1 * productRepository.save(_ as Product) >> { throw new ProductAlreadyExistException("이미 존재하는 상품입니다.") }
        thrown(ProductAlreadyExistException)
    }

    def "Product 업데이트"() {
        given:
        def savedProduct = new Product(price: 100)
        def newProduct = new Product(price: 150)

        when:
        stub.updateProduct(savedProduct, newProduct)

        then:
        1 * productRepository.save(_ as Product) >> newProduct

    }

    def "제품을 삭제해야 한다"() {
        given:
        def product = new Product(price: 100)

        when:
        stub.deleteProduct(product)

        then:
        1 * productRepository.delete(_ as Product)
    }

    def "제품 존재 여부를 확인해야 한다"() {
        given:

        when:
        def result = stub.isExist(1, 1)

        then:
        1 * productRepository.findByCategoryIdAndBrandId(_ as Long, _ as Long) >> Optional.of(new Product())
        result == true
    }

    def "제품이 존재하지 않을 때 예외를 발생시켜야 한다"() {
        given:

        when:
        stub.findProductById(1, 1)

        then:
        1 * productRepository.findByCategoryIdAndBrandId(_ as Long, _ as Long) >> Optional.empty()
        
        thrown(NotFoundException)
    }
}
