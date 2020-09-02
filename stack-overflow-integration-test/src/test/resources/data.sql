INSERT INTO  tag (name, description) VALUES ('JAVA', 'Some tag description');
INSERT INTO  tag (name, description) VALUES ('C#', 'Some tag description');
INSERT INTO  tag (name, description) VALUES ('SQL', 'Some tag description');

INSERT INTO  account (password, name, email, reputation, account_status) VALUES('password1', 'name1', 'email1@gmail.com', 2, 'active');
INSERT INTO  account (password, name, email, reputation, account_status) VALUES('password2', 'name2', 'email2@gmail.com', 0, 'blocked');
INSERT INTO  account (password, name, email, reputation, account_status) VALUES('password3', 'name3', 'email3@gmail.com', 5, 'banned');

INSERT INTO  bounty (reputation, expiry, creator_id) VALUES(4, '2020-02-05 10:22:51', 1);
INSERT INTO  bounty (reputation, expiry, creator_id) VALUES(33, '2020-03-19 22:22:11', 2);
INSERT INTO  bounty (reputation, expiry, creator_id) VALUES(1, '2020-01-24 15:02:14', 3);

INSERT INTO  question (title, description, view_count, vote_count, creation_time, update_time, status, closing_remark, author_id, bounty_id)
VALUES('First question title', 'First question description', 100, 15, '2020-03-19 13:32:37', '2020-05-15 12:35:51', 'open', 'duplicate', 1, 2);
INSERT INTO  question (title, description, view_count, vote_count, creation_time, update_time, status, closing_remark, author_id)
VALUES('Another question title', 'Another question description', 2, 0, '2020-03-19 14:55:14', '2020-03-18 10:35:22', 'close', 'offtopic', 2);
INSERT INTO  question (title, description, view_count, vote_count, creation_time, update_time, status, closing_remark, author_id, bounty_id)
VALUES('Just question title', 'Just question description', 11, 1, '2020-03-15 10:30:22', '2020-03-13 11:30:22', 'on_hold', 'too_broad', 3, 1);

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

INSERT INTO  photo (photo_path, creation_date, answer_id)
VALUES('/image/1/photo123.jpg', '2019-12-01 00:21:37', 1);
INSERT INTO  photo (photo_path, creation_date, answer_id)
VALUES('/image/2/photo456.jpg', '2020-01-19 22:32:01', 2);
INSERT INTO  photo (photo_path, creation_date, question_id)
VALUES('/image/3/photo789.jpg', '2016-04-28 13:00:00', 3);

INSERT INTO  tag_question (tag_id, question_id) VALUES(1, 2);
INSERT INTO  tag_question (tag_id, question_id) VALUES(3, 3);
INSERT INTO  tag_question (tag_id, question_id) VALUES(2, 1);

INSERT INTO member_badge_question (account_id, badge, question_id) VALUES (1, 'student', 1);
INSERT INTO member_badge_question (account_id, badge, question_id) VALUES (1, 'student', 2);
INSERT INTO member_badge_question (account_id, badge, question_id) VALUES (1, 'critic', 2);
INSERT INTO member_badge_question (account_id, badge, question_id) VALUES (2, 'student', 2);
INSERT INTO member_badge_question (account_id, badge, question_id) VALUES (2, 'teacher', 3);
INSERT INTO member_badge_question (account_id, badge, question_id) VALUES (3, 'supporter', 1);

INSERT INTO member_voted_question (account_id, question_id, upvoted) VALUES (2, 1, true);
INSERT INTO member_voted_question (account_id, question_id, upvoted) VALUES (1, 2, false);
INSERT INTO member_voted_question (account_id, question_id, upvoted) VALUES (3, 3, true);

INSERT INTO member_voted_answer (account_id, answer_id, upvoted) VALUES (2, 1, true);
INSERT INTO member_voted_answer (account_id, answer_id, upvoted) VALUES (1, 2, false);
INSERT INTO member_voted_answer (account_id, answer_id, upvoted) VALUES (3, 3, true);

INSERT INTO member_voted_comment (account_id, comment_id, upvoted) VALUES (1, 1, true);
INSERT INTO member_voted_comment (account_id, comment_id, upvoted) VALUES (2, 2, false);
INSERT INTO member_voted_comment (account_id, comment_id, upvoted) VALUES (3, 3, true);

INSERT INTO question_member_question_closing_remark (question_id, account_id, closing_remark, marked_for_closing, marked_for_deleting)
VALUES (1, 1, 'offtopic', true, false);
INSERT INTO question_member_question_closing_remark (question_id, account_id, closing_remark, marked_for_closing, marked_for_deleting)
VALUES (1, 2, 'not_constructive', true, false);
INSERT INTO question_member_question_closing_remark (question_id, account_id, closing_remark, marked_for_closing, marked_for_deleting)
VALUES (1, 3, 'advertising', false, true);
INSERT INTO question_member_question_closing_remark (question_id, account_id, closing_remark, marked_for_closing, marked_for_deleting)
VALUES (2, 1, 'too_broad', true, true);
INSERT INTO question_member_question_closing_remark (question_id, account_id, closing_remark, marked_for_closing, marked_for_deleting)
VALUES (2, 2, 'offtopic', true, false);
INSERT INTO question_member_question_closing_remark (question_id, account_id, closing_remark, marked_for_closing, marked_for_deleting)
VALUES (3, 3, 'duplicate', true, true);

INSERT INTO notification (created_on, content)
VALUES ('2020-03-19 13:32:37', '1,1,0,0,Your question was created,true');
INSERT INTO notification (created_on, content)
VALUES ('2020-08-03 17:27:37', '1,1,0,0,Your question was voting up,false');
INSERT INTO notification (created_on, content)
VALUES ('2020-03-19 13:55:14', '2,0,2,0,Your answer was created,false');