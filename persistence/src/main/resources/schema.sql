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
CREATE TABLE IF NOT EXISTS trips(
    trip_id SERIAL PRIMARY KEY,
    max_passengers INT,
    origin_address TEXT NOT NULL ,
    destination_address text NOT NULL,
    price DOUBLE PRECISION,
    origin_date_time TIMESTAMP NOT NULL,
    origin_city_id INT NOT NULL,
    destination_city_id INT NOT NULL,
    CONSTRAINT trips_to_origin FOREIGN KEY(origin_city_id) REFERENCES cities(city_id),
    CONSTRAINT trips_to_destination FOREIGN KEY(destination_city_id) REFERENCES cities(city_id)
    --     driver_id INT NOT NULL ,
--     car_id INT NOT NULL,
--     CONSTRAINT trip_to_driver FOREIGN KEY(driver_id) REFERENCES users(user_id),
--     CONSTRAINT trip_to_car FOREIGN KEY(car_id) REFERENCES cars(car_id)
);
--  Represents the relationship between users and trips
--     users --N--[Reserves]--M-- trips
create TABLE IF NOT EXISTS passengers(
    trip_id INT NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (trip_id,user_id),
    CONSTRAINT passengers_to_trip FOREIGN KEY(trip_id) REFERENCES trips(trip_id),
    CONSTRAINT passengers_to_users FOREIGN KEY(user_id) REFERENCES users(user_id)
);
-- Represents the relationship between cars, users and trips
CREATE TABLE IF NOT EXISTS trips_cars_drivers(
    trip_id INT NOT NULL,
    user_id INT NOT NULL,
--     TODO: ver que pasa si se elimina un auto (CASCADE/SET NULL/...)
    car_id INT,
    PRIMARY KEY(trip_id,user_id),
    UNIQUE(trip_id,car_id),
    CONSTRAINT trips_to_users FOREIGN KEY(user_id) REFERENCES users(user_id),
    CONSTRAINT trips_to_cars FOREIGN KEY(car_id) references cars(car_id)
);
