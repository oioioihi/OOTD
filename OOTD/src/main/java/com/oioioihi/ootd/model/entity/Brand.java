package com.oioioihi.ootd.model.entity;


import com.oioioihi.ootd.model.dto.BrandDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public Brand(String name) {
        this.name = name;
    }

    private Brand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Brand createInstance(String name) {
        return new Brand(name);
    }

    public Brand update(BrandDto brandDto) {
        return Brand.builder()
                .id(this.id)
                .name(brandDto.getName())
                .build();
    }
}
