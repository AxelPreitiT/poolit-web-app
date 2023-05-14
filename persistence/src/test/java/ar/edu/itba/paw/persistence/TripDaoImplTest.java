package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TripDaoImplTest {
    @Autowired
    private DataSource ds;

    @Autowired
    private TripDaoImpl tripDao;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert tripInsert;
    private SimpleJdbcInsert passengerInsert;
    private SimpleJdbcInsert tripsCarsDriversInsert;

    private static final long PROVINCE_ID = 1;
    private static final String PROVINCE_NAME = "CABA";
    private static final Province PROVINCE = new Province(PROVINCE_ID,PROVINCE_NAME);
    private static final long CITY_ID = 1;
    private static final String CITY_NAME = "Recoleta";
    private static final City CITY = new City(CITY_ID,CITY_NAME,PROVINCE_ID);
    private static final long IMAGE_ID = 1;
    private static final long USER_ID_1 = 1;
    private static final long USER_ID_2 = 2;
    private static final String USER_1_EMAIL = "jmentasti@itba.edu.ar";
    private static final String USER_2_EMAIL = "jrmenta2@gmail.com";
    private static final Locale USER_LOCALE = Locale.US;
    private static final String USER_ROLE_1 = "USER";
    private static final String USER_ROLE_2 = "DRIVER";
    private static final User USER_1 = new User(USER_ID_1,"","",USER_1_EMAIL,"","",CITY,USER_LOCALE,USER_ROLE_1,IMAGE_ID);
    private static final User USER_2 = new User(USER_ID_2,"","",USER_2_EMAIL,"","",CITY,USER_LOCALE,USER_ROLE_2,IMAGE_ID);
    private static final long CAR_ID_1 = 1;
    private static final long CAR_ID_2 = 2;
    private static final String CAR_PLATE = "AE062TP";
    private static final String CAR_INFO = "Honda Fit";
    private static final Car CAR_1 = new Car(CAR_ID_1,CAR_PLATE,CAR_INFO,USER_1,IMAGE_ID);
    private static final Car CAR_2 = new Car(CAR_ID_2,CAR_PLATE,CAR_INFO,USER_2,IMAGE_ID);

    private static final String ORIGIN_ADDRESS = "Av Callao 1348";
    private static final String DESTINATION_ADDRESS = "ITBA";
    private static final LocalDateTime START = LocalDateTime.now();
    private static final LocalDateTime END = START.plusDays(14);
    private static final double PRICE = 1200.0;
    private static final int MAX_SEATS = 3;

    private Trip createTrip(Trip trip){
        Map<String,Object> data = new HashMap<>();
        data.put("max_passengers",trip.getMaxSeats());
        data.put("origin_address",trip.getOriginAddress());
        data.put("destination_address",trip.getDestinationAddress());
        data.put("price",trip.getPrice());
        data.put("start_date_time",Timestamp.valueOf(trip.getStartDateTime()));
        data.put("end_date_time",Timestamp.valueOf(trip.getEndDateTime()));
        data.put("day_of_week",trip.getDayOfWeek().getValue());
        data.put("origin_city_id",trip.getOriginCity().getId());
        data.put("destination_city_id",trip.getDestinationCity().getId());
        Number key = tripInsert.executeAndReturnKey(data);
        data = new HashMap<>();
        data.put("trip_id",key.longValue());
        data.put("user_id",trip.getDriver().getUserId());
        data.put("car_id",trip.getCar().getCarId());
        tripsCarsDriversInsert.execute(data);
        return new Trip(key.longValue(),trip.getOriginCity(),trip.getOriginAddress(),
                trip.getDestinationCity(),trip.getDestinationAddress(),trip.getStartDateTime(),
                trip.getEndDateTime(),trip.getMaxSeats(),trip.getDriver(),trip.getCar(),trip.getOccupiedSeats(),trip.getPrice(),trip.getStartDateTime(),trip.getEndDateTime());
    }


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        tripInsert = new SimpleJdbcInsert(ds)
                .withTableName("trips")
                .usingGeneratedKeyColumns("trip_id");
        tripsCarsDriversInsert = new SimpleJdbcInsert(ds)
                .withTableName("trips_cars_drivers");
        jdbcTemplate.update("INSERT INTO provinces (province_id, name) VALUES (?, ?)",PROVINCE_ID,PROVINCE_NAME);
        jdbcTemplate.update("INSERT INTO cities (city_id,name, province_id) VALUES (?, ?,?)",CITY_ID,CITY_NAME,PROVINCE_ID);
        jdbcTemplate.update("INSERT INTO images VALUES (?,?)",IMAGE_ID,null);
        jdbcTemplate.update("INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?,?)",USER_ID_1,"","",USER_1_EMAIL,"","",CITY_ID,USER_LOCALE.toString(),USER_ROLE_1,IMAGE_ID);
        jdbcTemplate.update("INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?,?)",USER_ID_2,"","",USER_2_EMAIL,"","",CITY_ID,USER_LOCALE.toString(),USER_ROLE_2,IMAGE_ID);
        jdbcTemplate.update("INSERT INTO cars VALUES(?,?,?,?,?)",CAR_ID_1,CAR_PLATE,CAR_INFO,USER_ID_1,IMAGE_ID);
        jdbcTemplate.update("INSERT INTO cars VALUES(?,?,?,?,?)",CAR_ID_2,CAR_PLATE,CAR_INFO,USER_ID_2,IMAGE_ID);
    }

    @Rollback
    @Test
    public void testCreateTripSimple(){
        //Set Up
        int countTrips = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips");
        int countDrivers = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers");
        //Execute
        Trip aux = tripDao.create(CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,CAR_2,START,START,false,PRICE,MAX_SEATS,USER_2);

        //Assert
        Assert.assertEquals(CITY_ID,aux.getOriginCity().getId());
        Assert.assertEquals(START,aux.getStartDateTime());
        Assert.assertEquals(START,aux.getEndDateTime());
        Assert.assertEquals(CITY_NAME,aux.getOriginCity().getName());
        Assert.assertEquals(PROVINCE_ID,aux.getOriginCity().getProvinceId());
        Assert.assertEquals(ORIGIN_ADDRESS,aux.getOriginAddress());
        Assert.assertEquals(DESTINATION_ADDRESS,aux.getDestinationAddress());
        Assert.assertEquals(CITY_ID,aux.getDestinationCity().getId());
        Assert.assertEquals(CITY_NAME,aux.getDestinationCity().getName());
        Assert.assertEquals(PROVINCE_ID,aux.getDestinationCity().getProvinceId());
        Assert.assertEquals(CAR_ID_2,aux.getCar().getCarId());
        Assert.assertEquals(CAR_PLATE,aux.getCar().getPlate());
        Assert.assertEquals(USER_ID_2,aux.getDriver().getUserId());
        Assert.assertEquals(MAX_SEATS,aux.getMaxSeats());
        Assert.assertFalse(aux.isRecurrent());
        assertEquals(0, Double.compare(PRICE, aux.getPrice()));
        Assert.assertEquals(countTrips+1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips"));
        Assert.assertEquals(countDrivers+1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers"));
    }

    @Rollback
    @Test
    public void testCreateTripRecurrent(){
        //Set Up
        int countTrips = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips");
        int countDrivers = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers");
        //Execute
        Trip aux = tripDao.create(CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,CAR_2,START,END,false,PRICE,MAX_SEATS,USER_2);

        //Assert
        Assert.assertEquals(CITY_ID,aux.getOriginCity().getId());
        Assert.assertEquals(START,aux.getStartDateTime());
        Assert.assertEquals(END,aux.getEndDateTime());
        Assert.assertEquals(CITY_NAME,aux.getOriginCity().getName());
        Assert.assertEquals(PROVINCE_ID,aux.getOriginCity().getProvinceId());
        Assert.assertEquals(ORIGIN_ADDRESS,aux.getOriginAddress());
        Assert.assertEquals(DESTINATION_ADDRESS,aux.getDestinationAddress());
        Assert.assertEquals(CITY_ID,aux.getDestinationCity().getId());
        Assert.assertEquals(CITY_NAME,aux.getDestinationCity().getName());
        Assert.assertEquals(PROVINCE_ID,aux.getDestinationCity().getProvinceId());
        Assert.assertEquals(CAR_ID_2,aux.getCar().getCarId());
        Assert.assertEquals(CAR_PLATE,aux.getCar().getPlate());
        Assert.assertEquals(USER_ID_2,aux.getDriver().getUserId());
        Assert.assertEquals(MAX_SEATS,aux.getMaxSeats());
        Assert.assertTrue(aux.isRecurrent());
        assertEquals(0, Double.compare(PRICE, aux.getPrice()));
        Assert.assertEquals(countTrips+1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips"));
        Assert.assertEquals(countDrivers+1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers"));
    }

    @Rollback
    @Test
    public void testAddPassenger(){
        //SetUp
        Trip trip = createTrip(new Trip(0,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,START,MAX_SEATS,USER_2,CAR_2,0,PRICE,START,START));
        int passengerCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers");

        //Execute and Assert
        Assert.assertTrue(tripDao.addPassenger(trip,new Passenger(USER_1,START,START)));
        Assert.assertEquals(passengerCount+1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers"));
    }

    @Rollback
    @Test
    public void testDeleteTripWithoutPassengers(){
        //SetUp
        Trip trip = createTrip(new Trip(0,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,START,MAX_SEATS,USER_2,CAR_2,0,PRICE,START,START));
        int passengerCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers");
        int tripCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips");
        int countDrivers = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers");

        //Execute and Assert
        Assert.assertTrue(tripDao.deleteTrip(trip));
        Assert.assertEquals(passengerCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers"));
        Assert.assertEquals(tripCount-1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips"));
        Assert.assertEquals(countDrivers-1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers"));
    }
    @Rollback
    @Test
    public void testDeleteTripWithPassengers(){
        //SetUp
        Trip trip = createTrip(new Trip(0,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,END,MAX_SEATS,USER_2,CAR_2,0,PRICE,START,END));
        jdbcTemplate.update("INSERT INTO passengers VALUES(?,?,?,?)",trip.getTripId(),USER_1.getUserId(),Timestamp.valueOf(START),Timestamp.valueOf(START));
        jdbcTemplate.update("INSERT INTO passengers VALUES(?,?,?,?)",trip.getTripId(),USER_2.getUserId(),Timestamp.valueOf(START),Timestamp.valueOf(START));
        int passengerCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers");
        int tripCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips");
        int countDrivers = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers");

        //Execute and Assert
        Assert.assertTrue(tripDao.deleteTrip(trip));
        Assert.assertEquals(passengerCount-2,JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers"));
        Assert.assertEquals(tripCount-1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips"));
        Assert.assertEquals(countDrivers-1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers"));
    }

    @Rollback
    @Test
    public void testRemovePassenger(){
        //SetUp
        Trip trip = createTrip(new Trip(0,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,END,MAX_SEATS,USER_2,CAR_2,0,PRICE,START,END));
        jdbcTemplate.update("INSERT INTO passengers VALUES(?,?,?,?)",trip.getTripId(),USER_1.getUserId(),Timestamp.valueOf(START),Timestamp.valueOf(START));
        int passengerCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers");
        int tripCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips");
        int countDrivers = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers");


        //Execute and Assert
        Assert.assertTrue(tripDao.removePassenger(trip,new Passenger(USER_1,START,START)));
        Assert.assertEquals(passengerCount-1,JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers"));
        Assert.assertEquals(tripCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips"));
        Assert.assertEquals(countDrivers,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers"));
    }

    @Test
    @Rollback
    public void testGetPassengersEmpty(){
        //SetUp
        Trip trip = createTrip(new Trip(0,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,END,MAX_SEATS,USER_2,CAR_2,0,PRICE,START,END));
        int passengerCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers");
        int tripCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips");
        int countDrivers = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers");

        //Execute
        List<Passenger> passengers = tripDao.getPassengers(trip,START,END);

        //Assert
        Assert.assertTrue(passengers.isEmpty());
        Assert.assertEquals(passengerCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers"));
        Assert.assertEquals(tripCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips"));
        Assert.assertEquals(countDrivers,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers"));
    }

    @Test
    @Rollback
    public void testGetPassengersInAllTrip(){
        //SetUp
        Trip trip = createTrip(new Trip(0,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,END,MAX_SEATS,USER_2,CAR_2,0,PRICE,START,END));
        jdbcTemplate.update("INSERT INTO passengers VALUES(?,?,?,?)",trip.getTripId(),USER_1.getUserId(),Timestamp.valueOf(START),Timestamp.valueOf(START));
        jdbcTemplate.update("INSERT INTO passengers VALUES(?,?,?,?)",trip.getTripId(),USER_2.getUserId(),Timestamp.valueOf(START),Timestamp.valueOf(END));
        int passengerCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers");
        int tripCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips");
        int countDrivers = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers");

        //Execute
        List<Passenger> passengers = tripDao.getPassengers(trip,START,END);

        //Assert
        Assert.assertEquals(2,passengers.size());
        Assert.assertTrue(passengers.stream().anyMatch(p -> p.getUserId() == USER_ID_1 && p.getStartDateTime().equals(START) && p.getEndDateTime().equals(START)));
        Assert.assertTrue(passengers.stream().anyMatch(p -> p.getUserId() == USER_ID_2 && p.getStartDateTime().equals(START) && p.getEndDateTime().equals(END)));
        Assert.assertEquals(passengerCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers"));
        Assert.assertEquals(tripCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips"));
        Assert.assertEquals(countDrivers,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers"));
    }

    @Test
    @Rollback
    public void testGetPassengerInIntervals(){
        //SetUp
        Trip trip = createTrip(new Trip(0,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,END,MAX_SEATS,USER_2,CAR_2,0,PRICE,START,END));
        jdbcTemplate.update("INSERT INTO passengers VALUES(?,?,?,?)",trip.getTripId(),USER_1.getUserId(),Timestamp.valueOf(START),Timestamp.valueOf(START));
        jdbcTemplate.update("INSERT INTO passengers VALUES(?,?,?,?)",trip.getTripId(),USER_2.getUserId(),Timestamp.valueOf(START.plusDays(1)),Timestamp.valueOf(END));
        int passengerCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers");
        int tripCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips");
        int countDrivers = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers");

        //Execute
        List<Passenger> passengers1 = tripDao.getPassengers(trip,START,START);
        List<Passenger> passengers2 = tripDao.getPassengers(trip,START.plusDays(1),END);
        //Assert
        Assert.assertEquals(1,passengers1.size());
        Assert.assertEquals(1,passengers2.size());
        Assert.assertTrue(passengers1.stream().anyMatch(p -> p.getUserId() == USER_ID_1 && p.getStartDateTime().equals(START) && p.getEndDateTime().equals(START)));
        Assert.assertTrue(passengers2.stream().anyMatch(p -> p.getUserId() == USER_ID_2 && p.getStartDateTime().equals(START.plusDays(1)) && p.getEndDateTime().equals(END)));
        Assert.assertEquals(passengerCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers"));
        Assert.assertEquals(tripCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips"));
        Assert.assertEquals(countDrivers,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers"));
    }

    @Test
    @Rollback
    public void testGetPassengerEmpty(){
        //SetUp
        Trip trip = createTrip(new Trip(0,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,END,MAX_SEATS,USER_2,CAR_2,0,PRICE,START,END));
        int passengerCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers");
        int tripCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips");
        int countDrivers = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers");

        //Execute
        Optional<Passenger> passenger = tripDao.getPassenger(trip,USER_1);

        //Assert
        Assert.assertFalse(passenger.isPresent());
        Assert.assertEquals(passengerCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers"));
        Assert.assertEquals(tripCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips"));
        Assert.assertEquals(countDrivers,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers"));
    }

    @Test
    @Rollback
    public void testGetPassengerPresent(){
        Trip trip = createTrip(new Trip(0,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,END,MAX_SEATS,USER_2,CAR_2,0,PRICE,START,END));
        jdbcTemplate.update("INSERT INTO passengers VALUES(?,?,?,?)",trip.getTripId(),USER_1.getUserId(),Timestamp.valueOf(START),Timestamp.valueOf(START));
        int passengerCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers");
        int tripCount = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips");
        int countDrivers = JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers");

        //Execute
        Optional<Passenger> passenger = tripDao.getPassenger(trip,USER_1);

        //Assert
        Assert.assertTrue(passenger.isPresent());
        Assert.assertEquals(USER_ID_1,passenger.get().getUserId());
        Assert.assertEquals(START,passenger.get().getStartDateTime());
        Assert.assertEquals(START,passenger.get().getEndDateTime());
        Assert.assertEquals(passengerCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"passengers"));
        Assert.assertEquals(tripCount,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips"));
        Assert.assertEquals(countDrivers,JdbcTestUtils.countRowsInTable(jdbcTemplate,"trips_cars_drivers"));
    }
}

