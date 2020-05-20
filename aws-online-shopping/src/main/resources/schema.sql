CREATE SCHEMA IF NOT EXISTS shopping;
USE shopping;

DROP TABLE IF EXISTS payment_status, shipment_status, account_status, order_status,
    address, notification, `sms_notification`, email_notification, shipment, shipment_log,
    `order`, order_log, payment, account, member, electronic_bank_transfer, credit_card,
    electronic_bank_transaction, credit_card_transaction, billing_address, product_category, product,
    shopping_cart, item, product_review;

create table product_category
(
    pr_cat_id IDENTITY NOT NULL PRIMARY KEY,
    cat_name varchar(255) not null,
    cat_description varchar(255),
    constraint UK_category_name unique (cat_name)

);
create table product
(
    pr_id IDENTITY NOT NULL PRIMARY KEY,
    availableItemCount int not null,
    name varchar(255) not null,
    description varchar(255),
    price float not null,
    product_category_id int not null,
    constraint FK_product_category_id
        foreign key (product_category_id)
            references product_category(pr_cat_id) ON DELETE CASCADE
);

create table shopping_cart
(
    s_cart_id IDENTITY NOT NULL PRIMARY KEY,
    member_id int not null,
    constraint UK_member_id unique (member_id)
);
create table item
(
    item_id IDENTITY NOT NULL PRIMARY KEY,
    price float not null,
    quantity int not null,
    shopping_cart_id int not null,
    product_id int not null,
    constraint FK_shopping_cart_id
        foreign key (shopping_cart_id)
            references shopping_cart(s_cart_id) ON DELETE CASCADE,
    constraint FK_item_product_id
        foreign key (product_id)
            references product(pr_id) ON DELETE CASCADE

);

create table product_review
(
    pr_rev_id IDENTITY NOT NULL PRIMARY KEY,
    rating int not null,
    review varchar(255),
    product_id int not null,
    constraint FK_product_id
        foreign key (product_id)
            references product(pr_id) ON DELETE CASCADE
);

CREATE TABLE payment_status
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    status varchar (255) NOT NULL
);

CREATE TABLE shipment_status
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    status varchar (255) NOT NULL
);

CREATE TABLE account_status
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    status varchar (255) NOT NULL
);

CREATE TABLE order_status
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    status varchar (255) NOT NULL
);



CREATE TABLE account
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    userName varchar (255) NOT NULL,
    password varchar (255) NOT NULL,
    account_status_id INT,
    account_status varchar(255),
    name varchar (255) NOT NULL,
    address_id INT,
    email varchar (255) NOT NULL,
    phone varchar (255) NOT NULL
);

CREATE TABLE address
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    streetAddress varchar(255) NOT NULL,
    city          varchar(255) NOT NULL,
    state         varchar(255) NOT NULL,
    zipcode       varchar(255) NOT NULL,
    country       varchar(255) NOT NULL,
    account_id    INT,
    CONSTRAINT fk_account_id1
        FOREIGN KEY (account_id) REFERENCES account(id)
            ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE member
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT NOT NULL,
    shopping_cart_id INT NOT NULL,
    constraint FK_account_id
        foreign key (account_id)
            references account(id),
    constraint FK_shop_cart_id
        foreign key (shopping_cart_id)
            references shopping_cart (s_cart_id)
);



CREATE TABLE notification
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    createdOn DATETIME NOT NULL ,
    content varchar (255) NOT NULL
);

CREATE TABLE `sms_notification`
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    `phone` varchar (255) NOT NULL,
    `notification_id` INT NOT NULL,
    CONSTRAINT  `fk_sms_notification_id`
        FOREIGN KEY (`notification_id`) REFERENCES notification (`id`) ON DELETE CASCADE
);

CREATE TABLE email_notification
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    email varchar (255) NOT NULL,
    notification_id INT NOT NULL,
    CONSTRAINT  fk_email_notification_id
        FOREIGN KEY (notification_id) REFERENCES notification (`id`) ON DELETE CASCADE
);

CREATE TABLE shipment
(
    `id` INT PRIMARY KEY AUTO_INCREMENT,
    shipmentNumber varchar (255) NOT NULL,
    shipmentDate DATETIME NOT NULL,
    estimatedArrival DATETIME NOT NULL,
    shipmentMethod varchar (255) NOT NULL
);

CREATE TABLE shipment_log
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    shipmentNumber varchar (255) NOT NULL,
    shipment_status_id INT NOT NULL,
    creationDate DATETIME NOT NULL,
    shipment_id INT NOT NULL,
    CONSTRAINT  fk_shipment_status
        FOREIGN KEY (shipment_status_id) REFERENCES shipment_status (id),
    CONSTRAINT  fk_shipment_id
        FOREIGN KEY (shipment_id) REFERENCES shipment (`id`) ON DELETE CASCADE
);

CREATE TABLE `order`
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    orderNumber varchar (255) NOT NULL,
    order_status_id INT NOT NULL,
    orderDate DATETIME NOT NULL,
    CONSTRAINT  fk_order_status
        FOREIGN KEY (order_status_id) REFERENCES order_status (id)
);

CREATE TABLE order_log
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    orderNumber varchar (255) NOT NULL,
    creationDate DATETIME NOT NULL,
    order_status_id INT NOT NULL,
    order_id INT NOT NULL,
    CONSTRAINT  order_status_fk
        FOREIGN KEY (order_status_id) REFERENCES order_status (id),
    CONSTRAINT  order_id_fk
        FOREIGN KEY (order_id) REFERENCES `order` (id) ON DELETE CASCADE
);

CREATE TABLE payment
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    payment_status_id INT NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    CONSTRAINT  fk_payment_status
        FOREIGN KEY (payment_status_id) REFERENCES payment_status (id)
);

CREATE TABLE electronic_bank_transfer
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    bankName varchar (255) NOT NULL,
    routingNumber varchar (255) NOT NULL,
    accountNumber varchar (255) NOT NULL,
    account_id INT NOT NULL,
    CONSTRAINT  account_id_fk
        FOREIGN KEY (account_id) REFERENCES account (id)
            ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE credit_card
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    nameOnCard varchar(255) NOT NULL,
    cardNumber varchar(255) NOT NULL,
    code       INT          NOT NULL,
    address_id INT,
    account_id INT          NOT NULL,
    CONSTRAINT fk_account_id_cc
        FOREIGN KEY (account_id) REFERENCES account (id)
            ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE billing_address
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    streetAddress  varchar(255) NOT NULL,
    city           varchar(255) NOT NULL,
    state          varchar(255) NOT NULL,
    zipcode        varchar(255) NOT NULL,
    country        varchar(255) NOT NULL,
    credit_card_id INT,
    CONSTRAINT fk_credit_card_id
        FOREIGN KEY (credit_card_id) REFERENCES credit_card (id)
            ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE electronic_bank_transaction
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    payment_id INT NOT NULL,
    CONSTRAINT  fk_payment_id
        FOREIGN KEY (payment_id) REFERENCES payment (id)
);

CREATE TABLE credit_card_transaction
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    payment_id INT NOT NULL,
    CONSTRAINT  payment_id_fk
        FOREIGN KEY (payment_id) REFERENCES payment (id)
);

