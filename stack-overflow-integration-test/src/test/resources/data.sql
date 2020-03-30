
INSERT INTO  account_status (status) VALUES ('ACTIVE');
INSERT INTO  account_status (status) VALUES ('BLOCKED');
INSERT INTO  account_status (status) VALUES ('BANNED');
INSERT INTO  account_status (status) VALUES ('COMPROMISED');
INSERT INTO  account_status (status) VALUES ('ARCHIVED');
INSERT INTO  account_status (status) VALUES ('UNKNOWN');

INSERT INTO  question_closing_remark (remark) VALUES ('DUPLICATE');
INSERT INTO  question_closing_remark (remark) VALUES ('OFFTOPIC');
INSERT INTO  question_closing_remark (remark) VALUES ('TOO_BROAD');
INSERT INTO  question_closing_remark (remark) VALUES ('NOT_CONSTRUCTIVE');
INSERT INTO  question_closing_remark (remark) VALUES ('NOT_REAL_QUESTION');
INSERT INTO  question_closing_remark (remark) VALUES ('PRIMARLY_OPINION_BASED');
INSERT INTO  question_closing_remark (remark) VALUES ('ADVERTASING');
INSERT INTO  question_closing_remark (remark) VALUES ('ABUSE');
INSERT INTO  question_closing_remark (remark) VALUES ('SPAM');
INSERT INTO  question_closing_remark (remark) VALUES ('NOT_MARKED_FOR_CLOSING');

INSERT INTO  question_status (status) VALUES ('OPEN');
INSERT INTO  question_status (status) VALUES ('CLOSE');
INSERT INTO  question_status (status) VALUES ('ON_HOLD');
INSERT INTO  question_status (status) VALUES ('DELETED');

INSERT INTO  tag (name, description) VALUES ('JAVA', 'Some tag description');
INSERT INTO  tag (name, description) VALUES ('C#', 'Some tag description');
INSERT INTO  tag (name, description) VALUES ('SQL', 'Some tag description');

INSERT INTO  account (password, name, email, reputation, account_status_id) VALUES('password1', 'name1', 'email1@gmail.com', 2, 1);
INSERT INTO  account (password, name, email, reputation, account_status_id) VALUES('password2', 'name2', 'email2@gmail.com', 0, 2);
INSERT INTO  account (password, name, email, reputation, account_status_id) VALUES('password3', 'name3', 'email3@gmail.com', 5, 3);

INSERT INTO  member (account_id) VALUES(1);
INSERT INTO  member (account_id) VALUES(2);
INSERT INTO  member (account_id) VALUES(3);

INSERT INTO  bounty (reputation, expiry, creator_id) VALUES(4, '2020-02-05 10:22:51', 1);
INSERT INTO  bounty (reputation, expiry, creator_id) VALUES(33, '2020-03-19 22:22:11', 2);
INSERT INTO  bounty (reputation, expiry, creator_id) VALUES(1, '2020-01-24 15:02:14', 3);

INSERT INTO  question (title, description, view_count, vote_count, creation_time, update_time, status_id, closing_remark_id, author_id, bounty_id)
VALUES('Firs question title', 'Firs question description', 100, 15, '2020-03-19 13:32:37', '2020-05-15 12:35:51', 1, 1, 1, 2);
INSERT INTO  question (title, description, view_count, vote_count, creation_time, update_time, status_id, closing_remark_id, author_id)
VALUES('Another question title', 'Another question description', 2, 0, '2020-03-19 14:55:14', '2020-03-18 10:35:22', 2, 2, 2);
INSERT INTO  question (title, description, view_count, vote_count, creation_time, update_time, status_id, closing_remark_id, author_id, bounty_id)
VALUES('Just question title', 'Just question description', 11, 1, '2020-03-15 10:30:22', '2020-03-13 11:30:22', 3, 3, 3, 1);

INSERT INTO  answer (answer_text, accepted, vote_count, flag_count, creation_date, author_id, question_id)
VALUES('Some answer text', true, 3, 5, '2020-03-19 13:32:37', 1, 1);
INSERT INTO  answer (answer_text, accepted, vote_count, flag_count, creation_date, author_id, question_id)
VALUES('Another answer text', false, 2, 4, '2020-03-19 14:55:14', 2, 2);
INSERT INTO  answer (answer_text, accepted, vote_count, flag_count, creation_date, author_id, question_id)
VALUES('Just answer text', false, 0, 0, '2020-03-15 10:30:22', 3, 3);

INSERT INTO  comment (comment_text, creation_date, vote_count, author_id, question_id)
VALUES('Some comment text', '2020-03-19 13:32:37', 3, 3, 1);
INSERT INTO  comment (comment_text, creation_date, vote_count, author_id, answer_id)
VALUES('Another comment text', '2020-03-19 14:55:14', 2,1, 2);
INSERT INTO  comment (comment_text, creation_date, vote_count, author_id, answer_id)
VALUES('Just comment text', '2020-03-15 10:30:22', 5,1, 3);

INSERT INTO  photo (photo_path, creation_date, comment_id)
VALUES('/image/1/photo123.jpg', '2019-12-01 00:21:37', 1);
INSERT INTO  photo (photo_path, creation_date, answer_id)
VALUES('/image/2/photo456.jpg', '2020-01-19 22:32:01', 2);
INSERT INTO  photo (photo_path, creation_date, question_id)
VALUES('/image/3/photo789.jpg', '2016-04-28 13:00:00', 3);

INSERT INTO  tag_question (tag_id, question_id) VALUES(1, 2);
INSERT INTO  tag_question (tag_id, question_id) VALUES(2, 1);
INSERT INTO  tag_question (tag_id, question_id) VALUES(3, 3);