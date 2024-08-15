CREATE TABLE category
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE

);

CREATE TABLE brand
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE

);

CREATE TABLE product
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT NOT NULL,
    brand_id    INT NOT NULL,
    price       DECIMAL(10, 2)

);
ALTER TABLE product
    ADD CONSTRAINT category_brand unique (category_id, brand_id);