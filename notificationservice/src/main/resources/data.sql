DELETE FROM "mailer_log";
INSERT INTO mailer_log (topic_name, receiver, message)
VALUES ('topic1', 'receiver1@example.com', 'This is a sample message 1'),
       ('topic2', 'receiver2@example.com', 'This is a sample message 2');
