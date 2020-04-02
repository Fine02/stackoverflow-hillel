BEGIN;

DROP TABLE IF EXISTS notification, tag, account, member, bounty, question, answer, comment, photo, notification,
    tag_question, member_question, member_answer, member_comment, member_notification,
    member_badge_question, member_voted_question, member_voted_answer, member_voted_comment;

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

CREATE TABLE notification (
                              id BIGSERIAL PRIMARY KEY,
                              created_on TIMESTAMP NOT NULL,
                              content TEXT NOT NULL
);

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
                           account_status  account_status_type,
                           CONSTRAINT ak_email
                               UNIQUE (email));

CREATE TABLE  member  (
                          id  BIGSERIAL PRIMARY KEY,
                          account_id  BIGINT NOT NULL,
                          CONSTRAINT  fk_account_id
                              FOREIGN KEY ( id )
                                  REFERENCES  account  ( id ));

CREATE TABLE  bounty  (
                          id  BIGSERIAL PRIMARY KEY,
                          reputation  INT NULL,
                          expiry  TIMESTAMP NOT NULL,
                          creator_id  BIGINT NOT NULL,
                          CONSTRAINT  fk_creator_id
                              FOREIGN KEY ( creator_id )
                                  REFERENCES  member  ( id ));

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
                                    REFERENCES  member  ( id ),
                            CONSTRAINT  fk_bounty_id
                                FOREIGN KEY ( bounty_id )
                                    REFERENCES  bounty  (id));

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
                                  REFERENCES  member  ( id ),
                          CONSTRAINT  fk_answer_question_id
                              FOREIGN KEY ( question_id )
                                  REFERENCES  question  ( id ));

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
                                   REFERENCES  member  ( id ),
                           CONSTRAINT  fk_comment_answer_id
                               FOREIGN KEY ( answer_id )
                                   REFERENCES  answer  ( id )
                                   ON DELETE CASCADE
                                   ON UPDATE CASCADE,
                           CONSTRAINT  fk_comment_question_id
                               FOREIGN KEY ( question_id )
                                   REFERENCES  question  ( id ));

CREATE TABLE  photo  (
                         id  BIGSERIAL PRIMARY KEY,
                         photo_path  VARCHAR(1024) NOT NULL,
                         creation_date  TIMESTAMP NOT NULL,
                         question_id  BIGINT NULL,
                         answer_id  BIGINT NULL,
                         comment_id  BIGINT NULL,
                         CONSTRAINT  fk_photo_question_id
                             FOREIGN KEY ( question_id )
                                 REFERENCES  question  ( id ),
                         CONSTRAINT  fk_photo_answer_id
                             FOREIGN KEY ( answer_id )
                                 REFERENCES  answer  ( id )
                                 ON DELETE CASCADE
                                 ON UPDATE CASCADE,
                         CONSTRAINT  fk_photo_comment_id
                             FOREIGN KEY ( comment_id )
                                 REFERENCES  comment  ( id )
                                 ON DELETE CASCADE
                                 ON UPDATE CASCADE);

CREATE TABLE  tag_question  (
                                tag_id  BIGINT NOT NULL,
                                question_id  BIGINT NOT NULL,
                                PRIMARY KEY (tag_id, question_id),
                                CONSTRAINT  fk_tag_question_tag_id
                                    FOREIGN KEY ( tag_id )
                                        REFERENCES  tag  ( id )
                                        ON DELETE CASCADE
                                        ON UPDATE CASCADE,
                                CONSTRAINT  fk_tag_question_question_id
                                    FOREIGN KEY ( question_id )
                                        REFERENCES  question  ( id )
                                        ON DELETE CASCADE
                                        ON UPDATE CASCADE);

CREATE TABLE member_question (
                                 member_id BIGINT NOT NULL,
                                 question_id BIGINT NOT NULL,
                                 PRIMARY KEY (member_id, question_id),
                                 CONSTRAINT fk_member_id
                                     FOREIGN KEY (member_id)
                                         REFERENCES member (id)
                                         ON DELETE CASCADE
                                         ON UPDATE CASCADE,
                                 CONSTRAINT fk_question_id
                                     FOREIGN KEY (question_id)
                                         REFERENCES question (id)
                                         ON DELETE CASCADE
                                         ON UPDATE CASCADE);

CREATE TABLE member_answer (
                               member_id BIGINT NOT NULL,
                               answer_id BIGINT NOT NULL,
                               PRIMARY KEY (member_id, answer_id),
                               CONSTRAINT fk_member_id
                                   FOREIGN KEY (member_id)
                                       REFERENCES member (id)
                                       ON DELETE CASCADE
                                       ON UPDATE CASCADE,
                               CONSTRAINT fk_answer_id
                                   FOREIGN KEY (answer_id)
                                       REFERENCES answer (id)
                                       ON DELETE CASCADE
                                       ON UPDATE CASCADE);

CREATE TABLE member_comment (
                                member_id BIGINT NOT NULL,
                                comment_id BIGINT NOT NULL,
                                PRIMARY KEY (member_id, comment_id),
                                CONSTRAINT fk_member_id
                                    FOREIGN KEY (member_id)
                                        REFERENCES member (id)
                                        ON DELETE CASCADE
                                        ON UPDATE CASCADE,
                                CONSTRAINT fk_comment_id
                                    FOREIGN KEY (comment_id)
                                        REFERENCES comment (id)
                                        ON DELETE CASCADE
                                        ON UPDATE CASCADE);

CREATE TABLE member_notification (
                                     member_id BIGINT NOT NULL,
                                     notification_id BIGINT NOT NULL,
                                     PRIMARY KEY (member_id, notification_id),
                                     CONSTRAINT fk_member_id
                                         FOREIGN KEY (member_id)
                                             REFERENCES member (id)
                                             ON DELETE CASCADE
                                             ON UPDATE CASCADE,
                                     CONSTRAINT fk_notification_id
                                         FOREIGN KEY (notification_id)
                                             REFERENCES notification (id)
                                             ON DELETE CASCADE
                                             ON UPDATE CASCADE);

CREATE TABLE member_badge_question (
                                       member_id BIGINT NOT NULL,
                                       badge badge_type,
                                       question_id BIGINT NOT NULL,
                                       PRIMARY KEY (member_id, badge, question_id),
                                       CONSTRAINT fk_member_id
                                           FOREIGN KEY (member_id)
                                               REFERENCES member (id)
                                               ON DELETE CASCADE
                                               ON UPDATE CASCADE,
                                       CONSTRAINT fk_question_id
                                           FOREIGN KEY (question_id)
                                               REFERENCES question (id)
                                               ON DELETE CASCADE
                                               ON UPDATE CASCADE);

CREATE TABLE member_voted_question (
                                       member_id BIGINT NOT NULL,
                                       question_id BIGINT NOT NULL,
                                       upvoted BOOLEAN NULL,
                                       PRIMARY KEY (member_id, question_id),
                                       CONSTRAINT fk_member_id
                                           FOREIGN KEY (member_id)
                                               REFERENCES member (id)
                                               ON DELETE CASCADE
                                               ON UPDATE CASCADE,
                                       CONSTRAINT fk_question_id
                                           FOREIGN KEY (question_id)
                                               REFERENCES question (id)
                                               ON DELETE CASCADE
                                               ON UPDATE CASCADE);

CREATE TABLE member_voted_answer (
                                     member_id BIGINT NOT NULL,
                                     answer_id BIGINT NOT NULL,
                                     upvoted BOOLEAN NULL,
                                     PRIMARY KEY (member_id, answer_id),
                                     CONSTRAINT fk_member_id
                                         FOREIGN KEY (member_id)
                                             REFERENCES member (id)
                                             ON DELETE CASCADE
                                             ON UPDATE CASCADE,
                                     CONSTRAINT fk_answer_id
                                         FOREIGN KEY (answer_id)
                                             REFERENCES answer (id)
                                             ON DELETE CASCADE
                                             ON UPDATE CASCADE);

CREATE TABLE member_voted_comment (
                                      member_id BIGINT NOT NULL,
                                      comment_id BIGINT NOT NULL,
                                      upvoted BOOLEAN NULL,
                                      PRIMARY KEY (member_id, comment_id),
                                      CONSTRAINT fk_member_id
                                          FOREIGN KEY (member_id)
                                              REFERENCES member (id)
                                              ON DELETE CASCADE
                                              ON UPDATE CASCADE,
                                      CONSTRAINT fk_comment_id
                                          FOREIGN KEY (comment_id)
                                              REFERENCES comment (id)
                                              ON DELETE CASCADE
                                              ON UPDATE CASCADE);

COMMIT;