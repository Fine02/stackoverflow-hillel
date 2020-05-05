INSERT INTO address (streetaddress, city, state, zipcode, country) VALUES  ('Gonchara', 'Kyiv', 'Kyiv', '01134', 'Ukraine');
INSERT INTO address (streetaddress, city, state, zipcode, country) VALUES  ('Zlayoustivska', 'Kyiv', 'Kyiv', '01135', 'Ukraine');
INSERT INTO address (streetaddress, city, state, zipcode, country) VALUES ('Kirova', 'Dnirpo', 'Dnipro', '14569', 'Ukraine');
INSERT INTO address (streetaddress, city, state, zipcode, country) VALUES ('Sadova', 'Dnirpo', 'Dnipro', '14568', 'Ukraine');
INSERT INTO address (streetaddress, city, state, zipcode, country) VALUES ('Arnautska', 'Odesa', 'Odesa', '25147', 'Ukraine');
INSERT INTO address (streetaddress, city, state, zipcode, country) VALUES ('Derybasivska', 'Odesa', 'Odesa', '25144', 'Ukraine');

INSERT INTO account (userName, password, account_status, name, address_id, email, phone) VALUES ('david', '123456', 'ACTIVE', 'David Gilmour', 1, 'gilmour@gmail.com', '+380523698745');
INSERT INTO account (userName, password, account_status, name, address_id, email, phone) VALUES ('roger', '123123', 'COMPROMISED', 'Roger Waters', 2, 'waters@gmail.com', '+145878965644');
INSERT INTO account (userName, password, account_status, name, address_id, email, phone) VALUES ('syd', '321321', 'ARCHIVED', 'Syd Barret', 3, 'barret@gmail.com', '+7858789656458');

INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id) VALUES ('Privat', '111111', '111111', 1);
INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id) VALUES ('Monobank', '222222', '222222', 1);
INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id) VALUES ('Alfa', '333333', '333333', 2);
INSERT INTO electronic_bank_transfer (bankName, routingNumber, accountNumber, account_id) VALUES ('PUMB', '444444', '444444', 3);

INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('David Gilmour', '555555', 555, 1, 1);
INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('Gilmour David', '666666', 666, 2, 1);
INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('Roger Waters', '777777', 777, 3, 2);
INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('Waters Roger', '888888', 888, 4, 2);
INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('Syd Barret', '999999', 999, 5, 3);
INSERT INTO credit_card (nameOnCard, cardNumber, code, address_id, account_id) VALUES ('Barret Syd', '000000', 000, 6, 3);


