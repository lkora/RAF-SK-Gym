-- Delete existing data from the tables
DELETE FROM user_appointments;
DELETE FROM appointment;
DELETE FROM gym_training;
DELETE FROM training;
DELETE FROM loyal_customer_offer;
DELETE FROM gym;

-- Inserting new data into gym table
INSERT INTO gym(name, description, number_of_trainers)
VALUES ('Gym A', 'Best gym in town!', 5),
       ('Gym B', 'Second best gym in town!', 3);

-- Inserting data into training table
INSERT INTO training(name, category)
VALUES ('Powerlifting', 'individual'),
       ('Pilates', 'group'),
       ('Calisthenics', 'individual'),
       ('Yoga', 'group');

-- Inserting data into gym_training table
-- Assuming Gym A offers these trainings
INSERT INTO gym_training(gym_id, training_id, price)
VALUES (1, 1, 10.00),
       (1, 2, 5.00);
-- Assuming Gym B offers these trainings
INSERT INTO gym_training(gym_id, training_id, price)
VALUES (2, 3, 15.00),
       (2, 4, 20.00);

-- Inserting data into appointment table
-- For Gym A (user being the manager)
INSERT INTO appointment(gym_training_id,day_of_week, start_time)
VALUES (1, 'Monday', '09:00:00'),
       (2, 'Tuesday', '10:00:00');
-- For Gym B (user being the manager)
INSERT INTO appointment(gym_training_id, day_of_week, start_time)
VALUES (3, 'Wednesday', '11:00:00'),
       (4,'Thursday', '12:00:00');

-- Inserting data into user_appointments table
-- Booked by the client
INSERT INTO user_appointments(user_id, appointment_id)
VALUES (2, 1),
       (2, 2),
       (2, 3),
       (2, 4);

-- Inserting data into loyal_customer_offer table
INSERT INTO loyal_customer_offer(gym_id, number_of_appointments, benefit_description)
VALUES (1, 10, 'Every 10th appointment is free.'),
       (2, 8, 'Every 8th appointment is free.');