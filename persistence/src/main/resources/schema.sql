CREATE TABLE IF NOT EXISTS users(
    user_id SERIAL PRIMARY KEY,
    email TEXT NOT NULL,
    phone VARCHAR(20) NOT NULL,
    UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS cars(
    car_id SERIAL PRIMARY KEY,
    plate TEXT NOT NULL,
    user_id INT NOT NULL,
    UNIQUE(car_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);