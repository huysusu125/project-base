-- liquibase formatted sql
-- changeset huytd:1.1

create table "user"
(
    id bigserial
        constraint user_pk
            primary key,
    username varchar(255) not null,
    email varchar(255) not null,
    password varchar(255),
    role_type integer,
    created_at timestamp default now(),
    updated_at timestamp default now(),
    is_deleted boolean default false
);

create unique index user_email_uindex
    on "user" (email);

create unique index user_username_uindex
    on "user" (username);



