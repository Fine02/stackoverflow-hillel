INSERT INTO product_categories VALUES (nextval('product_categories_seq'), 'Accessories', 'Accessories description');
INSERT INTO product_categories VALUES (nextval('product_categories_seq'), 'Fragnances', 'Fragnanses description');
INSERT INTO product_categories VALUES (nextval('product_categories_seq'), 'Shoes', 'Shoes description');

INSERT INTO products VALUES (nextval('products_seq'), 'Sunglasses', 'RayBan Wayfarer', 199.99, 11, (SELECT id from product_categories WHERE name='Accessories'));
INSERT INTO products VALUES (nextval('products_seq'), 'Sunglasses', 'RayBan Wayfarer', 199.99, 11, (SELECT id from product_categories WHERE name='Accessories'));
INSERT INTO products VALUES (nextval('products_seq'), 'Sunglasses', 'RayBan Wayfarer', 199.99, 11, (SELECT id from product_categories WHERE name='Accessories'));

INSERT INTO products VALUES (nextval('products_seq'), 'Sunglasses', 'RayBan Wayfarer', 199.99, 11, (SELECT id from product_categories WHERE name='Accessories'));
INSERT INTO products VALUES (nextval('products_seq'), 'Sunglasses', 'RayBan Aviator', 119.9, 15, (SELECT id from product_categories WHERE name='Accessories'));
INSERT INTO products VALUES (nextval('products_seq'), 'Sunglasses', 'RayBan Clubmaster', 249.9, 5, (SELECT id from product_categories WHERE name='Accessories'));








