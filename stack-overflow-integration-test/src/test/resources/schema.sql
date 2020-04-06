CREATE SCHEMA IF NOT EXISTS public;
USE public;

DROP TABLE IF EXISTS notification, tag, account, bounty, question, answer, comment, photo, notification,
    tag_question, member_notification, member_badge_question, member_voted_question, member_voted_answer,
    member_voted_comment, question_member_question_closing_remark;

CREATE TABLE notification
(
    id         IDENTITY PRIMARY KEY,
    created_on TIMESTAMP NOT NULL,
    content    TEXT      NOT NULL
);

CREATE TABLE tag
(
    id          IDENTITY PRIMARY KEY,
    name        VARCHAR(128) NOT NULL,
    description VARCHAR(255) NULL,
    CONSTRAINT ak_name_description
        UNIQUE (name)
);

CREATE TABLE account
(
    id             IDENTITY PRIMARY KEY,
    password       VARCHAR(45) NOT NULL,
    name           VARCHAR(45) NOT NULL,
    email          VARCHAR(45) NOT NULL,
    reputation     INT         NULL,
    account_status ENUM ('active',
        'blocked',
        'banned',
        'compromised',
        'archived',
        'unknown'),
    CONSTRAINT ak_account_email
        UNIQUE (email)
);

CREATE TABLE bounty
(
    id         IDENTITY PRIMARY KEY,
    reputation INT       NULL,
    expiry     TIMESTAMP NOT NULL,
    creator_id BIGINT    NOT NULL,
    CONSTRAINT fk_creator_id
        FOREIGN KEY (creator_id)
            REFERENCES account (id)
);

CREATE TABLE question
(
    id             IDENTITY PRIMARY KEY,
    title          VARCHAR(255) NOT NULL,
    description    TEXT         NOT NULL,
    view_count     INT          NOT NULL,
    vote_count     INT          NOT NULL,
    creation_time  TIMESTAMP    NOT NULL,
    update_time    TIMESTAMP    NOT NULL,
    status         ENUM ('open',
        'close',
        'on_hold',
        'deleted'),
    closing_remark ENUM ('duplicate',
        'offtopic',
        'too_broad',
        'not_constructive',
        'not_real_question',
        'primarly_opinion_based',
        'advertising',
        'abuse',
        'spam',
        'not_marked_for_closing'),
    author_id      BIGINT       NOT NULL,
    bounty_id      BIGINT       NULL,
    CONSTRAINT fk_author_id
        FOREIGN KEY (author_id)
            REFERENCES account (id),
    CONSTRAINT fk_bounty_id
        FOREIGN KEY (bounty_id)
            REFERENCES bounty (id)
            ON DELETE SET NULL
);

CREATE TABLE answer
(
    id            IDENTITY PRIMARY KEY,
    answer_text   TEXT      NOT NULL,
    accepted      BOOLEAN   NOT NULL DEFAULT false,
    vote_count    INT       NULL,
    flag_count    INT       NULL,
    creation_date TIMESTAMP NOT NULL,
    author_id     BIGINT    NOT NULL,
    question_id   BIGINT    NOT NULL,
    CONSTRAINT fk_answer_author_id
        FOREIGN KEY (author_id)
            REFERENCES account (id),
    CONSTRAINT fk_answer_question_id
        FOREIGN KEY (question_id)
            REFERENCES question (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE comment
(
    id            IDENTITY PRIMARY KEY,
    comment_text  VARCHAR(255) NOT NULL,
    creation_date TIMESTAMP    NOT NULL,
    vote_count    INT          NULL,
    author_id     BIGINT       NOT NULL,
    answer_id     BIGINT       NULL,
    question_id   BIGINT       NULL,
    CONSTRAINT fk_comment_author_id
        FOREIGN KEY (author_id)
            REFERENCES account (id),
    CONSTRAINT fk_comment_answer_id
        FOREIGN KEY (answer_id)
            REFERENCES answer (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_comment_question_id
        FOREIGN KEY (question_id)
            REFERENCES question (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);


CREATE TABLE photo
(
    id            IDENTITY PRIMARY KEY,
    photo_path    VARCHAR(1024) NOT NULL,
    creation_date TIMESTAMP     NOT NULL,
    question_id   BIGINT        NULL,
    answer_id     BIGINT        NULL,
    CONSTRAINT fk_photo_question_id
        FOREIGN KEY (question_id)
            REFERENCES question (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_photo_answer_id
        FOREIGN KEY (answer_id)
            REFERENCES answer (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE tag_question
(
    tag_id      BIGINT NOT NULL,
    question_id BIGINT NOT NULL,
    CONSTRAINT fk_tag_question_tag_id
        FOREIGN KEY (tag_id)
            REFERENCES tag (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_tag_question_question_id
        FOREIGN KEY (question_id)
            REFERENCES question (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE member_notification
(
    account_id      BIGINT NOT NULL,
    notification_id BIGINT NOT NULL,
    PRIMARY KEY (account_id, notification_id),
    CONSTRAINT fk_account_notification_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_account_notification_notification_id
        FOREIGN KEY (notification_id)
            REFERENCES notification (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE member_badge_question
(
    account_id  BIGINT NOT NULL,
    badge       ENUM ('student',
        'teacher',
        'commentator',
        'critic',
        'supporter',
        'benefactor',
        'nice_question',
        'good_question',
        'great_question'),
    question_id BIGINT NOT NULL,
    PRIMARY KEY (account_id, badge, question_id),
    CONSTRAINT fk_account_badge_question_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_account_badge_question_question_id
        FOREIGN KEY (question_id)
            REFERENCES question (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE member_voted_question
(
    account_id  BIGINT  NOT NULL,
    question_id BIGINT  NOT NULL,
    upvoted     BOOLEAN NULL,
    PRIMARY KEY (account_id, question_id),
    CONSTRAINT fk_account_voted_question_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_account_voted_question_question_id
        FOREIGN KEY (question_id)
            REFERENCES question (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE member_voted_answer
(
    account_id BIGINT  NOT NULL,
    answer_id  BIGINT  NOT NULL,
    upvoted    BOOLEAN NULL,
    PRIMARY KEY (account_id, answer_id),
    CONSTRAINT fk_account_voted_answer_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_account_voted_answer_answer_id
        FOREIGN KEY (answer_id)
            REFERENCES answer (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE member_voted_comment
(
    account_id BIGINT  NOT NULL,
    comment_id BIGINT  NOT NULL,
    upvoted    BOOLEAN NULL,
    PRIMARY KEY (account_id, comment_id),
    CONSTRAINT fk_account_voted_comment_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_account_voted_comment_comment_id
        FOREIGN KEY (comment_id)
            REFERENCES comment (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);

CREATE TABLE question_member_question_closing_remark
(
    question_id        BIGINT  NOT NULL,
    account_id         BIGINT  NOT NULL,
    closing_remark     ENUM ('duplicate',
        'offtopic',
        'too_broad',
        'not_constructive',
        'not_real_question',
        'primarly_opinion_based',
        'advertising',
        'abuse',
        'spam',
        'not_marked_for_closing'),
    marked_for_closing BOOLEAN NOT NULL,
    marked_for_deleting BOOLEAN NOT NULL,
    PRIMARY KEY (question_id, account_id, closing_remark),
    CONSTRAINT fk_question_member_question_closing_remark_question_id
        FOREIGN KEY (question_id)
            REFERENCES question (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_question_member_question_closing_remark_account_id
        FOREIGN KEY (account_id)
            REFERENCES account (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
);