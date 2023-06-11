CREATE TABLE IF NOT EXISTS images(
    image_id SERIAL PRIMARY KEY,
    bytea BYTEA
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

CREATE TABLE IF NOT EXISTS users(
    user_id SERIAL PRIMARY KEY,
    username TEXT,
    surname TEXT,
    email TEXT NOT NULL,
    phone VARCHAR(20) NOT NULL,
    password TEXT,
    city_id INT,
    mail_locale TEXT NOT NULL,
    user_role TEXT,
    user_image_id INT,
    enabled BOOLEAN NOT NULL,
    UNIQUE(email),
    CONSTRAINT users_to_images FOREIGN KEY (user_image_id) REFERENCES images (image_id) ON DELETE SET NULL,
    CONSTRAINT users_to_cities FOREIGN KEY (city_id) REFERENCES cities(city_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS cars(
    car_id SERIAL PRIMARY KEY,
    plate TEXT NOT NULL,
    info_car TEXT NOT NULL,
    user_id INT NOT NULL,
    image_id INT DEFAULT 1,
    seats INT NOT NULL,
    features TEXT NOT NULL,
    brand INT NOT NULL,
    UNIQUE(user_id,plate),
    CONSTRAINT cars_to_users FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT cars_to_images FOREIGN KEY (image_id) REFERENCES images (image_id)
);

CREATE TABLE IF NOT EXISTS trips(
    trip_id SERIAL PRIMARY KEY,
    max_passengers INT,
    origin_address TEXT NOT NULL ,
    destination_address text NOT NULL,
    price DOUBLE PRECISION DEFAULT 0.0,
    start_date_time TIMESTAMP NOT NULL,
    end_date_time TIMESTAMP NOT NULL,
--     is_recurrent BOOLEAN DEFAULT false,
-- day_of_week es 1->Lunes, 7->Domingo
    day_of_week INT NOT NULL,
    origin_city_id INT NOT NULL,
    destination_city_id INT NOT NULL,
    driver_id INT NOT NULL,
    car_id INT NOT NULL,
    CONSTRAINT trips_to_drivers FOREIGN KEY (driver_id) REFERENCES users(user_id),
    CONSTRAINT trips_to_cars FOREIGN KEY (car_id) REFERENCES cars(car_id),
    CONSTRAINT trips_to_origin FOREIGN KEY(origin_city_id) REFERENCES cities(city_id),
    CONSTRAINT trips_to_destination FOREIGN KEY(destination_city_id) REFERENCES cities(city_id)
);
--  Represents the relationship between users and trips
--     users --N--[Reserves]--M-- trips
create TABLE IF NOT EXISTS passengers(
    trip_id INT NOT NULL,
    user_id INT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    PRIMARY KEY (trip_id,user_id),
    CONSTRAINT passengers_to_trip FOREIGN KEY(trip_id) REFERENCES trips(trip_id),
    CONSTRAINT passengers_to_users FOREIGN KEY(user_id) REFERENCES users(user_id)
);
-- Represents the relationship between cars, users and trips
CREATE TABLE IF NOT EXISTS trips_cars_drivers(
    trip_id INT NOT NULL,
    user_id INT NOT NULL,
    car_id INT,
    PRIMARY KEY(trip_id,user_id),
    UNIQUE(trip_id,car_id),
    CONSTRAINT trips_to_users FOREIGN KEY(user_id) REFERENCES users(user_id),
    CONSTRAINT trips_to_cars FOREIGN KEY(car_id) references cars(car_id)
);

CREATE TABLE IF NOT EXISTS reviews(
  review_id SERIAL PRIMARY KEY,
  trip_id INT NOT NULL,
  receiver_id INT NOT NULL,
  user_id INT NOT NULL,
  rating INT NOT NULL,
  review TEXT NOT NULL,
  UNIQUE(trip_id,user_id),
  CONSTRAINT reviews_to_trips FOREIGN KEY(trip_id) REFERENCES trips(trip_id) ON DELETE CASCADE,
  CONSTRAINT reviews_to_users FOREIGN KEY(user_id) REFERENCES users(user_id),
  CONSTRAINT reviews_from_receiver FOREIGN KEY(receiver_id) REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS tokens(
    token_id SERIAL PRIMARY KEY,
    token    TEXT NOT NULL,
    user_id  INT  NOT NULL,
    date TIMESTAMP NOT NULL,
    CONSTRAINT tokens_to_users FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS car_reviews(
    review_id SERIAL PRIMARY KEY,
    car_id INT NOT NULL,
    review TEXT NOT NULL,
    rating INT NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT reviews_to_users FOREIGN KEY(user_id) REFERENCES users(user_id),
    CONSTRAINT reviews_to_cars FOREIGN KEY(car_id) REFERENCES cars(car_id)
);


