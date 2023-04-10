CREATE TABLE IF NOT EXISTS users(
    user_id SERIAL PRIMARY KEY,
    email TEXT NOT NULL,
    phone VARCHAR(20) NOT NULL,
    UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS provinces (
    province_id SERIAL NOT NULL,
    name TEXT NOT NULL,
    PRIMARY KEY (province_id),
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS cities (
    city_id SERIAL NOT NULL,
    name TEXT NOT NULL,
    province_id INT NOT NULL,
    PRIMARY KEY (province_id, name),
    UNIQUE (city_id),
    CONSTRAINT cities_to_provinces FOREIGN KEY (province_id) REFERENCES provinces(province_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cars(
    car_id SERIAL PRIMARY KEY,
    plate TEXT NOT NULL,
    info_car TEXT NOT NULL,
    user_id INT NOT NULL,
    UNIQUE(car_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);
