INSERT INTO images values (1,null);
INSERT INTO provinces values (1,'CABA');
INSERT INTO provinces values (2,'Buenos Aires');
INSERT INTO provinces values (3,'Santa Fe');
INSERT INTO provinces values (4,'Neuquen');
INSERT INTO provinces values (5,'Misiones');
INSERT INTO cities values (1,'Recoleta',1);
INSERT INTO cities values (2,'Parque Patricios',1);
INSERT INTO cities values (3,'San Nicolas',1);
INSERT INTO cities values (4,'Palermo',1);
INSERT INTO cities values (5,'Puerto Madero',1);
INSERT INTO users(user_id, username, surname, email, phone, password,city_id,user_image_id,mail_locale,user_role) values (1,'John','Doe','jonhdoe@mail.com','1234567800','1234',1,1,'en','USER')
INSERT INTO users(user_id, username, surname, email, phone, password,city_id,user_image_id,mail_locale,user_role) values (2,'John','Doe','jonhdoe2@mail.com','1234567800','1234',1,1,'en','DRIVER');
INSERT INTO cars(car_id, plate, info_car, user_id, image_id,seats,brand) values (1,'AA000AA','Fit azul',1,1,4,'UNKNOWN');
INSERT INTO cars(car_id, plate, info_car, user_id, image_id,seats) values (2,'BB000BB','Fit azul',1,1,4);
INSERT INTO tokens(token_id, date, token, user_id) values (1,current_date,'1234567890',2);
INSERT INTO trips(trip_id, max_passengers, origin_address, destination_address, price, start_date_time, end_date_time, day_of_week, origin_city_id, destination_city_id, driver_id, car_id)
            VALUES (1,3,'Av Callao 1348','ITBA',1200.0,timestamp '2023-07-03 23:30:00.000000',timestamp '2023-07-03 23:30:00.000000',1,1,1,1,1);
INSERT INTO trips(trip_id, max_passengers, origin_address, destination_address, price, start_date_time, end_date_time, day_of_week, origin_city_id, destination_city_id, driver_id, car_id)
            VALUES (2,3,'Av Callao 1348','ITBA',1200.0,timestamp '2023-07-03 23:30:00.000000',timestamp '2023-07-17 23:30:00.000000',1,1,1,1,1);
INSERT INTO passengers(trip_id, user_id, start_date, end_date) VALUES (2,1,timestamp '2023-07-03 23:30:00.000000',timestamp '2023-07-03 23:30:00.000000');
INSERT INTO passengers(trip_id, user_id, start_date, end_date) VALUES (2,2,timestamp '2023-07-03 23:30:00.000000',timestamp '2023-07-10 23:30:00.000000');