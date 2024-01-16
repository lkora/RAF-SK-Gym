-- noinspection SqlNoDataSourceInspectionForFile
DROP TABLE IF EXISTS "mailer_log";

-- Create the table
CREATE TABLE mailer_log
(
    id         SERIAL PRIMARY KEY,
    timestamp  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    topic_name VARCHAR(255),
    receiver   VARCHAR(255),
    message    TEXT
);
