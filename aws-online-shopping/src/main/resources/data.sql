USE onlineshopping;

INSERT INTO `payment_status` (`status`) VALUES ('UNPAID');
INSERT INTO `payment_status` (`status`) VALUES ('PENDING');
INSERT INTO `payment_status` (`status`) VALUES ('COMPLETED');
INSERT INTO `payment_status` (`status`) VALUES ('FAILD');
INSERT INTO `payment_status` (`status`) VALUES ('DECLINED');
INSERT INTO `payment_status` (`status`) VALUES ('CANCELED');
INSERT INTO `payment_status` (`status`) VALUES ('ABANDONED');
INSERT INTO `payment_status` (`status`) VALUES ('SETTLING');
INSERT INTO `payment_status` (`status`) VALUES ('SETTLED');
INSERT INTO `payment_status` (`status`) VALUES ('REFUNDER');

INSERT INTO `shipment_status` (`status`) VALUES ('PENDING');
INSERT INTO `shipment_status` (`status`) VALUES ('SHIPPED');
INSERT INTO `shipment_status` (`status`) VALUES ('DELIVERED');
INSERT INTO `shipment_status` (`status`) VALUES ('ONHOLD');

INSERT INTO `account_status` (`status`) VALUES ('ACTIVE');
INSERT INTO `account_status` (`status`) VALUES ('BLOCKED');
INSERT INTO `account_status` (`status`) VALUES ('BANNED');
INSERT INTO `account_status` (`status`) VALUES ('COMPROMISED');
INSERT INTO `account_status` (`status`) VALUES ('ARCHIVED');
INSERT INTO `account_status` (`status`) VALUES ('UNKNOWN');

INSERT INTO `order_status` (`status`) VALUES ('UNSHIPPED');
INSERT INTO `order_status` (`status`) VALUES ('PENDING');
INSERT INTO `order_status` (`status`) VALUES ('SHIPPED');
INSERT INTO `order_status` (`status`) VALUES ('COMPLETE');
INSERT INTO `order_status` (`status`) VALUES ('CANCELED');
INSERT INTO `order_status` (`status`) VALUES ('REFUND_APPLIED');

INSERT INTO `address` (`streetAddress`,`city`, `state`, `zipcode`,`country` ) VALUES ('Mira, 8', 'Kyiv','Kyiv','14004', 'Ukraine');

INSERT INTO `sms_notification` (`phone` ) VALUES ('+380557777777');

INSERT INTO `email_notification` (`email` ) VALUES ('jjjjjj@gmail.com');

INSERT INTO `notification` (`createdOn`, `content`) VALUES ('2020-03-19 22:22:11', 'your order shipped');

INSERT INTO `shipment_log` (`shipmentNumber`, `shipment_status_id`, `creationDate`) VALUES ('111',2,'2020-03-19 22:22:11');

INSERT INTO `shipment` (`shipmentNumber`, `shipmentDate`, `estimatedArrival`, `shipmentMethod`,`shipment_logs_id`) VALUES ('22','2020-03-19 22:22:11', '2020-04-19 22:22:11','by air',1);

INSERT INTO `order_log` (`orderNumber`, `creationDate`, `order_status_id`) VALUES ('1','2020-03-19 22:22:11', 2);

INSERT INTO `order` (`orderNumber`, `order_status_id`, `orderDate`, `order_log_id`) VALUES ('1', 3,'2020-03-19 22:22:11', 1);

INSERT INTO `payment` (`payment_status_id`, `amount`) VALUES (1, 100.10);

INSERT INTO `electronic_bank_transfer` (`bankName`, `routingNumber`, `accountNumber`) VALUES ('AmericanExpress', '7777', '7415');

INSERT INTO `credit_card` (`nameOnCard`, `cardNumber`, `code`, `address_id`) VALUES ('VISA', '7777', '7415', 1);

INSERT INTO `account` (`userName`, `password`, `account_status_id`, `name`, `address_id`, `email`, `phone`, `creditCardList_id`, `electronicBankTransferList_id`) VALUES ('ivan1','111', 1, 'ivan', 1, 'jj@gmail.com', '380777777777', 1, 1);

INSERT INTO `member` (`account_id`) VALUES (1);









