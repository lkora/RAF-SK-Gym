-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE "user"
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    email      VARCHAR(255) UNIQUE,
    birth_date DATE,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    user_type  VARCHAR(20)  NOT NULL CHECK (user_type IN ('admin', 'client', 'manager')),
    is_banned  boolean
);

CREATE TABLE "client"
(
    client_id                     INT REFERENCES "user" (id) ON DELETE CASCADE,
    member_card_number            INT UNIQUE,
    number_of_scheduled_trainings INT
);

CREATE TABLE "manager"
(
    manager_id INT REFERENCES "user" (id) ON DELETE CASCADE,
    gym_name   VARCHAR(255),
    hire_date  DATE
);