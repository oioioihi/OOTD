package com.oioioihi.ootd.service

import com.oioioihi.ootd.exception.CategoryAlreadyExistException
import com.oioioihi.ootd.exception.NotFoundException
import com.oioioihi.ootd.model.dto.CategoryDto
import com.oioioihi.ootd.model.entity.Category
import com.oioioihi.ootd.repository.CategoryRepository
import spock.lang.Specification
import spock.lang.Subject

class CategoryServiceTest extends Specification {

    def categoryRepository = Mock(CategoryRepository)

    @Subject
    CategoryService categoryService = new CategoryService(categoryRepository)

    def "Category 이름으로 조회"() {
        given:
        def category = new Category(name: "categoryA")

        when:
        def result = categoryService.findCategoryByName("categoryA")

        then:
        1 * categoryRepository.findByName(_ as String) >> Optional.of(category)
        result.name == "categoryA"
    }

    def "Category 조회 : 존재하지 않은 카테고리인 경우"() {
        given:

        when:
        categoryService.findCategoryByName("NonExistingCategory")

        then:
        1 * categoryRepository.findByName(_ as String) >> Optional.empty()
        thrown(NotFoundException)
    }

    def "Category 생성"() {
        given:
        def categoryDto = new CategoryDto(name: "NewCategory")
        def category = new Category(name: "NewCategory")

        when:
        def result = categoryService.createCategory(categoryDto)

        then:
        1 * categoryRepository.findByName(_ as String) >> Optional.empty()
        1 * categoryRepository.save(_ as Category) >> category
        result.name == "NewCategory"
    }

    def "Category 생성 : 이미 존재하는 카테고리인 경우"() {
        given:
        def categoryDto = new CategoryDto(name: "ExistingCategory")

        when:
        categoryService.createCategory(categoryDto)

        then:
        1 * categoryRepository.findByName(_ as String) >> Optional.of(new Category(name: "ExistingCategory"))
        thrown(CategoryAlreadyExistException)
    }

    def "Category 업데이트"() {
        given:
        def categoryDto = new CategoryDto(name: "UpdatedCategory")
        def existingCategory = new Category(name: "OldCategory")
        def updatedCategory = new Category(name: "UpdatedCategory")

        when:
        categoryService.updateCategory(1, categoryDto)

        then:
        1 * categoryRepository.findByName(_ as String) >> Optional.empty()
        1 * categoryRepository.findById(_ as Long) >> Optional.of(existingCategory)
        1 * categoryRepository.save(_ as Category) >> updatedCategory

    }

    def "Category 업데이트 : 이미 존재하는 카테고리인 경우"() {
        given:
        def categoryDto = new CategoryDto(name: "ExistingCategory")
        def existingCategory = new Category(name: "ExistingCategory")

        when:
        categoryService.updateCategory(1, categoryDto)

        then:
        1 * categoryRepository.findByName(_ as String) >> Optional.of(existingCategory)
        0 * categoryRepository.findById(_ as Long) >> Optional.of(new Category(name: "OldCategory"))
        thrown(CategoryAlreadyExistException)
    }

    def "Category 업데이트 : 존재하지 않는 카테고리 ID"() {
        given:
        def categoryDto = new CategoryDto(name: "NewCategory")

        when:
        categoryService.updateCategory(1, categoryDto)

        then:
        1 * categoryRepository.findByName(_ as String) >> Optional.empty()
        1 * categoryRepository.findById(_ as Long) >> Optional.empty()
        thrown(NotFoundException)
    }

    def "Category 삭제"() {
        given:
        def category = new Category(name: "CategoryToDelete")

        when:
        categoryService.deleteCategory(1)

        then:
        1 * categoryRepository.findById(_ as Long) >> Optional.of(category)
        1 * categoryRepository.delete(category)
    }

    def "Category 삭제 : 존재하지 않는 카테고리 ID"() {
        given:

        when:
        categoryService.deleteCategory(1)

        then:
        1 * categoryRepository.findById(_ as Long) >> Optional.empty()
        thrown(NotFoundException)
    }
}
