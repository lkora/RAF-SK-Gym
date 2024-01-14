-- noinspection SqlNoDataSourceInspectionForFile
DROP TABLE "user" CASCADE;
DROP TABLE "client";
DROP TABLE "manager";
DROP TABLE "confirmation_token";

CREATE TABLE IF NOT EXISTS "user"
(
    id              SERIAL PRIMARY KEY,
    username        VARCHAR(255) NOT NULL UNIQUE,
    password        VARCHAR(255) NOT NULL,
    email           VARCHAR(255) UNIQUE,
    birth_date      DATE,
    first_name      VARCHAR(255),
    last_name       VARCHAR(255),
    user_type       VARCHAR(20)  NOT NULL CHECK (user_type IN ('admin', 'client', 'manager')),
    is_banned       boolean,
    is_activated    boolean
);

CREATE TABLE IF NOT EXISTS "client"
(
    client_id                     INT REFERENCES "user" (id) ON DELETE CASCADE,
    member_card_number            INT UNIQUE,
    number_of_scheduled_trainings INT
);

CREATE TABLE IF NOT EXISTS "manager"
(
    manager_id INT REFERENCES "user" (id) ON DELETE CASCADE,
    gym_name   VARCHAR(255),
    hire_date  DATE
);

CREATE TABLE IF NOT EXISTS "confirmation_token" (
    id                  SERIAL PRIMARY KEY,
    confirmation_token  UUID UNIQUE NOT NULL,
    created_at          TIMESTAMP NOT NULL,
    user_id             INT REFERENCES "user"(id) ON DELETE CASCADE
);