DROP TABLE IF EXISTS address CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS electronic_bank_transfer CASCADE;
DROP TABLE IF EXISTS credit_card CASCADE;

DROP SEQUENCE IF EXISTS address_seq CASCADE;
DROP SEQUENCE IF EXISTS account_seq CASCADE;
DROP SEQUENCE IF EXISTS electronic_bank_transfer_seq CASCADE;
DROP SEQUENCE IF EXISTS credit_card_seq CASCADE;

CREATE SEQUENCE address_seq;

CREATE TABLE address
(
    id            INT PRIMARY KEY DEFAULT NEXTVAL('address_seq'),
    streetAddress varchar(255) NOT NULL,
    city          varchar(255) NOT NULL,
    state         varchar(255) NOT NULL,
    zipcode       varchar(255) NOT NULL,
    country       varchar(255) NOT NULL
);


CREATE SEQUENCE account_seq;
CREATE TABLE account
(
    id             INT PRIMARY KEY DEFAULT NEXTVAL('account_seq'),
    userName       varchar(255) NOT NULL,
    password       varchar(255) NOT NULL,
    account_status varchar(255) NOT NULL,
    name           varchar(255) NOT NULL,
    address_id     INT          NOT NULL,
    email          varchar(255) NOT NULL,
    phone          varchar(255) NOT NULL,
    CONSTRAINT fk_address_id
        FOREIGN KEY (address_id) REFERENCES address (id) MATCH SIMPLE
            ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE SEQUENCE electronic_bank_transfer_seq;
CREATE TABLE electronic_bank_transfer
(
    id            INT PRIMARY KEY DEFAULT NEXTVAL('electronic_bank_transfer_seq'),
    bankName      varchar(255) NOT NULL,
    routingNumber varchar(255) NOT NULL,
    accountNumber varchar(255) NOT NULL,
    account_id    INT          NOT NULL,
    CONSTRAINT account_id_fk
        FOREIGN KEY (account_id) REFERENCES account (id) MATCH SIMPLE
            ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE SEQUENCE credit_card_seq;
CREATE TABLE credit_card
(
    id         INT PRIMARY KEY DEFAULT NEXTVAL('credit_card_seq'),
    nameOnCard varchar(255) NOT NULL,
    cardNumber varchar(255) NOT NULL,
    code       INT          NOT NULL,
    address_id INT          NOT NULL,
    account_id INT          NOT NULL,
    CONSTRAINT address_id_fk
        FOREIGN KEY (address_id) REFERENCES address (id) MATCH SIMPLE
            ON UPDATE NO ACTION ON DELETE CASCADE,
    CONSTRAINT fk_account_id_cc
        FOREIGN KEY (account_id) REFERENCES account (id) MATCH SIMPLE
            ON UPDATE NO ACTION ON DELETE CASCADE
);

