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
import java.sql.Date;
import java.sql.Time;
import java.util.*;

@Repository
public class TripDaoImpl implements TripDao {
    @Autowired
    private CityDao cityDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CarDao carDao;

    //TODO: preguntar si usamos metodos de otros DAO's o hacemos Natural Join
    private RowMapper<Trip> ROW_MAPPER = (resultSet,rowNum)-> {
        long tripId = resultSet.getLong("trip_id");
        City originCity = cityDao.findCityById(resultSet.getLong("origin_city_id")).get();
        City destinationCity = cityDao.findCityById(resultSet.getLong("destination_city_id")).get();
        User driver = userDao.findById(resultSet.getLong("user_id")).get();
        Car car = getCar(tripId).get();
        List<User> passengers = getPassengers(tripId);
        return new Trip(tripId,
                originCity,
                resultSet.getString("origin_address"),
                destinationCity,
                resultSet.getString("destination_address"),
                resultSet.getDate("origin_date"),
                resultSet.getTime("origin_time"),
                resultSet.getInt("max_passengers"),
                driver,
                car,
                passengers);
    };
//
    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert tripsInsert;
    private final SimpleJdbcInsert driverCarInsert;
    private final SimpleJdbcInsert passengerInsert;
    @Autowired
    public TripDaoImpl(final DataSource tripDataSource){
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
    public Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final String plate, final Date date, final Time time,final double price, final int max_passengers, User driver) {
        //Insertamos los datos en la tabla trip
        Map<String, Object> tripData = new HashMap<>();
        tripData.put("max_passengers",max_passengers);
        tripData.put("origin_address",originAddress);
        tripData.put("destination_address",destinationAddress);
        tripData.put("price",price);
        tripData.put("origin_time", time);
        tripData.put("origin_date",date);
        tripData.put("destination_city_id",destinationCity.getId());
        tripData.put("origin_city_id",originCity.getId());
        Number tripKey = tripsInsert.executeAndReturnKey(tripData);
        //Insertamos el auto y usuario en la tabla trips_cars_drivers
        Map<String,Object> driverCarData = new HashMap<>();
        driverCarData.put("trip_id",tripKey.longValue());
        driverCarData.put("user_id",driver.getUserId());
        driverCarData.put("car_id",car.getCarId());
        driverCarInsert.execute(driverCarData);
        return new Trip(tripKey.longValue(),originCity,originAddress,destinationCity,destinationAddress,date,time,max_passengers,driver,car,new ArrayList<>());
    }
    private Optional<User> getDriver(long tripId){
        return jdbcTemplate.query("SELECT user_id FROM trips_cars_drivers WHERE trip_id=?",(resultSet,rowNum)->userDao.findById(resultSet.getLong("user_id")).get(),tripId).stream().findFirst();
    }
    private Optional<Car> getCar(long tripId){
        return jdbcTemplate.query("SELECT user_id FROM trips_cars_drivers WHERE trip_id=?",(resultSet,rowNum)->carDao.findById(resultSet.getLong("car_id")).get(),tripId).stream().findFirst();
    }

    @Override
    public boolean addPassenger(final Trip trip, final User passenger){
        if(trip == null || passenger == null){
            return false;
        }
        Map<String,Object> passengerData = new HashMap<>();
        passengerData.put("user_id",passenger.getUserId());
        passengerData.put("trip_id",trip.getTripId());
        return passengerInsert.execute(passengerData)>0;
    }

    //TODO: preguntar si lo hacemos con Natural Join o usamos los otros DAO's
    private List<User> getPassengers(final long tripId){
        return jdbcTemplate.query("SELECT * FROM passengers NATURAL JOIN users WHERE trip_id=?",(resultSet, rowNumber)-> new User(resultSet.getLong("user_id"),resultSet.getString("email"),resultSet.getString("phone")),tripId);
    }
    @Override
    public Optional<Trip> findById(long tripId) {
        return jdbcTemplate.query("SELECT * FROM trips WHERE trip_id = ?",ROW_MAPPER,tripId).stream().findFirst();
    }
}
