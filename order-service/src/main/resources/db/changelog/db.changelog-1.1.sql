-- liquibase formatted sql
-- changeset huytd:1.1

create table if not exists "order"
(
    id                bigserial
    constraint order_pk
    primary key,
    customer_name     varchar(255),
    user_id           bigint,
    address           int,
    payment_method    int,
    order_total_value decimal,
    status            int     default 0,
    created_at        timestamp,
    updated_at        timestamp,
    is_deleted        boolean default false
    );

create table if not exists order_detail
(
    id         bigserial
    constraint order_detail_pk
    primary key,
    order_id   bigint,
    product_id varchar(255),
    quantity   int,
    created_at timestamp,
    updated_at timestamp,
    is_deleted boolean default false
    );

alter table order_detail
    add constraint order_detail_order_id_fk
        foreign key (order_id) references "order";

create index if not exists order_detail_order_id_is_deleted_index
    on order_detail (order_id desc, is_deleted asc);