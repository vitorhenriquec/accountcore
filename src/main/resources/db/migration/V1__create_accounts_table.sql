CREATE TABLE accounts (
    id int8 NOT NULL,
    document_number VARCHAR(255) NOT NULL UNIQUE,
    created_at timestamp NOT NULL,
    updated_at timestamp NULL,
    CONSTRAINT accounts_pk PRIMARY KEY (id)
);

CREATE SEQUENCE accounts_seq_id INCREMENT BY 1
START WITH 1
MAXVALUE 99999999999
MINVALUE 1
CACHE 10;