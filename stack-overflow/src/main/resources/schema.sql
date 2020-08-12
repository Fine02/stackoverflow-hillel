BEGIN TRANSACTION;

DROP TABLE IF EXISTS notification, tag, account, bounty, question, answer, comment, photo, notification,
    tag_question, member_notification, member_badge_question, member_voted_question, member_voted_answer,
    member_voted_comment, question_member_question_closing_remark;

DROP TYPE IF EXISTS account_status_type, question_closing_remark_type, question_status_type, badge_type;

CREATE TYPE account_status_type AS ENUM ('active',
    'blocked',
    'banned',
    'compromised',
    'archived',
    'unknown');

CREATE TYPE question_closing_remark_type AS ENUM ('duplicate',
    'offtopic',
    'too_broad',
    'not_constructive',
    'not_real_question',
    'primarly_opinion_based',
    'advertising',
    'abuse',
    'spam',
    'not_marked_for_closing');

CREATE TYPE question_status_type AS ENUM ('open',
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

create table notification
(
    id         bigserial not null
        constraint notification_pkey
            primary key,
    created_on timestamp not null,
    content    text      not null
);

alter table notification
    owner to postgres;


create table tag
(
    id          bigserial    not null
        constraint tag_pkey
            primary key,
    name        varchar(128) not null
        constraint ak_tag_name
            unique,
    description varchar(255)
);

alter table tag
    owner to postgres;

create table account
(
    id             bigserial   not null
        constraint account_pkey
            primary key,
    password       varchar(45) not null,
    name           varchar(45) not null,
    email          varchar(45) not null
        constraint ak_account_email
            unique,
    reputation     integer,
    account_status account_status_type
);

alter table account
    owner to postgres;

create table bounty
(
    id         bigserial not null
        constraint bounty_pkey
            primary key,
    reputation integer,
    expiry     timestamp not null,
    creator_id bigint    not null
        constraint fk_creator_id
            references account
);

alter table bounty
    owner to postgres;


create table question
(
    id             bigserial    not null
        constraint question_pkey
            primary key,
    title          varchar(255) not null,
    description    text         not null,
    view_count     integer,
    vote_count     integer,
    creation_time  timestamp    not null,
    update_time    timestamp    not null,
    status         question_status_type,
    closing_remark question_closing_remark_type,
    author_id      bigint       not null
        constraint fk_author_id
            references account,
    bounty_id      bigint
        constraint fk_bounty_id
            references bounty
            on delete set null
);

alter table question
    owner to postgres;




create table answer
(
    id            bigserial             not null
        constraint answer_pkey
            primary key,
    answer_text   text                  not null,
    accepted      boolean default false not null,
    vote_count    integer,
    flag_count    integer,
    creation_date timestamp             not null,
    author_id     bigint                not null
        constraint fk_answer_author_id
            references account,
    question_id   bigint                not null
        constraint fk_answer_question_id
            references question
            on update cascade on delete cascade
);

alter table answer
    owner to postgres;

create table comment
(
    id            bigserial    not null
        constraint comment_pkey
            primary key,
    comment_text  varchar(255) not null,
    creation_date timestamp    not null,
    vote_count    integer,
    author_id     bigint       not null
        constraint fk_comment_author_id
            references account,
    answer_id     bigint
        constraint fk_comment_answer_id
            references answer
            on update cascade on delete cascade,
    question_id   bigint
        constraint fk_comment_question_id
            references question
            on update cascade on delete cascade
);

alter table comment
    owner to postgres;


create table photo
(
    id            bigserial     not null
        constraint photo_pkey
            primary key,
    photo_path    varchar(1024) not null,
    creation_date timestamp     not null,
    question_id   bigint
        constraint fk_photo_question_id
            references question
            on update cascade on delete cascade,
    answer_id     bigint
        constraint fk_photo_answer_id
            references answer
            on update cascade on delete cascade
);

alter table photo
    owner to postgres;


create table tag_question
(
    tag_id      bigint not null
        constraint fk_tag_question_tag_id
            references tag
            on update cascade on delete cascade,
    question_id bigint not null
        constraint fk_tag_question_question_id
            references question
            on update cascade on delete cascade
);

alter table tag_question
    owner to postgres;


create table member_notification
(
    account_id      bigint not null
        constraint fk_account_notification_account_id
            references account
            on update cascade on delete cascade,
    notification_id bigint not null
        constraint fk_account_notification_notification_id
            references notification
            on update cascade on delete cascade,
    constraint member_notification_pkey
        primary key (account_id, notification_id)
);

alter table member_notification
    owner to postgres;



create table member_badge_question
(
    account_id  bigint     not null
        constraint fk_account_badge_question_account_id
            references account
            on update cascade on delete cascade,
    badge       badge_type not null,
    question_id bigint     not null
        constraint fk_account_badge_question_question_id
            references question
            on update cascade on delete cascade,
    constraint member_badge_question_pkey
        primary key (account_id, badge, question_id)
);

alter table member_badge_question
    owner to postgres;
create table member_voted_question
(
    account_id  bigint not null
        constraint fk_account_voted_question_account_id
            references account
            on update cascade on delete cascade,
    question_id bigint not null
        constraint fk_account_voted_question_question_id
            references question
            on update cascade on delete cascade,
    upvoted     boolean,
    constraint member_voted_question_pkey
        primary key (account_id, question_id)
);

alter table member_voted_question
    owner to postgres;



create table member_voted_answer
(
    account_id bigint not null
        constraint fk_account_voted_answer_account_id
            references account
            on update cascade on delete cascade,
    answer_id  bigint not null
        constraint fk_account_voted_answer_answer_id
            references answer
            on update cascade on delete cascade,
    upvoted    boolean,
    constraint member_voted_answer_pkey
        primary key (account_id, answer_id)
);

alter table member_voted_answer
    owner to postgres;



create table member_voted_comment
(
    account_id bigint not null
        constraint fk_account_voted_comment_account_id
            references account
            on update cascade on delete cascade,
    comment_id bigint not null
        constraint fk_account_voted_comment_comment_id
            references comment
            on update cascade on delete cascade,
    upvoted    boolean,
    constraint member_voted_comment_pkey
        primary key (account_id, comment_id)
);

alter table member_voted_comment
    owner to postgres;


create table question_member_question_closing_remark
(
    question_id         bigint                       not null
        constraint fk_question_member_question_closing_remark_question_id
            references question
            on update cascade on delete cascade,
    account_id          bigint                       not null
        constraint fk_question_member_question_closing_remark_account_id
            references account
            on update cascade on delete cascade,
    closing_remark      question_closing_remark_type not null,
    marked_for_closing  boolean                      not null,
    marked_for_deleting boolean                      not null,
    constraint question_member_question_closing_remark_pkey
        primary key (question_id, account_id, closing_remark)
);

alter table question_member_question_closing_remark
    owner to postgres;


COMMIT TRANSACTION;