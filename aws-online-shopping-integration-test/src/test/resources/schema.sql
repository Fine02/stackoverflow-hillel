DROP TABLE IF EXISTS payment_status;
DROP TABLE IF EXISTS shipment_status;
DROP TABLE IF EXISTS account_status;
DROP TABLE IF EXISTS order_status;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS sms_notification;
DROP TABLE IF EXISTS email_notification;
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS shipment;
DROP TABLE IF EXISTS shipment_log;
DROP TABLE IF EXISTS "order";
DROP TABLE IF EXISTS order_log;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS electronic_bank_transfer;
DROP TABLE IF EXISTS credit_card;

CREATE SEQUENCE payment_status_seq;

CREATE TABLE payment_status
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('payment_status_seq'),
    status varchar (255) NOT NULL
);

CREATE SEQUENCE shipment_status_seq;

CREATE TABLE shipment_status
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('shipment_status_seq'),
    status varchar (255) NOT NULL
);

CREATE SEQUENCE account_status_seq;

CREATE TABLE account_status
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('account_status_seq'),
    status varchar (255) NOT NULL
);

CREATE SEQUENCE order_status_seq;

CREATE TABLE order_status
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('order_status_seq'),
    status varchar (255) NOT NULL
);

CREATE SEQUENCE address_seq;

CREATE TABLE address
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('address_seq'),
    streetAddress varchar (255) NOT NULL,
    city varchar (255) NOT NULL,
    state varchar (255) NOT NULL,
    zipcode varchar (255) NOT NULL,
    country varchar (255) NOT NULL
);

CREATE SEQUENCE notification_seq;

CREATE TABLE notification
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('notification_seq'),
    createdOn TIMESTAMP(0) NOT NULL ,
    content varchar (255) NOT NULL
);

CREATE SEQUENCE sms_notification_seq;

CREATE TABLE sms_notification
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('sms_notification_seq'),
    phone varchar (255) NOT NULL,
    notification_id INT NOT NULL,
    CONSTRAINT  fk_sms_notification_id
    FOREIGN KEY (notification_id) REFERENCES notification (id) ON DELETE CASCADE
);

CREATE SEQUENCE email_notification_seq;

CREATE TABLE email_notification
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('email_notification_seq'),
    email varchar (255) NOT NULL,
    notification_id INT NOT NULL,
    CONSTRAINT  fk_email_notification_id
    FOREIGN KEY (notification_id) REFERENCES notification (id) ON DELETE CASCADE
);

CREATE SEQUENCE shipment_seq;

CREATE TABLE shipment
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('shipment_seq'),
    shipmentNumber varchar (255) NOT NULL,
    shipmentDate TIMESTAMP(0) NOT NULL,
    estimatedArrival TIMESTAMP(0) NOT NULL,
    shipmentMethod varchar (255) NOT NULL
);

CREATE SEQUENCE shipment_log_seq;

CREATE TABLE shipment_log
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('shipment_log_seq'),
    shipmentNumber varchar (255) NOT NULL,
    shipment_status_id INT NOT NULL,
    creationDate TIMESTAMP(0) NOT NULL,
    shipment_id INT NOT NULL,
    CONSTRAINT  fk_shipment_status
        FOREIGN KEY (shipment_status_id) REFERENCES shipment_status (id),
    CONSTRAINT  fk_shipment_id
        FOREIGN KEY (shipment_id) REFERENCES Shipment (id) ON DELETE CASCADE
);

CREATE SEQUENCE order_seq;

CREATE TABLE "order"
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('order_seq'),
    orderNumber varchar (255) NOT NULL,
    order_status_id INT NOT NULL,
    orderDate TIMESTAMP(0) NOT NULL,
    CONSTRAINT  fk_order_status
        FOREIGN KEY (order_status_id) REFERENCES order_status (id)
);

CREATE SEQUENCE order_log_seq;

CREATE TABLE order_log
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('order_log_seq'),
    orderNumber varchar (255) NOT NULL,
    creationDate TIMESTAMP(0) NOT NULL,
    order_status_id INT NOT NULL,
    order_id INT NOT NULL,
    CONSTRAINT  order_status_fk
        FOREIGN KEY (order_status_id) REFERENCES order_status (id),
    CONSTRAINT  order_id_fk
        FOREIGN KEY (order_id) REFERENCES "order" (id) ON DELETE CASCADE
);


CREATE SEQUENCE payment_seq;

CREATE TABLE payment
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('payment_seq'),
    payment_status_id INT NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    CONSTRAINT  fk_payment_status
        FOREIGN KEY (payment_status_id) REFERENCES payment_status (id)
);


CREATE SEQUENCE account_seq;

CREATE TABLE account
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('account_seq'),
    userName varchar (255) NOT NULL,
    password varchar (255) NOT NULL,
    account_status_id INT NOT NULL,
    name varchar (255) NOT NULL,
    address_id INT NOT NULL,
    email varchar (255) NOT NULL,
    phone varchar (255) NOT NULL,
    CONSTRAINT  fk_account_status_id
        FOREIGN KEY (account_status_id) REFERENCES account_status (id),
    CONSTRAINT  fk_address_id
        FOREIGN KEY (address_id) REFERENCES address (id)
);


CREATE SEQUENCE member_seq;

CREATE TABLE member
(
    account_id INT NOT NULL,
    id INT PRIMARY KEY DEFAULT NEXTVAL ('member_seq'),
    CONSTRAINT  fk_account_id
        FOREIGN KEY (account_id) REFERENCES account (id)
);


CREATE SEQUENCE electronic_bank_transfer_seq;

CREATE TABLE electronic_bank_transfer
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('electronic_bank_transfer_seq'),
    bankName varchar (255) NOT NULL,
    routingNumber varchar (255) NOT NULL,
    accountNumber varchar (255) NOT NULL,
    account_id INT NOT NULL,
    CONSTRAINT  account_id_fk
        FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE SEQUENCE credit_card_seq;

CREATE TABLE credit_card
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('credit_card_seq'),
    nameOnCard varchar (255) NOT NULL,
    cardNumber varchar (255) NOT NULL,
    code INT NOT NULL,
    address_id INT NOT NULL,
    account_id INT NOT NULL,
    CONSTRAINT  address_id_fk
        FOREIGN KEY (address_id) REFERENCES address (id),
    CONSTRAINT  fk_account_id_cc
        FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE SEQUENCE electronic_bank_transaction_seq;

CREATE TABLE electronic_bank_transaction
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('electronic_bank_transaction_seq'),
    payment_id INT NOT NULL,
    CONSTRAINT  fk_payment_id
        FOREIGN KEY (payment_id) REFERENCES payment (id)
);


CREATE SEQUENCE credit_card_transaction_seq;

CREATE TABLE credit_card_transaction
(
    id INT PRIMARY KEY DEFAULT NEXTVAL ('credit_card_transaction_seq'),
    payment_id INT NOT NULL,
    CONSTRAINT  payment_id_fk
        FOREIGN KEY (payment_id) REFERENCES payment (id)
);