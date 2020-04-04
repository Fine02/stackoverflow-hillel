CREATE SCHEMA IF NOT EXISTS public;
USE public;

DROP TABLE IF EXISTS notification, tag, account, member, bounty, question, answer, comment, photo, tag_question;
DROP DOMAIN IF EXISTS badge_type;
DROP SEQUENCE IF EXISTS comment_id_seq;

CREATE TYPE badge_type AS ENUM ('student',
                                'teacher',
                                'commentator',
                                'critic',
                                'supporter',
                                'benefactor',
                                'nice_question',
                                'good_question',
                                'great_question');


CREATE TABLE notification (
                              id IDENTITY PRIMARY KEY,
                              created_on TIMESTAMP NOT NULL,
                              content TEXT NOT NULL
);

CREATE TABLE  tag  (
                       id  IDENTITY PRIMARY KEY,
                       name  VARCHAR(128) NOT NULL,
                       description  VARCHAR(255) NULL
);

CREATE TABLE  account  (
                           id  IDENTITY PRIMARY KEY,
                           password  VARCHAR(45) NOT NULL,
                           name  VARCHAR(45) NOT NULL,
                           email  VARCHAR(45) NOT NULL,
                           reputation  INT NULL,
                           account_status  ENUM ('active',
                               'blocked',
                               'banned',
                               'compromised',
                               'archived',
                               'unknown'));

CREATE TABLE  member  (
                          id  IDENTITY PRIMARY KEY,
                          account_id  INT NOT NULL,
                          CONSTRAINT  fk_account_id
                              FOREIGN KEY ( id )
                                  REFERENCES  account  ( id )
                                  ON DELETE CASCADE
                                  ON UPDATE CASCADE);

CREATE TABLE  bounty  (
                          id  IDENTITY PRIMARY KEY,
                          reputation  INT NULL,
                          expiry  TIMESTAMP NOT NULL,
                          creator_id  INT NOT NULL,
                          CONSTRAINT  fk_creator_id
                              FOREIGN KEY ( id )
                                  REFERENCES  member  ( id )
                                  ON DELETE NO ACTION
                                  ON UPDATE NO ACTION);

CREATE TABLE  question  (
                            id  IDENTITY PRIMARY KEY,
                            title  VARCHAR(255) NOT NULL,
                            description  TEXT NOT NULL,
                            view_count  INT NOT NULL,
                            vote_count  INT NOT NULL,
                            creation_time  TIMESTAMP NOT NULL,
                            update_time  TIMESTAMP NOT NULL,
                            status  ENUM ('open',
                                'close',
                                'on_hold',
                                'deleted'),
                            closing_remark ENUM ('duplicate',
                                'offtopic',
                                'too_broad',
                                'not_constructive',
                                'not_real_question',
                                'primarly_opinion_based',
                                'advertasing',
                                'abuse',
                                'spam',
                                'not_marked_for_closing'),
                            author_id  INT NOT NULL,
                            bounty_id  INT NULL,
                            CONSTRAINT  fk_author_id
                                FOREIGN KEY ( author_id )
                                    REFERENCES  member  ( id )
                                    ON DELETE NO ACTION
                                    ON UPDATE NO ACTION,
                            CONSTRAINT  fk_bounty_id
                                FOREIGN KEY ( bounty_id )
                                    REFERENCES  bounty  ( id )
                                    ON DELETE NO ACTION
                                    ON UPDATE NO ACTION);

CREATE TABLE  answer  (
                          id  IDENTITY PRIMARY KEY,
                          answer_text  TEXT NOT NULL,
                          accepted  BOOLEAN NOT NULL DEFAULT false,
                          vote_count  INT NOT NULL,
                          flag_count  INT NOT NULL,
                          creation_date  TIMESTAMP NOT NULL,
                          author_id  INT NOT NULL,
                          question_id  INT NOT NULL,
                          CONSTRAINT  fk_answer_author_id
                              FOREIGN KEY ( author_id )
                                  REFERENCES  member  ( id )
                                  ON DELETE NO ACTION
                                  ON UPDATE NO ACTION,
                          CONSTRAINT  fk_answer_question_id
                              FOREIGN KEY ( question_id )
                                  REFERENCES  question  ( id )
                                  ON DELETE NO ACTION
                                  ON UPDATE NO ACTION);

// please don't change the part with creating comment_table
CREATE SEQUENCE comment_id_seq START WITH 1;

CREATE TABLE comment
(
    id            BIGINT NOT NULL DEFAULT nextval('comment_id_seq')PRIMARY KEY,
    comment_text  VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP    NOT NULL,
    vote_count    INT,
    author_id     BIGINT       NOT NULL,
    answer_id     BIGINT,
    question_id   BIGINT,
    CONSTRAINT fk_comment_author_id FOREIGN KEY (author_id) REFERENCES account (id),
    CONSTRAINT fk_comment_answer_id FOREIGN KEY (answer_id) REFERENCES answer (id),
    CONSTRAINT fk_comment_question_id FOREIGN KEY (question_id) REFERENCES question (id)
);


CREATE TABLE  photo  (
                         id  IDENTITY PRIMARY KEY,
                         photo_path  VARCHAR(1024) NOT NULL,
                         creation_date  TIMESTAMP NOT NULL,
                         question_id  INT NULL,
                         answer_id  INT NULL,
                         comment_id  INT NULL,
                         CONSTRAINT  fk_photo_question_id
                             FOREIGN KEY ( question_id )
                                 REFERENCES  question  ( id )
                                 ON DELETE NO ACTION
                                 ON UPDATE NO ACTION,
                         CONSTRAINT  fk_photo_answer_id
                             FOREIGN KEY ( answer_id )
                                 REFERENCES  answer  ( id )
                                 ON DELETE NO ACTION
                                 ON UPDATE NO ACTION,
                         CONSTRAINT  fk_photo_comment_id
                             FOREIGN KEY ( comment_id )
                                 REFERENCES  comment  ( id )
                                 ON DELETE NO ACTION
                                 ON UPDATE NO ACTION);

CREATE TABLE  tag_question  (
                                tag_id  INT NOT NULL,
                                question_id  INT NOT NULL,
                                CONSTRAINT  fk_tag_question_tag_id
                                    FOREIGN KEY ( tag_id )
                                        REFERENCES  tag  ( id )
                                        ON DELETE NO ACTION
                                        ON UPDATE NO ACTION,
                                CONSTRAINT  fk_tag_question_question_id
                                    FOREIGN KEY ( question_id )
                                        REFERENCES  question  ( id )
                                        ON DELETE NO ACTION
                                        ON UPDATE NO ACTION);