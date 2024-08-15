package com.oioioihi.ootd.service

import com.oioioihi.ootd.exception.BrandAlreadyExistException
import com.oioioihi.ootd.exception.NotFoundException
import com.oioioihi.ootd.model.dto.BrandDto
import com.oioioihi.ootd.model.entity.Brand
import com.oioioihi.ootd.repository.BrandRepository
import spock.lang.Specification
import spock.lang.Subject

class BrandServiceTest extends Specification {

    def brandRepository = Mock(BrandRepository)

    @Subject
    BrandService brandService = new BrandService(brandRepository)

    def "Brand 생성"() {
        given:
        def brandDto = new BrandDto(name: "NewBrand")
        def brand = new Brand(name: "NewBrand")

        when:
        def result = brandService.createBrand(brandDto)

        then:
        1 * brandRepository.findByName(_ as String) >> Optional.empty()
        1 * brandRepository.save(_ as Brand) >> brand
        result.name == "NewBrand"
    }

    def " Brand 생성 : 이미 존재하는 브랜드로 생성"() {
        given:
        def brandDto = new BrandDto(name: "ExistingBrand")

        when:
        brandService.createBrand(brandDto)

        then:
        1 * brandRepository.findByName(_ as String) >> Optional.of(new Brand(name: "ExistingBrand"))
        thrown(BrandAlreadyExistException)
    }

    def "Brand 업데이트"() {
        given:
        def brandDto = new BrandDto(name: "UpdatedBrand")
        def existingBrand = new Brand(name: "OldBrand")
        def updatedBrand = new Brand(name: "UpdatedBrand")

        when:
        brandService.updateBrand(1, brandDto)

        then:
        1 * brandRepository.findByName(_ as String) >> Optional.empty()
        1 * brandRepository.findById(_ as Long) >> Optional.of(existingBrand)
        1 * brandRepository.save(_ as Brand) >> updatedBrand
    }


    def "Brand 업데이트 : 이미 존재하는 브랜드"() {
        given:
        def brandDto = new BrandDto(name: "ExistingBrand")
        def existingBrand = new Brand(name: "ExistingBrand")

        when:
        brandService.updateBrand(1, brandDto)

        then:
        1 * brandRepository.findByName(_ as String) >> Optional.of(existingBrand)
        0 * brandRepository.findById(_ as Long)
        thrown(BrandAlreadyExistException)
    }

    def "Brand 업데이트 : 존재하지 않는 브랜드 ID"() {
        given:
        def brandDto = new BrandDto(name: "NewBrand")


        when:
        brandService.updateBrand(1, brandDto)

        then:
        1 * brandRepository.findByName(_ as String) >> Optional.empty()
        1 * brandRepository.findById(_ as Long) >> Optional.empty()
        thrown(NotFoundException)
    }

    def "Brand 삭제"() {
        given:
        def brand = new Brand(name: "BrandToDelete")

        when:
        brandService.deleteBrand(1)

        then:
        1 * brandRepository.findById(1) >> Optional.of(brand)
        1 * brandRepository.delete(_ as Brand)
    }

    def "Brand 삭제 : 존재하지 않는 브랜드 ID"() {
        given:

        when:
        brandService.deleteBrand(1)

        then:
        1 * brandRepository.findById(_ as Long) >> Optional.empty()
        thrown(NotFoundException)
    }

    def "Brand 이름으로 조회"() {
        given:
        def brand = new Brand(name: "ExistingBrand")

        when:
        def result = brandService.findBrandByName("ExistingBrand")

        then:
        1 * brandRepository.findByName(_ as String) >> Optional.of(brand)
        result.name == "ExistingBrand"
    }

    def "Brand 이름으로 조회 : 존재하지 않는 브랜드 이름"() {
        given:

        when:
        brandService.findBrandByName("NonExistingBrand")

        then:
        1 * brandRepository.findByName(_ as String) >> Optional.empty()
        thrown(NotFoundException)
    }
}
