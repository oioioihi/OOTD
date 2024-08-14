CREATE TABLE category
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE brand
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE product
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT,
    brand_id    INT,
    price       DECIMAL(10, 2)
        UNIQUE (category_id, brand_id)


);

-- 카테고리 데이터 삽입
INSERT INTO category (name)
VALUES ('상의');
INSERT INTO category (name)
VALUES ('아우터');
INSERT INTO category (name)
VALUES ('바지');
INSERT INTO category (name)
VALUES ('스니커즈');
INSERT INTO category (name)
VALUES ('가방');
INSERT INTO category (name)
VALUES ('모자');
INSERT INTO category (name)
VALUES ('양말');
INSERT INTO category (name)
VALUES ('액세서리');

-- 브랜드 데이터 삽입
INSERT INTO brand (name)
VALUES ('A');
INSERT INTO brand (name)
VALUES ('B');
INSERT INTO brand (name)
VALUES ('C');
INSERT INTO brand (name)
VALUES ('D');
INSERT INTO brand (name)
VALUES ('E');
INSERT INTO brand (name)
VALUES ('F');
INSERT INTO brand (name)
VALUES ('G');
INSERT INTO brand (name)
VALUES ('H');
INSERT INTO brand (name)
VALUES ('I');

-- 제품 데이터 삽입
-- A 브랜드
INSERT INTO product (category_id, brand_id, price)
VALUES (1, 1, 11200); -- 상의
INSERT INTO product (category_id, brand_id, price)
VALUES (2, 1, 5500); -- 아우터
INSERT INTO product (category_id, brand_id, price)
VALUES (3, 1, 4200); -- 바지
INSERT INTO product (category_id, brand_id, price)
VALUES (4, 1, 9000); -- 스니커즈
INSERT INTO product (category_id, brand_id, price)
VALUES (5, 1, 2000); -- 가방
INSERT INTO product (category_id, brand_id, price)
VALUES (6, 1, 1700); -- 모자
INSERT INTO product (category_id, brand_id, price)
VALUES (7, 1, 1800); -- 양말
INSERT INTO product (category_id, brand_id, price)
VALUES (8, 1, 2300);
-- 액세서리

-- B 브랜드
INSERT INTO product (category_id, brand_id, price)
VALUES (1, 2, 10500);
INSERT INTO product (category_id, brand_id, price)
VALUES (2, 2, 5900);
INSERT INTO product (category_id, brand_id, price)
VALUES (3, 2, 3800);
INSERT INTO product (category_id, brand_id, price)
VALUES (4, 2, 9100);
INSERT INTO product (category_id, brand_id, price)
VALUES (5, 2, 2100);
INSERT INTO product (category_id, brand_id, price)
VALUES (6, 2, 2000);
INSERT INTO product (category_id, brand_id, price)
VALUES (7, 2, 2000);
INSERT INTO product (category_id, brand_id, price)
VALUES (8, 2, 2200);

-- C 브랜드
INSERT INTO product (category_id, brand_id, price)
VALUES (1, 3, 10000);
INSERT INTO product (category_id, brand_id, price)
VALUES (2, 3, 6200);
INSERT INTO product (category_id, brand_id, price)
VALUES (3, 3, 3300);
INSERT INTO product (category_id, brand_id, price)
VALUES (4, 3, 9200);
INSERT INTO product (category_id, brand_id, price)
VALUES (5, 3, 2200);
INSERT INTO product (category_id, brand_id, price)
VALUES (6, 3, 1900);
INSERT INTO product (category_id, brand_id, price)
VALUES (7, 3, 2200);
INSERT INTO product (category_id, brand_id, price)
VALUES (8, 3, 2100);

-- D 브랜드
INSERT INTO product (category_id, brand_id, price)
VALUES (1, 4, 10100);
INSERT INTO product (category_id, brand_id, price)
VALUES (2, 4, 5100);
INSERT INTO product (category_id, brand_id, price)
VALUES (3, 4, 3000);
INSERT INTO product (category_id, brand_id, price)
VALUES (4, 4, 9500);
INSERT INTO product (category_id, brand_id, price)
VALUES (5, 4, 2500);
INSERT INTO product (category_id, brand_id, price)
VALUES (6, 4, 1500);
INSERT INTO product (category_id, brand_id, price)
VALUES (7, 4, 2400);
INSERT INTO product (category_id, brand_id, price)
VALUES (8, 4, 2000);

-- E 브랜드
INSERT INTO product (category_id, brand_id, price)
VALUES (1, 5, 10700);
INSERT INTO product (category_id, brand_id, price)
VALUES (2, 5, 5000);
INSERT INTO product (category_id, brand_id, price)
VALUES (3, 5, 3800);
INSERT INTO product (category_id, brand_id, price)
VALUES (4, 5, 9900);
INSERT INTO product (category_id, brand_id, price)
VALUES (5, 5, 2300);
INSERT INTO product (category_id, brand_id, price)
VALUES (6, 5, 1800);
INSERT INTO product (category_id, brand_id, price)
VALUES (7, 5, 2100);
INSERT INTO product (category_id, brand_id, price)
VALUES (8, 5, 2100);

-- F 브랜드
INSERT INTO product (category_id, brand_id, price)
VALUES (1, 6, 11200);
INSERT INTO product (category_id, brand_id, price)
VALUES (2, 6, 7200);
INSERT INTO product (category_id, brand_id, price)
VALUES (3, 6, 4000);
INSERT INTO product (category_id, brand_id, price)
VALUES (4, 6, 9300);
INSERT INTO product (category_id, brand_id, price)
VALUES (5, 6, 2100);
INSERT INTO product (category_id, brand_id, price)
VALUES (6, 6, 1600);
INSERT INTO product (category_id, brand_id, price)
VALUES (7, 6, 2300);
INSERT INTO product (category_id, brand_id, price)
VALUES (8, 6, 1900);

-- G 브랜드
INSERT INTO product (category_id, brand_id, price)
VALUES (1, 7, 10500);
INSERT INTO product (category_id, brand_id, price)
VALUES (2, 7, 5800);
INSERT INTO product (category_id, brand_id, price)
VALUES (3, 7, 3900);
INSERT INTO product (category_id, brand_id, price)
VALUES (4, 7, 9000);
INSERT INTO product (category_id, brand_id, price)
VALUES (5, 7, 2200);
INSERT INTO product (category_id, brand_id, price)
VALUES (6, 7, 1700);
INSERT INTO product (category_id, brand_id, price)
VALUES (7, 7, 2100);
INSERT INTO product (category_id, brand_id, price)
VALUES (8, 7, 2000);

-- H 브랜드
INSERT INTO product (category_id, brand_id, price)
VALUES (1, 8, 10800);
INSERT INTO product (category_id, brand_id, price)
VALUES (2, 8, 6300);
INSERT INTO product (category_id, brand_id, price)
VALUES (3, 8, 3100);
INSERT INTO product (category_id, brand_id, price)
VALUES (4, 8, 9700);
INSERT INTO product (category_id, brand_id, price)
VALUES (5, 8, 2100);
INSERT INTO product (category_id, brand_id, price)
VALUES (6, 8, 1600);
INSERT INTO product (category_id, brand_id, price)
VALUES (7, 8, 2000);
INSERT INTO product (category_id, brand_id, price)
VALUES (8, 8, 2000);

-- I 브랜드
INSERT INTO product (category_id, brand_id, price)
VALUES (1, 9, 11400);
INSERT INTO product (category_id, brand_id, price)
VALUES (2, 9, 6700);
INSERT INTO product (category_id, brand_id, price)
VALUES (3, 9, 3200);
INSERT INTO product (category_id, brand_id, price)
VALUES (4, 9, 9500);
INSERT INTO product (category_id, brand_id, price)
VALUES (5, 9, 2400);
INSERT INTO product (category_id, brand_id, price)
VALUES (6, 9, 1700);
INSERT INTO product (category_id, brand_id, price)
VALUES (7, 9, 1700);
INSERT INTO product (category_id, brand_id, price)
VALUES (8, 9, 2400);
