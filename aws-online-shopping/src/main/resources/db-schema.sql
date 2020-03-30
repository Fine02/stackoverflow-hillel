CREATE TABLE `payment_status`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`status` varchar (255) NOT NULL
};

CREATE TABLE `shipment_status`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`status` varchar (255) NOT NULL
};

CREATE TABLE `account_status`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`status` varchar (255) NOT NULL
};

CREATE TABLE `order_status`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`status` varchar (255) NOT NULL
};

CREATE TABLE `address`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`streetAddress` varchar (255) NOT NULL,
`city` varchar (255),
`state` varchar (255),
`zipcode` varchar (255),
`country` varchar (255)
};


CREATE TABLE `sms_notification`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`phone` varchar (255)
};

CREATE TABLE `email_notification`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`phone` varchar (255)
};

CREATE TABLE `notification`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`createdOn` DATE,
`content` varchar (255)
};

CREATE TABLE `shipment`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`shipmentNumber` varchar (255),
`shipmentDate` DATE,
`estimatedArrival` varchar (255),
`ShipmentMethod` varchar (255)
};

CREATE TABLE `shipment_log`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`shipmentNumber` varchar (255),
`shipment_status_id` INT NOT NULL,
`creationDate` DATE,
`shipment_id` INT NOT NULL,
CONSTRAINT  `fk_shipment_status`
FOREIGN KEY (`shipment_status_id`) REFERENCES `shipment_status` (`id`),
CONSTRAINT  `fk_shipment_id`
FOREIGN KEY (`shipment_id`) REFERENCES `Shipment` (`id`)
};

CREATE TABLE `order`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`orderNumber` varchar (255),
`order_status_id` INT NOT NULL,
`orderDate` DATE,
CONSTRAINT  `fk_order_status`
FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`)
};

CREATE TABLE `order_log`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`orderNumber` varchar (255),
`creationDate` DATE,
`order_status_id` INT NOT NULL,
`order_id` INT,
CONSTRAINT  `fk_order_status`
FOREIGN KEY (`order_status_id`) REFERENCES `order_status` (`id`),
CONSTRAINT  `fk_order_id`
FOREIGN KEY (`order_id`) REFERENCES `order` (`id`)
};

CREATE TABLE `payment`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`payment_status_id` INT NOT NULL,
`amount` DOUBLE NOT NULL,
CONSTRAINT  `fk_payment_status`
FOREIGN KEY (`payment_status_id`) REFERENCES `payment_status` (`id`)
};

CREATE TABLE `electronic_bank_transfer`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`bankName` varchar (255) NOT NULL,
`routingNumber` varchar (255) NOT NULL,
`accountNumber` varchar (255) NOT NULL
`account_id` INT NOT NULL,
CONSTRAINT  `fk_account_id`
FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
};

CREATE TABLE `credit_card`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`nameOnCard` varchar (255) NOT NULL,
`cardNumber` varchar (255) NOT NULL,
`code` INT NOT NULL,
`address_id` INT NOT NULL,
`account_id` INT NOT NULL,
CONSTRAINT  `fk_address_id`
FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
CONSTRAINT  `fk_account_id`
FOREIGN KEY (`account_id`) REFERENCES `account` (`id`)
};

CREATE TABLE `account`
{
`id` INT PRIMARY KEY AUTO_INCREMENT,
`userName` varchar (255) NOT NULL,
`password` varchar (255) NOT NULL,
`account_status_id` INT NOT NULL,
`name` varchar (255) NOT NULL,
`address_status_id` INT NOT NULL,
`email` varchar (255) NOT NULL,
`phone` varchar (255) NOT NULL,
CONSTRAINT  `fk_address_id`
FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
};