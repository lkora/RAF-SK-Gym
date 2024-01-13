DELETE FROM "user";
INSERT INTO "user" (username, password, email, birth_date, first_name, last_name, user_type, is_banned)
VALUES ('admin', '$2a$12$oSBM6QQ19VHBMyvmc1cKzuP/AbNmE39RwIOMdfG4mewL5xwAlE2eC', 'johndoe@example.com', '1990-01-01',
        'John', 'Doe', 'admin', false),
       ('djues3', '$2a$12$0UlMQTjoMv6SqR0/YjyQCeHlMFm18e6nWKkFpUynwQhPo43JhYt/2', 'djues3@gmail.com', '2002-11-03',
        'David', 'Djuretanovic', 'client', false),
       ('idkman', ' $2a$12$9B3ITDCcRk3A//LoV8t32Oz6EaKfzUsAwCuJi6/pqVoY2JjNSrnDW ', 'example@example.com', '1999-03-03',
        'Idk', 'Man', 'admin', false);