
CREATE TABLE `payment_status`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`status` varchar (255) NOT NULL
);

CREATE TABLE `shipment_status`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`status` varchar (255) NOT NULL
);

CREATE TABLE `account_status`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`status` varchar (255) NOT NULL
);

CREATE TABLE `order_status`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`status` varchar (255) NOT NULL
);

CREATE TABLE `address`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`streetAddress` varchar (255) NOT NULL,
`city` varchar (255) NOT NULL,
`state` varchar (255) NOT NULL,
`zipcode` varchar (255) NOT NULL,
`country` varchar (255) NOT NULL
);

CREATE TABLE `sms_notification`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`phone` varchar (255) NOT NULL
);

CREATE TABLE `email_notification`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`email` varchar (255) NOT NULL
);

CREATE TABLE `notification`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`createdOn` DATETIME NOT NULL ,
`content` varchar (255) NOT NULL
);

CREATE TABLE `shipment`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`shipmentNumber` varchar (255) NOT NULL,
`shipmentDate` DATETIME NOT NULL,
`estimatedArrival` DATETIME NOT NULL,
`shipmentMethod` varchar (255) NOT NULL
);

CREATE TABLE `shipment_log`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`shipmentNumber` varchar (255) NOT NULL,
`shipment_status_id` INT NOT NULL,
`creationDate` DATETIME NOT NULL,
`shipment_id` INT NOT NULL,
CONSTRAINT  `fk_shipment_status`
FOREIGN KEY (`shipment_status_id`) REFERENCES `shipment_status` (`id`),
CONSTRAINT  `fk_shipment_id`
FOREIGN KEY (`shipment_id`) REFERENCES `Shipment` (`id`) ON DELETE CASCADE
);

CREATE TABLE `order`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`orderNumber` varchar (255) NOT NULL,
`order_status_id` INT NOT NULL,
`orderDate` DATETIME NOT NULL,
CONSTRAINT  `fk_order_status`
FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`)
);

CREATE TABLE `order_log`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`orderNumber` varchar (255) NOT NULL,
`creationDate` DATETIME NOT NULL,
`order_status_id` INT NOT NULL,
`order_id` INT NOT NULL,
CONSTRAINT  `order_status_fk`
FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`),
CONSTRAINT  `order_id_fk`
FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE
);


CREATE TABLE `payment`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`payment_status_id` INT NOT NULL,
`amount` DOUBLE NOT NULL,
CONSTRAINT  `fk_payment_status`
FOREIGN KEY (`payment_status_id`) REFERENCES `payment_status` (`id`)
);


CREATE TABLE `account`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`userName` varchar (255) NOT NULL,
`password` varchar (255) NOT NULL,
`account_status_id` INT NOT NULL,
`name` varchar (255) NOT NULL,
`address_id` INT NOT NULL,
`email` varchar (255) NOT NULL,
`phone` varchar (255) NOT NULL,
CONSTRAINT  `fk_account_status_id`
FOREIGN KEY (`account_status_id`) REFERENCES `account_status` (`id`),
CONSTRAINT  `fk_address_id`
FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
);


CREATE TABLE `member`
(
`account_id` INT NOT NULL,
`id` INT PRIMARY KEY AUTO_INCREMENT,
CONSTRAINT  `fk_account_id`
FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
);


CREATE TABLE `electronic_bank_transfer`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`bankName` varchar (255) NOT NULL,
`routingNumber` varchar (255) NOT NULL,
`accountNumber` varchar (255) NOT NULL,
`account_id` INT NOT NULL,
CONSTRAINT  `account_id_fk`
FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
);

CREATE TABLE `credit_card`
(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`nameOnCard` varchar (255) NOT NULL,
`cardNumber` varchar (255) NOT NULL,
`code` INT NOT NULL,
`address_id` INT NOT NULL,
`account_id` INT NOT NULL,
CONSTRAINT  `address_id_fk`
FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
CONSTRAINT  `fk_account_id_cc`
FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
);
