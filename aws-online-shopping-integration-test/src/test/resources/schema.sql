DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS electronic_bank_transfer CASCADE;
DROP TABLE IF EXISTS credit_card CASCADE;

CREATE TABLE address
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    streetaddress varchar(255) NOT NULL,
    city          varchar(255) NOT NULL,
    state         varchar(255) NOT NULL,
    zipcode       varchar(255) NOT NULL,
    country       varchar(255) NOT NULL
);

CREATE TABLE account
(
    id             INT PRIMARY KEY AUTO_INCREMENT,
    userName       varchar(255) NOT NULL,
    password       varchar(255) NOT NULL,
    account_status varchar(255) NOT NULL,
    name           varchar(255) NOT NULL,
    address_id     INT          NOT NULL,
    email          varchar(255) NOT NULL,
    phone          varchar(255) NOT NULL,
    CONSTRAINT fk_address_id
        FOREIGN KEY (address_id) REFERENCES address (id)
            ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE electronic_bank_transfer
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    bankName      varchar(255) NOT NULL,
    routingNumber varchar(255) NOT NULL,
    accountNumber varchar(255) NOT NULL,
    account_id    INT          NOT NULL,
    CONSTRAINT account_id_fk
        FOREIGN KEY (account_id) REFERENCES account (id)
            ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE credit_card
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    nameOnCard varchar(255) NOT NULL,
    cardNumber varchar(255) NOT NULL,
    code       INT          NOT NULL,
    address_id INT          NOT NULL,
    account_id INT          NOT NULL,
    CONSTRAINT address_id_fk
        FOREIGN KEY (address_id) REFERENCES address (id)
            ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT fk_account_id_cc
        FOREIGN KEY (account_id) REFERENCES account (id)
            ON UPDATE NO ACTION ON DELETE CASCADE
);

