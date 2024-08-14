package com.oioioihi.ootd.model.entity;

import com.oioioihi.ootd.model.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public static Category createInstance(String name) {
        return new Category(name);
    }

    public Category update(CategoryDto categoryDto) {
        return Category.builder()
                .id(this.id)
                .name(categoryDto.getName())
                .build();
    }
}
