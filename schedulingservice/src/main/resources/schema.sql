DROP TABLE IF EXISTS gym CASCADE;
DROP TABLE IF EXISTS training_type CASCADE;
DROP TABLE IF EXISTS gym_training_types CASCADE;



CREATE TABLE IF NOT EXISTS gym (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description TEXT,
    number_of_trainers INTEGER
);

CREATE TABLE IF NOT EXISTS training_type (
    id SERIAL PRIMARY KEY,
    gym_id INTEGER REFERENCES gym(id),
    name VARCHAR(255),
    price DECIMAL(10,2),
    category VARCHAR(10) CHECK (category in ('individual', 'group'))
);

CREATE TABLE IF NOT EXISTS appointment (
    id SERIAL PRIMARY KEY,
    training_type_id INTEGER REFERENCES training_type(id),
    gym_id INTEGER REFERENCES gym(id),
    user_id INTEGER,
    day_of_week VARCHAR(9),
    start_time TIME,
    participants INTEGER DEFAULT 1
);
CREATE TABLE IF NOT EXISTS loyal_customer_offer (
    id SERIAL PRIMARY KEY,
    gym_id INTEGER REFERENCES gym(id),
    number_of_appointments INTEGER,
    benefit_description TEXT
);