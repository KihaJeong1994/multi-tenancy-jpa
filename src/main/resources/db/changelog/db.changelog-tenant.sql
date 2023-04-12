--liquibase formatted sql

--changeset example:1
CREATE TABLE ${schema}.product(
    id SERIAL PRIMARY KEY,
    version INTEGER NOT NULL  DEFAULT 0,
    name VARCHAR(255) NOT NULL
);

ALTER SEQUENCE ${schema}.product_id_seq RESTART WITH 100000;

--changeset example:2
ALTER TABLE ${schema}.product ADD phone VARCHAR(50) NULL;