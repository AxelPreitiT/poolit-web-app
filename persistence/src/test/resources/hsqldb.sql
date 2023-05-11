CREATE TABLE IF NOT EXISTS images(
                                     image_id IDENTITY PRIMARY KEY,
                                     bytea BLOB
);
CREATE TABLE IF NOT EXISTS provinces (
                                         province_id IDENTITY NOT NULL,
                                         name VARCHAR(2000) NOT NULL,
                                         UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS cities (
                                      city_id IDENTITY NOT NULL,
                                      name VARCHAR(2000) NOT NULL,
                                      province_id INT NOT NULL,
                                      UNIQUE (province_id, name),
                                      CONSTRAINT cities_to_provinces FOREIGN KEY (province_id) REFERENCES provinces(province_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS users(
                                    user_id IDENTITY PRIMARY KEY,
                                    username VARCHAR(2000),
                                    surname VARCHAR(2000),
                                    email VARCHAR(2000) NOT NULL,
                                    phone VARCHAR(20) NOT NULL,
                                    password VARCHAR(2000),
                                    birthdate TIMESTAMP,
                                    city_id INT,
                                    user_role VARCHAR(200),
                                    user_image_id INT,
                                    UNIQUE(email),
                                    CONSTRAINT users_to_images FOREIGN KEY (user_image_id) REFERENCES images (image_id) ON DELETE SET NULL,
                                    CONSTRAINT users_to_cities FOREIGN KEY (city_id) REFERENCES cities(city_id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS cars(
                                   car_id IDENTITY,
                                   plate VARCHAR(2000) NOT NULL,
                                   info_car VARCHAR(2000) NOT NULL,
                                   user_id INT NOT NULL,
                                   image_id INT DEFAULT 1,
                                   UNIQUE(user_id,plate),
                                   CONSTRAINT cars_to_users FOREIGN KEY (user_id) REFERENCES users (user_id),
                                   CONSTRAINT cars_to_images FOREIGN KEY (image_id) REFERENCES images (image_id)
);
CREATE TABLE IF NOT EXISTS trips(
                                    trip_id IDENTITY PRIMARY KEY,
                                    max_passengers INT,
                                    origin_address VARCHAR(2000) NOT NULL ,
                                    destination_address VARCHAR(2000) NOT NULL,
                                    price DOUBLE PRECISION DEFAULT 0.0,
                                    start_date_time TIMESTAMP NOT NULL,
                                    end_date_time TIMESTAMP NOT NULL,
--     is_recurrent BOOLEAN DEFAULT false,
-- day_of_week es 1->Lunes, 7->Domingo
                                    day_of_week INT NOT NULL,
                                    origin_city_id INT NOT NULL,
                                    destination_city_id INT NOT NULL,
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
--     TODO: ver que pasa si se elimina un auto (CASCADE/SET NULL/...)
                                                 car_id INT,
                                                 PRIMARY KEY(trip_id,user_id),
                                                 UNIQUE(trip_id,car_id),
                                                 CONSTRAINT trips_to_users FOREIGN KEY(user_id) REFERENCES users(user_id),
                                                 CONSTRAINT trips_to_cars FOREIGN KEY(car_id) references cars(car_id)
);
