INSERT INTO product_category (cat_name, cat_description) VALUES('Cameras', 'some description for cameras');
INSERT INTO product_category (cat_name, cat_description) VALUES ('Cell_phones', 'some description for cell phones');
INSERT INTO product_category (cat_name, cat_description) VALUES  ('Clothing', 'some description for clothing');
INSERT INTO product_category (cat_name, cat_description) VALUES  ('Music', 'some description for music');

INSERT INTO product (availableItemCount, name, description, price, product_category_id)
VALUES ('1','samsung smart camera', 'description for camera', '125.25', (select pr_cat_id from product_category where cat_name='Cameras')),
       ('10','apple iphone6s', 'description for iphone', '150.00', (select pr_cat_id from product_category where cat_name='Cell_phones')),
       ('20','apple iphone6s', 'description for iphone6s', '175.00', (select pr_cat_id from product_category where cat_name='Cell_phones')),
       ('59','samsung S9', 'description for S9', '350.00', (select pr_cat_id from product_category where cat_name='Cell_phones')),
       ('50','jeans', 'clothing for men', '30.00', (select pr_cat_id from product_category where cat_name='Clothing')),
       ('100','Queen', 'The Best Records', '99.00', (select pr_cat_id from product_category where cat_name='Music'));

INSERT INTO shopping_cart (member_id) VALUES(1);
INSERT INTO shopping_cart (member_id) VALUES(2);
INSERT INTO shopping_cart (member_id) VALUES(3);

INSERT INTO item (price, quantity, shopping_cart_id, product_id)
VALUES('175.00', '1', (select s_cart_id from shopping_cart where member_id =1), (select pr_id from product where price = '175.00' and name ='apple iphone6s'));
INSERT INTO item (price, quantity, shopping_cart_id, product_id)
VALUES  ('300.00', '2', (select s_cart_id from shopping_cart where member_id =2), (select pr_id from product where price = '150.00' and name ='apple iphone6s'));
INSERT INTO item (price, quantity, shopping_cart_id, product_id)
VALUES  ('150.00', '5', (select s_cart_id from shopping_cart where member_id =1), (select pr_id from product where name ='jeans'));
INSERT INTO item (price, quantity, shopping_cart_id, product_id)
VALUES  ('125.25', '1', (select s_cart_id from shopping_cart where member_id =1), (select pr_id from product where name ='samsung smart camera'));

INSERT INTO product_review (rating, review, product_id)
VALUES('7', 'some review', (select pr_id from product where name='apple iphone6s' and price = '175.00')),
      ('10', 'some review for samsung ', (select pr_id from product where name='samsung S9'));
INSERT INTO address (streetAddress, city, state, zipcode, country) VALUES ('Sobornosti, 4', 'Kyiv','Kyiv','14021', 'Ukraine');
INSERT INTO address (streetAddress, city, state, zipcode, country) VALUES ('Lugova, 10 ', 'Dnipro','Dnipro','34044', 'Ukraine');
INSERT INTO address (streetAddress, city, state, zipcode, country) VALUES ('Mira, 5 ', 'Lviv','Lviv','22004', 'Ukraine');

INSERT INTO account (userName, password, account_status, name, address_id, email, phone) VALUES ('Ivan021','1111', 'ACTIVE', 'Ivan', 1, '123@gmail.com', '380672554554');
INSERT INTO account (userName, password, account_status, name, address_id, email, phone) VALUES ('Rom_Ko','1234', 'ACTIVE', 'Roman', 2, '2555jl@gmail.com', '380504228800');
INSERT INTO account (userName, password, account_status, name, address_id, email, phone) VALUES ('ThirdAccount','12345', 'ACTIVE', 'Boris', 3, 'boris@gmail.com', '380505585565');

INSERT INTO member (account_id, shopping_cart_id) VALUES ((select id from account where userName='Ivan021'), (select s_cart_id from shopping_cart where member_id= 1));
INSERT INTO member (account_id, shopping_cart_id) VALUES ((select id from account where userName='Rom_Ko'), (select s_cart_id from shopping_cart where member_id= 2));
INSERT INTO member (account_id, shopping_cart_id) VALUES ((select id from account where userName='ThirdAccount'), (select s_cart_id from shopping_cart where member_id= 3));


INSERT INTO payment_status (status) VALUES ('UNPAID');
INSERT INTO payment_status (status) VALUES ('PENDING');
INSERT INTO payment_status (status) VALUES ('COMPLETED');
INSERT INTO payment_status (status) VALUES ('FAILD');
INSERT INTO payment_status (status) VALUES ('DECLINED');
INSERT INTO payment_status (status) VALUES ('CANCELED');
INSERT INTO payment_status (status) VALUES ('ABANDONED');
INSERT INTO payment_status (status) VALUES ('SETTLING');
INSERT INTO payment_status (status) VALUES ('SETTLED');
INSERT INTO payment_status (status) VALUES ('REFUNDER');

INSERT INTO shipment_status (status) VALUES ('PENDING');
INSERT INTO shipment_status (status) VALUES ('SHIPPED');
INSERT INTO shipment_status (status) VALUES ('DELIVERED');
INSERT INTO shipment_status (status) VALUES ('ONHOLD');

INSERT INTO account_status (status) VALUES ('ACTIVE');
INSERT INTO account_status (status) VALUES ('BLOCKED');
INSERT INTO account_status (status) VALUES ('BANNED');
INSERT INTO account_status (status) VALUES ('COMPROMISED');
INSERT INTO account_status (status) VALUES ('ARCHIVED');
INSERT INTO account_status (status) VALUES ('UNKNOWN');

INSERT INTO order_status (status) VALUES ('UNSHIPPED');
INSERT INTO order_status (status) VALUES ('PENDING');
INSERT INTO order_status (status) VALUES ('SHIPPED');
INSERT INTO order_status (status) VALUES ('COMPLETE');
INSERT INTO order_status (status) VALUES ('CANCELED');
INSERT INTO order_status (status) VALUES ('REFUND_APPLIED');

INSERT INTO address (streetAddress,city, state, zipcode,country ) VALUES ('Mira, 8', 'Kyiv','Kyiv','14004', 'Ukraine');
INSERT INTO address (streetAddress,city, state, zipcode,country ) VALUES ('Mira, 9', 'Kyiv','Kyiv','14004', 'Ukraine');
INSERT INTO address (streetAddress,city, state, zipcode,country ) VALUES ('Mira, 10', 'Kyiv','Kyiv','14004', 'Ukraine');

INSERT INTO notification (createdOn, content) VALUES ('2020-03-19 20:22:11', 'your order shipped');
INSERT INTO notification (createdOn, content) VALUES ('2020-03-20 21:22:11', 'your order canceled');
INSERT INTO notification (createdOn, content) VALUES ('2020-03-20 22:22:11', 'your order arrived');

INSERT INTO sms_notification (phone, notification_id ) VALUES ('+380559999999',1);
INSERT INTO sms_notification (phone, notification_id ) VALUES ('+380555555555',2);
INSERT INTO sms_notification (phone, notification_id ) VALUES ('+380557777777',3);

INSERT INTO email_notification (email, notification_id ) VALUES ('jjjjjj@gmail.com',1);
INSERT INTO email_notification (email, notification_id ) VALUES ('iiiiii@gmail.com',2);
INSERT INTO email_notification (email, notification_id ) VALUES ('pppppp@gmail.com',3);

INSERT INTO shipment (shipmentNumber, shipmentDate, estimatedArrival, shipmentMethod) VALUES ('1','2020-03-19 22:22:11', '2020-04-19 22:22:11','by air');
INSERT INTO shipment (shipmentNumber, shipmentDate, estimatedArrival, shipmentMethod) VALUES ('2','2020-03-20 22:22:11', '2020-04-19 22:22:11','by air');
INSERT INTO shipment (shipmentNumber, shipmentDate, estimatedArrival, shipmentMethod) VALUES ('3','2020-03-21 22:22:11', '2020-04-19 22:22:11','by air');

INSERT INTO shipment_log (shipmentNumber, shipment_status_id, creationDate, shipment_id) VALUES ('1',2,'2020-03-19 22:22:11',1);
INSERT INTO shipment_log (shipmentNumber, shipment_status_id, creationDate, shipment_id) VALUES ('2',2,'2020-03-20 22:22:11',2);
INSERT INTO shipment_log (shipmentNumber, shipment_status_id, creationDate, shipment_id) VALUES ('2',2,'2020-03-21 22:22:11',2);

INSERT INTO `order` (orderNumber, order_status_id, orderDate) VALUES ('1', 3,'2020-03-19 22:22:11');
INSERT INTO `order` (orderNumber, order_status_id, orderDate) VALUES ('2', 3,'2020-03-20 22:22:11');
INSERT INTO `order` (orderNumber, order_status_id, orderDate) VALUES ('3', 3,'2020-03-21 22:22:11');
INSERT INTO `order` (orderNumber, order_status_id, orderDate) VALUES ('4', 4,'2021-03-21 22:22:11');

INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES ('1','2020-03-19 22:22:11', 2,1);
INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES ('2','2020-03-20 22:22:11', 2,2);
INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES ('2','2020-03-21 22:22:11', 2,2);

INSERT INTO payment (payment_status_id, amount) VALUES (1, 100.10);
INSERT INTO payment (payment_status_id, amount) VALUES (2, 200.10);
INSERT INTO payment (payment_status_id, amount) VALUES (3, 300.10);

INSERT INTO account (userName, password, account_status_id, name, address_id, email, phone) VALUES ('ivan','111', 1, 'ivan', 1, '111j@gmail.com', '38012345111');

INSERT INTO account (userName, password, account_status_id, name, address_id, email, phone) VALUES ('vasiliy','111', 2, 'vasiliy', 2, '222j@gmail.com', '38012345222');

INSERT INTO account (userName, password, account_status_id, name, address_id, email, phone) VALUES ('egor','111', 3, 'egor', 3, '333j@gmail.com', '38012345333');

INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id) VALUES ('GermanBank', '5265', '8542',1);
INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id) VALUES ('UniversalBank', '5548', '1254',2);
INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id) VALUES ('AmericanExpress', '7777', '7415',3);

INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('VISA', '5584', '5662', 1, 1);
INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('MASTERCARD', '8545', '8554', 2, 2);
INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('VISA', '7777', '7415', 3, 3);

