INSERT INTO account (userName, password, account_status, name, email, phone)
VALUES ('david', '123456', 'ACTIVE', 'David Gilmour', 'gilmour@gmail.com', '+380523698745');
INSERT INTO account (userName, password, account_status, name, email, phone)
VALUES ('roger', '123123', 'COMPROMISED', 'Roger Waters', 'waters@gmail.com', '+145878965644');
INSERT INTO account (userName, password, account_status, name, email, phone)
VALUES ('syd', '321321', 'ARCHIVED', 'Syd Barret', 'barret@gmail.com', '+7858789656458');

INSERT INTO address (streetAddress, city, state, zipcode, country, account_id)
VALUES ('Gonchara', 'Kyiv', 'Kyiv', '01134', 'Ukraine', 1);
INSERT INTO address (streetAddress, city, state, zipcode, country, account_id)
VALUES ('Zlayoustivska', 'Kyiv', 'Kyiv', '01135', 'Ukraine', 2);
INSERT INTO address (streetAddress, city, state, zipcode, country, account_id)
VALUES ('Kirova', 'Dnirpo', 'Dnipro', '14569', 'Ukraine', 3);

INSERT INTO credit_card (nameOnCard, cardNumber, code, account_id)
VALUES ('David Gilmour', '555555', 555, 1);
INSERT INTO credit_card (nameOnCard, cardNumber, code, account_id)
VALUES ('Gilmour David', '666666', 666, 1);
INSERT INTO credit_card (nameOnCard, cardNumber, code, account_id)
VALUES ('Roger Waters', '777777', 777, 2);
INSERT INTO credit_card (nameOnCard, cardNumber, code, account_id)
VALUES ('Waters Roger', '888888', 888, 2);
INSERT INTO credit_card (nameOnCard, cardNumber, code, account_id)
VALUES ('Syd Barret', '999999', 999, 3);
INSERT INTO credit_card (nameOnCard, cardNumber, code, account_id)
VALUES ('Barret Syd', '000000', 000, 3);

INSERT INTO billing_address (streetAddress, city, state, zipcode, country, credit_card_id)
VALUES ('GoncharaB', 'Kyiv', 'Kyiv', '01134', 'Ukraine', 1);
INSERT INTO billing_address (streetAddress, city, state, zipcode, country, credit_card_id)
VALUES ('ZlayoustivskaB', 'Kyiv', 'Kyiv', '01135', 'Ukraine', 2);
INSERT INTO billing_address (streetAddress, city, state, zipcode, country, credit_card_id)
VALUES ('KirovaB', 'Dnirpo', 'Dnipro', '14569', 'Ukraine', 3);
INSERT INTO billing_address (streetAddress, city, state, zipcode, country, credit_card_id)
VALUES ('SadovaB', 'Dnirpo', 'Dnipro', '14568', 'Ukraine', 4);
INSERT INTO billing_address (streetAddress, city, state, zipcode, country, credit_card_id)
VALUES ('ArnautskaB', 'Odesa', 'Odesa', '25147', 'Ukraine', 5);
INSERT INTO billing_address (streetAddress, city, state, zipcode, country, credit_card_id)
VALUES ('DerybasivskaB', 'Odesa', 'Odesa', '25144', 'Ukraine', 6);

INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id)
VALUES ('Privat', '111111', '111111', 1);
INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id)
VALUES ('Monobank', '222222', '222222', 1);
INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id)
VALUES ('Alfa', '333333', '333333', 2);
INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id)
VALUES ('PUMB', '444444', '444444', 3);



