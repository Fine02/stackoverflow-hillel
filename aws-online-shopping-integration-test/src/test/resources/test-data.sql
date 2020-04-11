
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

INSERT INTO sms_notification (phone ) VALUES ('+380559999999');
INSERT INTO sms_notification (phone ) VALUES ('+380555555555');
INSERT INTO sms_notification (phone ) VALUES ('+380557777777');

INSERT INTO email_notification (email ) VALUES ('jjjjjj@gmail.com');
INSERT INTO email_notification (email ) VALUES ('iiiiii@gmail.com');
INSERT INTO email_notification (email ) VALUES ('pppppp@gmail.com');

INSERT INTO notification (createdOn, content) VALUES ('2020-03-19 20:22:11', 'your order shipped');
INSERT INTO notification (createdOn, content) VALUES ('2020-03-20 21:22:11', 'your order canceled');
INSERT INTO notification (createdOn, content) VALUES ('2020-03-20 22:22:11', 'your order arrived');

INSERT INTO shipment (shipmentNumber, shipmentDate, estimatedArrival, shipmentMethod) VALUES ('1','2020-03-19 22:22:11', '2020-04-19 22:22:11','by air');
INSERT INTO shipment (shipmentNumber, shipmentDate, estimatedArrival, shipmentMethod) VALUES ('2','2020-03-20 22:22:11', '2020-04-19 22:22:11','by air');
INSERT INTO shipment (shipmentNumber, shipmentDate, estimatedArrival, shipmentMethod) VALUES ('3','2020-03-21 22:22:11', '2020-04-19 22:22:11','by air');

INSERT INTO shipment_log (shipmentNumber, shipment_status_id, creationDate, shipment_id) VALUES ('1',2,'2020-03-19 22:22:11',1);
INSERT INTO shipment_log (shipmentNumber, shipment_status_id, creationDate, shipment_id) VALUES ('2',2,'2020-03-20 22:22:11',2);
INSERT INTO shipment_log (shipmentNumber, shipment_status_id, creationDate, shipment_id) VALUES ('2',2,'2020-03-21 22:22:11',2);

INSERT INTO "order" (orderNumber, order_status_id, orderDate) VALUES ('1', 3,'2020-03-19 22:22:11');
INSERT INTO "order" (orderNumber, order_status_id, orderDate) VALUES ('2', 3,'2020-03-20 22:22:11');
INSERT INTO "order" (orderNumber, order_status_id, orderDate) VALUES ('3', 3,'2020-03-21 22:22:11');

INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES ('1','2020-03-19 22:22:11', 2,1);
INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES ('2','2020-03-20 22:22:11', 2,2);
INSERT INTO order_log (orderNumber, creationDate, order_status_id, order_id) VALUES ('2','2020-03-21 22:22:11', 2,2);

INSERT INTO payment (payment_status_id, amount) VALUES (1, 100.10);
INSERT INTO payment (payment_status_id, amount) VALUES (2, 200.10);
INSERT INTO payment (payment_status_id, amount) VALUES (3, 300.10);

INSERT INTO account (userName, password, account_status_id, name, address_id, email, phone) VALUES ('ivan','111', 1, 'ivan', 1, '111j@gmail.com', '38012345111');

INSERT INTO account (userName, password, account_status_id, name, address_id, email, phone) VALUES ('vasiliy','111', 2, 'vasiliy', 2, '222j@gmail.com', '38012345222');

INSERT INTO account (userName, password, account_status_id, name, address_id, email, phone) VALUES ('egor','111', 3, 'egor', 3, '333j@gmail.com', '38012345333');

INSERT INTO member (account_id) VALUES (1);
INSERT INTO member (account_id) VALUES (2);
INSERT INTO member (account_id) VALUES (3);

INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id) VALUES ('GermanBank', '5265', '8542',1);
INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id) VALUES ('UniversalBank', '5548', '1254',2);
INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id) VALUES ('AmericanExpress', '7777', '7415',3);

INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('VISA', '5584', '5662', 1, 1);
INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('MASTERCARD', '8545', '8554', 2, 2);
INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('VISA', '7777', '7415', 3, 3);




















