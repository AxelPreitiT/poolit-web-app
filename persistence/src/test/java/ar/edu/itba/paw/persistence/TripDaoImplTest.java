package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

    private static final String email = "jmentasti@itba.edu.ar";
    private static final String phone = "1139150686";

    private static final String email2 = "jrmenta2@gmail.com";
    private static final String phone2 = "1139150686";

    private static final String cityName = "Recoleta";

    private User user1 = null;
    private User user2 = null;
    private City city = null;


    private Trip createTrip(Trip trip){
        Map<String,Object> data = new HashMap<>();
        data.put("max_passengers",trip.getMaxSeats());
        data.put("origin_address",trip.getOriginAddress());
        data.put("destination_address",trip.getDestinationAddress());
        data.put("price",trip.getPrice());
        data.put("start_date_time",trip.getStartDateTime());
        data.put("end_date_time",trip.getEndDateTime());
        data.put("day_of_week",trip.getDayOfWeek().getValue());
        data.put("origin_city_id",trip.getOriginCity().getId());
        data.put("destination_city_id",trip.getDestinationCity().getId());
        Number key = tripInsert.executeAndReturnKey(data);
        return new Trip(key.longValue(),trip.getOriginCity(),trip.getOriginAddress(),
                trip.getDestinationCity(),trip.getDestinationAddress(),trip.getStartDateTime(),
                trip.getEndDateTime(),trip.getMaxSeats(),trip.getDriver(),trip.getCar(),trip.getOccupiedSeats(),trip.getPrice(),trip.getStartDateTime(),trip.getEndDateTime());
    }

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "trips","trips_cars_drivers","passengers");
        tripInsert = new SimpleJdbcInsert(ds)
                .withTableName("trips")
                .usingGeneratedKeyColumns("trip_id");
        passengerInsert = new SimpleJdbcInsert(ds)
                .withTableName("passengers");
        tripsCarsDriversInsert = new SimpleJdbcInsert(ds)
                .withTableName("trips_cars_drivers");
        /*
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        Map<String,Object> user1Info = new HashMap<>();
        user1Info.put("email",email);
        user1Info.put("phone",phone);
        Number key = jdbcInsert.executeAndReturnKey(user1Info);
        user1 = new User(key.longValue(),email,phone);
        Map<String,Object> user2Info = new HashMap<>();
        user2Info.put("email",email2);
        user2Info.put("phone",phone2);
        Number key2 = jdbcInsert.executeAndReturnKey(user2Info);
        user2 = new User(key2.longValue(),email,phone);
        city = jdbcTemplate.query("SELECT * FROM cities WHERE name LIKE ?",(rs,rc)->new City(rs.getLong("city_id"),rs.getString("name"),rs.getLong("province_id")),cityName).stream().findFirst().orElseThrow(IllegalStateException::new);
         */
    }

    @Test
    public void testGetTripInstancesWithoutPassengers(){
        Trip aux = new Trip(1, city, "Av Callao", city, "Av Callao",
                LocalDateTime.of(2023,4,25,0,0),
                LocalDateTime.of(2023,4,25,0,0).plusDays(13),
                4, user1, new Car(1,"AE062TP","Honda Fit",user1,1), 0, 10.0
        );
        PagedContent<TripInstance> ans = tripDao.getTripInstances(aux,0,10);
        ans.getElements().forEach(instance ->{
                assertEquals(instance.getPassengerCount(),0);
            }
        );
        assertEquals(2,ans.getTotalCount());
        aux = new Trip(1, city, "Av Callao", city, "Av Callao",
                LocalDateTime.of(2023,4,25,0,0),
                LocalDateTime.of(2023,4,25,0,0).plusDays(14),
                4, user1, new Car(1,"AE062TP","Honda Fit",user1,1), 0, 10.0
        );
        ans = tripDao.getTripInstances(aux,0,10);
        ans.getElements().forEach(instance ->{
                    assertEquals(instance.getPassengerCount(),0);
                }
        );
        assertEquals(3,ans.getTotalCount());
        aux = new Trip(1, city, "Av Callao", city, "Av Callao",
                LocalDateTime.of(2023,4,25,0,0),
                LocalDateTime.of(2023,4,25,0,0).plusDays(7),
                4, user1, new Car(1,"AE062TP","Honda Fit",user1,1), 0, 10.0
        );
        ans = tripDao.getTripInstances(aux,0,10);
        ans.getElements().forEach(instance ->{
                    assertEquals(instance.getPassengerCount(),0);
                }
        );
        assertEquals(2,ans.getTotalCount());
        aux = new Trip(1, city, "Av Callao", city, "Av Callao",
                LocalDateTime.of(2023,4,25,0,0),
                LocalDateTime.of(2023,4,25,0,0).plusDays(6),
                4, user1, new Car(1,"AE062TP","Honda Fit",user1,1), 0, 10.0
        );
        ans = tripDao.getTripInstances(aux,0,10);
        ans.getElements().forEach(instance ->{
                    assertEquals(instance.getPassengerCount(),0);
                }
        );
        assertEquals(1,ans.getTotalCount());
        aux = new Trip(1, city, "Av Callao", city, "Av Callao",
                LocalDateTime.of(2023,4,25,0,0),
                LocalDateTime.of(2023,4,25,0,0),
                4, user1, new Car(1,"AE062TP","Honda Fit",user1,1), 0, 10.0
        );
        ans = tripDao.getTripInstances(aux,0,10);
        ans.getElements().forEach(instance ->{
                    assertEquals(instance.getPassengerCount(),0);
                }
        );
        assertEquals(1,ans.getTotalCount());
    }
    @Test
    public void testGetTripInstancesInRangeWithoutPassengers(){
        LocalDateTime dateTime =LocalDateTime.of(2023,4,25,0,0);
        Trip aux = new Trip(1, city, "Av Callao", city, "Av Callao",
                dateTime,
                dateTime.plusDays(14),
                4, user1, new Car(1,"AE062TP","Honda Fit",user1,1), 0, 10.0
        );
        PagedContent<TripInstance> ans = tripDao.getTripInstances(aux,0,10,dateTime,dateTime.plusDays(13));
        ans.getElements().forEach(instance ->{
                    assertEquals(instance.getPassengerCount(),0);
                }
        );
        assertEquals(2,ans.getTotalCount());
        ans = tripDao.getTripInstances(aux,0,10,dateTime,dateTime.plusDays(14));
        ans.getElements().forEach(instance ->{
                    assertEquals(instance.getPassengerCount(),0);
                }
        );
        assertEquals(3,ans.getTotalCount());
        ans = tripDao.getTripInstances(aux,0,10,dateTime,dateTime.plusDays(7));
        ans.getElements().forEach(instance ->{
                    assertEquals(instance.getPassengerCount(),0);
                }
        );
        assertEquals(2,ans.getTotalCount());
        ans = tripDao.getTripInstances(aux,0,10,dateTime,dateTime.plusDays(6));
        ans.getElements().forEach(instance ->{
                    assertEquals(instance.getPassengerCount(),0);
                }
        );
        assertEquals(1,ans.getTotalCount());
        ans = tripDao.getTripInstances(aux,0,10,dateTime,dateTime);
        ans.getElements().forEach(instance ->{
                    assertEquals(instance.getPassengerCount(),0);
                }
        );
        assertEquals(1,ans.getTotalCount());
        ans = tripDao.getTripInstances(aux,0,10,dateTime,dateTime.minusDays(1));
        ans.getElements().forEach(instance ->{
                    assertEquals(instance.getPassengerCount(),0);
                }
        );
        assertEquals(0,ans.getTotalCount());
    }

    @Test
    public void testGetPassengersWithoutPassengers(){
        LocalDateTime dateTime =LocalDateTime.of(2023,4,25,0,0);
        Trip aux = new Trip(0, city, "Av Callao", city, "Av Callao",
                dateTime,
                dateTime.plusDays(14),
                4, user1, new Car(1,"AE062TP","Honda Fit",user1,1), 0, 10.0
        );
        aux = createTrip(aux);
        List<Passenger> ans = tripDao.getPassengers(aux,dateTime);
        assertEquals(0,ans.size());
        ans = tripDao.getPassengers(aux,dateTime.minusDays(1));
        assertEquals(0,ans.size());
        ans = tripDao.getPassengers(aux,dateTime.plusDays(14));
        assertEquals(0,ans.size());
        ans = tripDao.getPassengers(aux,dateTime.plusDays(15));
        assertEquals(0,ans.size());
    }
    @Test
    public void testGetPassengers(){
        LocalDateTime dateTime =LocalDateTime.of(2023,4,25,0,0);
        Trip aux = new Trip(1, city, "Av Callao", city, "Av Callao",
                dateTime,
                dateTime.plusDays(14),
                4, user1, new Car(1,"AE062TP","Honda Fit",user1,1), 0, 10.0
        );
    }
}
