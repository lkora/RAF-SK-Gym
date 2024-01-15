DELETE FROM "user";
INSERT INTO "user" (username, password, email, birth_date, first_name, last_name, user_type, is_banned, is_activated)
VALUES ('admin', '$2a$12$oSBM6QQ19VHBMyvmc1cKzuP/AbNmE39RwIOMdfG4mewL5xwAlE2eC', 'johndoe@example.com', '1990-01-01',
        'John', 'Doe', 'admin', false, true),
       ('djues3', '$2a$12$0UlMQTjoMv6SqR0/YjyQCeHlMFm18e6nWKkFpUynwQhPo43JhYt/2', 'djues3@gmail.com', '2002-11-03',
        'David', 'Djuretanovic', 'client', false, true),
       ('idkman', '$2a$12$ueBiNeHrkkm9zaS/pszDguf1B3WNFaq5uGeCOTpxL4zcfS/TPXNKS', 'example@example.com', '1999-03-03',
        'Idk', 'Man', 'manager', false, true);
DELETE FROM "client";
INSERT INTO "client" (client_id, member_card_number, number_of_scheduled_trainings)
VALUES (2, 2, 0);
DELETE FROM "manager";
INSERT INTO "manager" (manager_id, gym_name, hire_date)
VALUES (3, 'Master Physical', '2017-09-25');