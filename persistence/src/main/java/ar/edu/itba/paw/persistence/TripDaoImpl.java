package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.persistence.CityDao;
import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.*;

//Timestamp en SQL
//LocalDateTime para Java
//En Java no usar los SQL

@Repository
public class TripDaoImpl implements TripDao {

    //NO anidar DAO's, son muchos llamados a la BD
    //Hacer los NATURAL JOIN

    //TODO: preguntar si usamos metodos de otros DAO's o hacemos Natural Join
    private RowMapper<Trip> ROW_MAPPER = (resultSet,rowNum)-> {
        return new Trip(
                resultSet.getLong("trip_id"),
                new City(resultSet.getLong("origin_city_id"),resultSet.getString("origin_city_name"),resultSet.getLong("origin_province_id")),
                resultSet.getString("origin_address"),
                new City(resultSet.getLong("destination_city_id"),resultSet.getString("destination_city_name"),resultSet.getLong("destination_province_id")),
                resultSet.getString("destination_address"),
                resultSet.getTimestamp("origin_date_time").toLocalDateTime(),
                resultSet.getInt("max_passengers"),
                new User(resultSet.getLong("user_id"),resultSet.getString("user_email"),resultSet.getString("user_phone")),
                new Car(resultSet.getLong("car_id"),resultSet.getString("car_plate"),resultSet.getString("car_info_car"),resultSet.getLong("user_id")),
                resultSet.getInt("occupied_seats")
        );
    };
//
    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert tripsInsert;
    private final SimpleJdbcInsert driverCarInsert;
    private final SimpleJdbcInsert passengerInsert;
    @Autowired
    public TripDaoImpl(final DataSource tripDataSource, CityDao cityDao, UserDao userDao, CarDao carDao){
        this.jdbcTemplate = new JdbcTemplate(tripDataSource);
        this.tripsInsert = new SimpleJdbcInsert(tripDataSource)
                .usingGeneratedKeyColumns("trip_id")
                .withTableName("trips");
        this.driverCarInsert = new SimpleJdbcInsert(tripDataSource)
                .withTableName("trips_cars_drivers");
        this.passengerInsert = new SimpleJdbcInsert(tripDataSource)
                .withTableName("passengers");
    }

    @Override
    public Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDateTime originDateTime, final double price, final int max_passengers, final User driver) {
        //Insertamos los datos en la tabla trip
        Map<String, Object> tripData = new HashMap<>();
        tripData.put("max_passengers",max_passengers);
        tripData.put("origin_address",originAddress);
        tripData.put("destination_address",destinationAddress);
        tripData.put("price",price);
        tripData.put("origin_date_time",originDateTime);
        tripData.put("destination_city_id",destinationCity.getId());
        tripData.put("origin_city_id",originCity.getId());
        Number tripKey = tripsInsert.executeAndReturnKey(tripData);
        //Insertamos el auto y usuario en la tabla trips_cars_drivers
        Map<String,Object> driverCarData = new HashMap<>();
        driverCarData.put("trip_id",tripKey.longValue());
        driverCarData.put("user_id",driver.getUserId());
        driverCarData.put("car_id",car.getCarId());
        driverCarInsert.execute(driverCarData);
        return new Trip(tripKey.longValue(),originCity,originAddress,destinationCity,destinationAddress,originDateTime,max_passengers,driver,car, 0);
    }

    @Override
    public boolean addPassenger(final Trip trip, final User passenger){
        if(trip == null || passenger == null){
            return false;
        }
        Map<String,Object> passengerData = new HashMap<>();
        passengerData.put("user_id",passenger.getUserId());
        passengerData.put("trip_id",trip.getTripId());
        //TODO: ver si es 1
        return passengerInsert.execute(passengerData)>0;
    }

    @Override
    public List<User> getPassengers(final long tripId){
        return jdbcTemplate.query("SELECT * FROM passengers NATURAL JOIN users WHERE trip_id=?",UserDaoImpl.ROW_MAPPER,tripId);
    }

    @Override
    public List<Trip> getFirstNTrips(long n){
        return jdbcTemplate.query("SELECT trips.trip_id, trips.max_passengers, trips.origin_date_time, trips.origin_address, origin.name as origin_city_name, origin.city_id as origin_city_id, origin.province_id as origin_province_id, trips.destination_address, destination.name as destination_city_name, destination.city_id as destination_city_id, destination.province_id as destination_province_id, users.email as user_email, users.user_id as user_id, users.phone as user_phone, cars.car_id as car_id, cars.plate  as car_plate, cars.info_car as car_info_car, count(passengers.user_id) as occupied_seats\n" +
                "FROM trips NATURAL JOIN trips_cars_drivers NATURAL JOIN users NATURAL JOIN cars JOIN cities origin ON trips.origin_city_id = origin.city_id JOIN cities destination ON trips.destination_city_id=destination.city_id LEFT OUTER JOIN passengers ON passengers.trip_id = trips.trip_id\n" +
                "GROUP BY trips.trip_id, trips.origin_date_time, trips.max_passengers, trips.origin_address, origin.name, origin.city_id, origin.province_id, destination_address, destination.name, destination.city_id, destination.province_id, users.email, users.user_id, users.phone, cars.car_id, cars.plate, cars.info_car\n" +
                "ORDER BY trips.origin_date_time ASC \n" +
                "LIMIT ?;",ROW_MAPPER,n);
    }
    //Preguntar:
    //reutilizar query -> concatenar string
    //pasar timestamp -> Probar
    //application properties condicional -> comentar el que no usamos (o crear perfiles)
    //ROWMAPPER globales -> hacer package protected
    //Backup BD (si no la semana que viene)
    @Override
    public List<Trip> getTripsByDateTimeAndOriginAndDestination(long origin_city_id, long destination_city_id, LocalDateTime dateTime){
        return jdbcTemplate.query("SELECT trips.trip_id, trips.max_passengers, trips.origin_date_time, trips.origin_address, origin.name as origin_city_name, origin.city_id as origin_city_id, origin.province_id as origin_province_id, trips.destination_address, destination.name as destination_city_name, destination.city_id as destination_city_id, destination.province_id as destination_province_id, users.email as user_email, users.user_id as user_id, users.phone as user_phone, cars.car_id as car_id, cars.plate  as car_plate, cars.info_car as car_info_car, count(passengers.user_id) as occupied_seats\n" +
                "FROM trips NATURAL JOIN trips_cars_drivers NATURAL JOIN users NATURAL JOIN cars JOIN cities origin ON trips.origin_city_id = origin.city_id JOIN cities destination ON trips.destination_city_id=destination.city_id LEFT OUTER JOIN passengers ON passengers.trip_id = trips.trip_id\n" +
                "WHERE origin.city_id = ? AND destination.city_id =? AND trips.origin_date_time = ? "+
                "GROUP BY trips.trip_id,trips.origin_date_time, trips.max_passengers, trips.origin_address, origin.name, origin.city_id, origin.province_id, destination_address, destination.name, destination.city_id, destination.province_id, users.email, users.user_id, users.phone, cars.car_id, cars.plate, cars.info_car\n" +
                "ORDER BY trips.origin_date_time ASC \n" +
                "LIMIT 10;",ROW_MAPPER,origin_city_id,destination_city_id, dateTime);
    }
    @Override
    public Optional<Trip> findById(long tripId) {
        return jdbcTemplate.query("SELECT trips.trip_id, trips.max_passengers, trips.origin_date_time, trips.origin_address, origin.name as origin_city_name, origin.city_id as origin_city_id, origin.province_id as origin_province_id, trips.destination_address, destination.name as destination_city_name, destination.city_id as destination_city_id, destination.province_id as destination_province_id, users.email as user_email, users.user_id as user_id, users.phone as user_phone, cars.car_id as car_id, cars.plate  as car_plate, cars.info_car as car_info_car, count(passengers.user_id) as occupied_seats\n" +
                "FROM trips NATURAL JOIN trips_cars_drivers NATURAL JOIN users NATURAL JOIN cars JOIN cities origin ON trips.origin_city_id = origin.city_id JOIN cities destination ON trips.destination_city_id=destination.city_id LEFT OUTER JOIN passengers ON passengers.trip_id = trips.trip_id\n" +
                "WHERE trips.trip_id = ?\n"+
                "GROUP BY trips.trip_id, trips.max_passengers, trips.origin_address, origin.name, origin.city_id, origin.province_id, destination_address, destination.name, destination.city_id, destination.province_id, users.email, users.user_id, users.phone, cars.car_id, cars.plate, cars.info_car;",ROW_MAPPER,tripId).stream().findFirst();
    }
}
