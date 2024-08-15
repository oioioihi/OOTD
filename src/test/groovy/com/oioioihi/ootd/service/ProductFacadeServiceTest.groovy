package com.oioioihi.ootd.service

import com.oioioihi.ootd.exception.ProductAlreadyExistException
import com.oioioihi.ootd.model.dao.ProductDao
import com.oioioihi.ootd.model.dto.request.ProductCreateDto
import com.oioioihi.ootd.model.dto.request.ProductUpdateDto
import com.oioioihi.ootd.model.entity.Brand
import com.oioioihi.ootd.model.entity.Category
import com.oioioihi.ootd.model.entity.Product
import org.springframework.data.util.Pair
import spock.lang.Specification
import spock.lang.Subject

class ProductFacadeServiceTest extends Specification {

    ProductService productService = Mock()
    CategoryService categoryService = Mock()
    BrandService brandService = Mock()

    @Subject
    ProductFacadeService stub = new ProductFacadeService(productService, categoryService, brandService)

    def "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회"() {
        given:
        def productDao = new ProductDao(price: 100, brand: "BrandA", category: "CategoryA")

        when:
        def result = stub.getMinPriceProducts()

        then:
        1 * productService.getMinPriceProducts() >> [productDao]
        result.productList.size() == 1
        result.totalPrice == 100
        result.productList[0].price == 100
        result.productList[0].brand == "BrandA"
    }

    def "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회"() {
        given:
        def productDao = new ProductDao(price: 100, brand: "BrandA", brandId: 1)
        def products = [new Product(price: 100, category: new Category(name: "CategoryA"))]

        when:
        def result = stub.getCheapestProductsByBrand()

        then:
        1 * productService.getCheapestProductsByBrand() >> productDao
        1 * productService.findAllByBrandId(_ as Long) >> products
        result.brand == "BrandA"
        result.totalPrice == 100
        result.productList.size() == 1
        result.productList[0].price == 100
        result.productList[0].category == "CategoryA"
    }

    def "category별 가장 저렴한 상품 조회"() {
        given:
        def category = new Category(name: "CategoryA", id: 1)
        def minProduct = new ProductDao(price: 50, brand: "BrandA")
        def maxProduct = new ProductDao(price: 150, brand: "BrandB")
        def products = Pair.of([maxProduct], [minProduct])

        when:
        def result = stub.findMinAndMaxPriceProductByCategoryName("CategoryA")

        then:
        1 * categoryService.findCategoryByName(_ as String) >> category
        1 * productService.findMinAndMaxProductByCategoryName(_ as Long) >> products
        result.categoryName == "CategoryA"
        result.cheapestProduct.size() == 1
        result.cheapestProduct[0].price == 50
        result.mostExpensiveProduct.size() == 1
        result.mostExpensiveProduct[0].price == 150
    }

    def "Product 등록 : 성공"() {
        given:
        def productCreateDto = ProductCreateDto.builder().
                category("CategoryA")
                .brand("BrandA")
                .price(100)
                .build()
        def category = new Category(name: "CategoryA", id: 1)
        def brand = new Brand(name: "BrandA", id: 1)
        def product = new Product(price: 100, category: category, brand: brand)

        when:
        def result = stub.createProduct(productCreateDto)

        then:
        1 * productService.isExist(_ as Long, _ as Long) >> false
        1 * categoryService.findCategoryByName(_ as String) >> category
        1 * brandService.findBrandByName(_ as String) >> brand
        1 * productService.createProduct(_ as Product) >> product
        result.price == 100
        result.brand == "BrandA"
        result.category == "CategoryA"
    }

    def "Product 등록 : 이미 Product가 존재하는 경우"() {
        given:
        def productCreateDto = ProductCreateDto.builder().
                category("CategoryA")
                .brand("BrandA")
                .price(100)
                .build()
        def category = new Category(name: "CategoryA", id: 1)
        def brand = new Brand(name: "BrandA", id: 1)


        when:
        stub.createProduct(productCreateDto)

        then:
        1 * productService.isExist(_ as Long, _ as Long) >> true
        1 * categoryService.findCategoryByName(_ as String) >> category
        1 * brandService.findBrandByName(_ as String) >> brand
        0 * productService.createProduct(_ as Product)
        thrown(ProductAlreadyExistException)
    }

    def "Product 업데이트"() {
        given:
        def productUpdateDto = ProductUpdateDto.builder().
                oldCategory("CategoryA")
                .oldBrand("BrandA")
                .newCategory("CategoryB")
                .newBrand("BrandB")
                .price(100)
                .build()
        def oldCategory = new Category(name: productUpdateDto.oldCategory, id: 1)
        def oldBrand = new Brand(name: productUpdateDto.oldBrand, id: 1)
        def savedProduct = new Product(price: 100, category: oldCategory, brand: oldBrand)
        def newCategory = new Category(name: productUpdateDto.newCategory, id: 2)
        def newBrand = new Brand(name: productUpdateDto.newBrand, id: 2)


        when:
        stub.updateProduct(productUpdateDto)

        then:
        2 * categoryService.findCategoryByName(_ as String) >> newCategory
        2 * brandService.findBrandByName(_ as String) >> newBrand
        2 * productService.findProductById(_ as Long, _ as Long) >> savedProduct
        1 * productService.updateProduct(_ as Product, _ as Product)
        1 * productService.deleteProduct(_ as Product)

    }

    def "Product 삭제"() {
        given:
        def category = new Category(name: "CategoryA", id: 1)
        def brand = new Brand(name: "BrandA", id: 1)
        def product = new Product(price: 100, category: category, brand: brand)

        when:
        stub.deleteProduct("CategoryA", "BrandA")

        then:
        1 * categoryService.findCategoryByName(_ as String) >> category
        1 * brandService.findBrandByName(_ as String) >> brand
        1 * productService.findProductById(_ as Long, _ as Long) >> product
        1 * productService.deleteProduct(_ as Product)
    }
}
