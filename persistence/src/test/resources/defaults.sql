INSERT INTO images values (3,null);
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
INSERT INTO users(user_id, username, surname, email, phone, password,city_id,user_image_id,mail_locale,user_role) values (3,'John','Doe','jonhdoe@mail.com','1234567800','1234',1,3,'en','USER')
INSERT INTO users(user_id, username, surname, email, phone, password,city_id,user_image_id,mail_locale,user_role) values (4,'John','Doe','jonhdoe2@mail.com','1234567800','1234',1,3,'en','DRIVER');
INSERT INTO cars(car_id, plate, info_car, user_id, image_id,seats,brand) values (3,'AA000AA','Fit azul',3,3,4,'UNKNOWN');
INSERT INTO cars(car_id, plate, info_car, user_id, image_id,seats) values (4,'BB000BB','Fit azul',3,3,4);
INSERT INTO tokens(token_id, date, token, user_id) values (3,current_date,'1234567890',4);
INSERT INTO trips(trip_id, max_passengers, origin_address, destination_address, price, start_date_time, end_date_time, day_of_week, origin_city_id, destination_city_id, driver_id, car_id)
            VALUES (3,3,'Av Callao 1348','ITBA',1200.0,timestamp '2023-07-03 23:30:00.000000',timestamp '2023-07-03 23:30:00.000000',1,1,1,3,3);
INSERT INTO trips(trip_id, max_passengers, origin_address, destination_address, price, start_date_time, end_date_time, day_of_week, origin_city_id, destination_city_id, driver_id, car_id)
            VALUES (4,3,'Av Callao 1348','ITBA',1200.0,timestamp '2023-07-03 23:30:00.000000',timestamp '2023-07-17 23:30:00.000000',1,1,1,3,3);
INSERT INTO passengers(trip_id, user_id, start_date, end_date) VALUES (4,3,timestamp '2023-07-03 23:30:00.000000',timestamp '2023-07-03 23:30:00.000000');
INSERT INTO passengers(trip_id, user_id, start_date, end_date) VALUES (4,4,timestamp '2023-07-03 23:30:00.000000',timestamp '2023-07-10 23:30:00.000000');
INSERT INTO car_reviews(review_id, comment, date, option, rating, car_id, reviewer_id, trip_id)
            VALUES(3,'The space was very good',current_timestamp,'BIG_TRUNK_SPACE',4,3,4,4);
-- review de user2 para user1 por trip2 (como driver)
INSERT INTO user_reviews(review_id, comment, date, rating, reviewed_id, reviewer_id, trip_id)
            VALUES (3,'The driver was very kind',current_timestamp,4,3,4,4);
INSERT INTO driver_reviews(option, review_id) VALUES ('VERY_FRIENDLY',3);
-- review de user2 para user1 por trip2 (como pasajero)
INSERT INTO user_reviews(review_id, comment, date, rating, reviewed_id, reviewer_id, trip_id)
                VALUES (4,'The passenger was very friendly',current_timestamp,4,3,4,4);
INSERT INTO passenger_reviews(option, review_id) VALUES ('VERY_FRIENDLY',4);
-- reporte de user2 para user1 por trip2 (como driver)
INSERT INTO reports(report_id, admin_reason, date, description, reason, relation, status, reported_id, reporter_id, trip_id)
                    VALUES (3,null,current_timestamp,'He did not do the trip','MISCONDUCT','PASSENGER_2_DRIVER','IN_REVISION',3,4,4);
