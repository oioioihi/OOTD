package com.oioioihi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Messages {
    public static final String BRAND_ALREADY_EXIST = "%s은 이미 존재하는 브랜드입니다.";
    public static final String BRAND_NOT_EXIST = "%s번 브랜드가 존재하지 않습니다.";
    public static final String BRAND_NAME_NOT_EXIST = "%s 브랜드가 없습니다.";
    public static final String CATEGORY_NOT_EXIST = "%s번 카테고리가 존재하지 않습니다.";
    public static final String CATEGORY_ALREADY_EXIST = "%s은 이미 존재하는 카테고리입니다.";
    public static final String CATEGORY_NAME_NOT_EXIST ="%s 카테고리가 없습니다.";
    public static final String BAD_REQUEST = "입력하신 값을 다시 확인해주세요.";
    public static final String PRODUCT_NOT_EXIST = "존재하지 않은 상품입니다.";
    public static final String PRODUCT_NOT_EXIST_BY_CATEGORY = "%s번 카테고리에 존재하는 상품이 없습니다.";
    public static final String PRODUCT_NOT_EXIST_CATEGORY = "해당 카테고리에 존재하는 상품이 없습니다";
    public static final String PRODUCT_CONDITION_NOT_EXIST = "일치하는 조건의 상품이 없습니다";
    public static final String PRODUCT_ALREADY_EXIST ="이미 존재하는 상품입니다.";
    public static final String PRICE_MUST_OVER_THAN_ZERO = "가격은 0 이상이어야 합니다.";


}
