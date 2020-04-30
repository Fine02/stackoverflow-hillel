INSERT INTO address VALUES (nextval('address_seq'), 'Gonchara', 'Kyiv', 'Kyiv', '01134', 'Ukraine');
INSERT INTO address VALUES (nextval('address_seq'), 'Kirova', 'Dnirpo', 'Dnipro', '14569', 'Ukraine');
INSERT INTO address VALUES (nextval('address_seq'), 'Arnautska', 'Odesa', 'Odesa', '25147', 'Ukraine');

INSERT INTO account VALUES (nextval('account_seq'), 'david', '123456', 'ACTIVE', 'David Gilmour', 1, 'gilmour@gmail.com', '+380523698745');
INSERT INTO account VALUES (nextval('account_seq'), 'roger', '123123', 'COMPROMISED', 'Roger Waters', 2, 'waters@gmail.com', '+145878965644');
INSERT INTO account VALUES (nextval('account_seq'), 'syd', '321321', 'ARCHIVED', 'Syd Barret', 3, 'barret@gmail.com', '+7858789656458');

INSERT INTO electronic_bank_transfer VALUES (nextval('electronic_bank_transfer_seq'), 'Privat', '111111', '111111', 1);
INSERT INTO electronic_bank_transfer VALUES (nextval('electronic_bank_transfer_seq'), 'Monobank', '222222', '222222', 1);
INSERT INTO electronic_bank_transfer VALUES (nextval('electronic_bank_transfer_seq'), 'Alfa', '333333', '333333', 2);
INSERT INTO electronic_bank_transfer VALUES (nextval('electronic_bank_transfer_seq'), 'PUMB', '444444', '444444', 3);

INSERT INTO credit_card VALUES (nextval('credit_card_seq'), 'David Gilmour', '555555', 555, 1, 1);
INSERT INTO credit_card VALUES (nextval('credit_card_seq'), 'Gilmour David', '666666', 666, 1, 1);
INSERT INTO credit_card VALUES (nextval('credit_card_seq'), 'Roger Waters', '777777', 777, 2, 2);
INSERT INTO credit_card VALUES (nextval('credit_card_seq'), 'Waters Roger', '888888', 888, 2, 2);
INSERT INTO credit_card VALUES (nextval('credit_card_seq'), 'Syd Barret', '999999', 999, 3, 3);
INSERT INTO credit_card VALUES (nextval('credit_card_seq'), 'Barret Syd', '000000', 000, 3, 3);


