DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS product_categories CASCADE;

DROP SEQUENCE IF EXISTS products_seq CASCADE;
DROP SEQUENCE IF EXISTS product_categories_seq CASCADE;

CREATE SEQUENCE product_categories_seq;

CREATE TABLE product_categories
(
    id          INT PRIMARY KEY DEFAULT NEXTVAL('product_categories_seq'),
    name        VARCHAR(400) NOT NULL,
    description VARCHAR(400)
);

CREATE SEQUENCE products_seq;

CREATE TABLE products
(
    id                 INT PRIMARY KEY DEFAULT NEXTVAL('products_seq'),
    name               VARCHAR(400) NOT NULL,
    description        VARCHAR(400),
    price              FLOAT        NOT NULL,
    availableItemCount INT          NOT NULL,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES product_categories (id)
);





