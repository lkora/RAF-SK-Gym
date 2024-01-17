INSERT INTO gym (name, description, number_of_trainers)
VALUES ('Gym A', 'Gym A is well-equipped for all your fitness needs.', 5);
INSERT INTO gym (name, description, number_of_trainers)
VALUES ('Gym B', 'Gym B specializes in body building and power training.', 7);

-- Populating training_type table
INSERT INTO training_type (gym_id, name, price, category)
VALUES (1, 'Yoga', 30.0, 'group');
INSERT INTO training_type (gym_id, name, price, category)
VALUES (1, 'Powerlifting', 20.0, 'individual');
INSERT INTO training_type (gym_id, name, price, category)
VALUES (2, 'Pilates', 25.0, 'group');
INSERT INTO training_type (gym_id, name, price, category)
VALUES (2, 'Calisthenics', 15.0, 'individual');

-- Populating appointment table
INSERT INTO appointment (training_type_id, gym_id, user_id, day_of_week, start_time, participants)
VALUES (1, 1, 1, 'Monday', '10:00', 1);
INSERT INTO appointment (training_type_id, gym_id, user_id, day_of_week, start_time, participants)
VALUES (2, 1, 2, 'Wednesday', '14:00', 1);
INSERT INTO appointment (training_type_id, gym_id, user_id, day_of_week, start_time, participants)
VALUES (3, 2, 3, 'Friday', '16:00', 3);
INSERT INTO appointment (training_type_id, gym_id, user_id, day_of_week, start_time, participants)
VALUES (4, 2, 1, 'Saturday', '10:00', 1);

-- Populating loyal_customer_offer table
INSERT INTO loyal_customer_offer (gym_id, number_of_appointments, benefit_description)
VALUES (1, 10, 'Every tenth training session is free in Gym A');
INSERT INTO loyal_customer_offer (gym_id, number_of_appointments, benefit_description)
VALUES (2, 8, 'Every eighth training session is free in Gym B');