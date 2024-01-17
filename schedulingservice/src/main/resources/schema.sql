DROP TABLE IF EXISTS gym CASCADE;
DROP TABLE IF EXISTS training CASCADE;
DROP TABLE IF EXISTS gym_training CASCADE;
DROP TABLE IF EXISTS appointment CASCADE;
DROP TABLE IF EXISTS cancellation_policy CASCADE;

CREATE TABLE IF NOT EXISTS gym (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    number_of_trainers INTEGER
);

CREATE TABLE IF NOT EXISTS training (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(10) CHECK (category in ('individual', 'group'))
);

CREATE TABLE IF NOT EXISTS gym_training (
    id SERIAL PRIMARY KEY,
    gym_id INTEGER REFERENCES gym(id),
    training_id INTEGER REFERENCES training(id),
    price DECIMAL(10,2)
);

CREATE TABLE IF NOT EXISTS appointment (
    id SERIAL PRIMARY KEY,
    gym_training_id INTEGER REFERENCES gym_training(id) ON DELETE RESTRICT,
    day_of_week VARCHAR(9),
    start_time TIME,
    participants INTEGER DEFAULT 1,
    registered_users INTEGER DEFAULT 0,
    is_cancelled BOOLEAN DEFAULT FALSE,
    cancelled_by INTEGER
);

CREATE TABLE IF NOT EXISTS user_appointments (
    user_id INTEGER,
    appointment_id INTEGER REFERENCES appointment(id),
    PRIMARY KEY (user_id, appointment_id)
);
CREATE TABLE IF NOT EXISTS loyal_customer_offer (
    id SERIAL PRIMARY KEY,
    gym_id INTEGER REFERENCES gym(id),
    number_of_appointments INTEGER,
    benefit_description TEXT
);