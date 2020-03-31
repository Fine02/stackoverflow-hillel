BEGIN TRANSACTION;
DROP TABLE IF EXISTS tag, account, member, bounty, question, answer, comment, photo, tag_question;

DROP TYPE IF EXISTS account_status_type, question_closing_remark_type, question_status_type, badge_type;

CREATE TYPE account_status_type AS ENUM ('active',
                                        'blocked',
                                        'banned',
                                        'compromised',
                                        'archived',
                                        'unknown');

CREATE TYPE  question_closing_remark_type AS ENUM ('duplicate',
                                                    'offtopic',
                                                    'too_broad',
                                                    'not_constructive',
                                                    'not_real_question',
                                                    'primarly_opinion_based',
                                                    'advertasing',
                                                    'abuse',
                                                    'spam',
                                                    'not_marked_for_closing');

CREATE TYPE  question_status_type AS ENUM ('open',
                                            'close',
                                            'on_hold',
                                            'deleted');

CREATE TYPE badge_type AS ENUM ('student',
                                'teacher',
                                'commentator',
                                'critic',
                                'supporter',
                                'benefactor',
                                'nice_question',
                                'good_question',
                                'great_question');

CREATE TABLE  tag  (
                       id  BIGSERIAL PRIMARY KEY,
                       name  VARCHAR(128) NOT NULL,
                       description  VARCHAR(255) NULL
);

CREATE TABLE  account  (
                           id  BIGSERIAL PRIMARY KEY,
                           password  VARCHAR(45) NOT NULL,
                           name  VARCHAR(45) NOT NULL,
                           email  VARCHAR(45) NOT NULL,
                           reputation  INT NULL,
                           account_status  account_status_type);

CREATE TABLE  member  (
                          id  BIGSERIAL PRIMARY KEY,
                          account_id  BIGINT NOT NULL,
                          CONSTRAINT  fk_account_id
                              FOREIGN KEY ( id )
                                  REFERENCES  account  ( id )
                                  ON DELETE CASCADE
                                  ON UPDATE CASCADE);

CREATE TABLE  bounty  (
                          id  BIGSERIAL PRIMARY KEY,
                          reputation  INT NULL,
                          expiry  TIMESTAMP NOT NULL,
                          creator_id  BIGINT NOT NULL,
                          CONSTRAINT  fk_creator_id
                              FOREIGN KEY ( id )
                                  REFERENCES  member  ( id )
                                  ON DELETE NO ACTION
                                  ON UPDATE NO ACTION);

CREATE TABLE  question  (
                            id  BIGSERIAL PRIMARY KEY,
                            title  VARCHAR(255) NOT NULL,
                            description  TEXT NOT NULL,
                            view_count  INT NOT NULL,
                            vote_count  INT NOT NULL,
                            creation_time  TIMESTAMP NOT NULL,
                            update_time  TIMESTAMP NOT NULL,
                            status  question_status_type,
                            closing_remark  question_closing_remark_type,
                            author_id  BIGINT NOT NULL,
                            bounty_id  BIGINT NULL,
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
                          id  BIGSERIAL PRIMARY KEY,
                          answer_text  TEXT NOT NULL,
                          accepted  BOOLEAN NOT NULL DEFAULT false,
                          vote_count  INT NOT NULL,
                          flag_count  INT NOT NULL,
                          creation_date  TIMESTAMP NOT NULL,
                          author_id  BIGINT NOT NULL,
                          question_id  BIGINT NOT NULL,
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

CREATE TABLE  comment  (
                           id  BIGSERIAL PRIMARY KEY,
                           comment_text  VARCHAR(255) NOT NULL,
                           creation_date  TIMESTAMP NOT NULL,
                           vote_count  INT,
                           author_id  BIGINT NOT NULL,
                           answer_id  BIGINT NULL,
                           question_id  BIGINT NULL,
                           CONSTRAINT  fk_comment_author_id
                               FOREIGN KEY ( author_id )
                                   REFERENCES  member  ( id )
                                   ON DELETE NO ACTION
                                   ON UPDATE NO ACTION,
                           CONSTRAINT  fk_comment_answer_id
                               FOREIGN KEY ( answer_id )
                                   REFERENCES  answer  ( id )
                                   ON DELETE NO ACTION
                                   ON UPDATE NO ACTION,
                           CONSTRAINT  fk_comment_question_id
                               FOREIGN KEY ( question_id )
                                   REFERENCES  question  ( id )
                                   ON DELETE NO ACTION
                                   ON UPDATE NO ACTION);

CREATE TABLE  photo  (
                         id  BIGSERIAL PRIMARY KEY,
                         photo_path  VARCHAR(1024) NOT NULL,
                         creation_date  TIMESTAMP NOT NULL,
                         question_id  BIGINT NULL,
                         answer_id  BIGINT NULL,
                         comment_id  BIGINT NULL,
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
                                tag_id  BIGINT NOT NULL,
                                question_id  BIGINT NOT NULL,
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
COMMIT TRANSACTION;