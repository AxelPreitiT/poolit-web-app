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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ReviewDaoImplTest {

    @Autowired
    private ReviewDaoImpl reviewDao;

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

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

    private static final long TRIP_ID = 1;
    private static final String ORIGIN_ADDRESS = "Av Callao 1348";
    private static final String DESTINATION_ADDRESS = "ITBA";
    private static final LocalDateTime START = LocalDateTime.now();
    private static final LocalDateTime END = START.plusDays(14);
    private static final double PRICE = 1200.0;
    private static final int MAX_SEATS = 3;
    private static final Trip TRIP = new Trip(TRIP_ID,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,END,MAX_SEATS,USER_1,CAR_1,0,PRICE,START,END);

    private static final String REVIEW = "The trip was perfect";
    private static final int RATING = 3;

    @Before
    public void setUp(){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("INSERT INTO provinces (province_id, name) VALUES (?, ?)",PROVINCE_ID,PROVINCE_NAME);
        jdbcTemplate.update("INSERT INTO cities (city_id,name, province_id) VALUES (?, ?,?)",CITY_ID,CITY_NAME,PROVINCE_ID);
        jdbcTemplate.update("INSERT INTO images VALUES (?,?)",IMAGE_ID,null);
        jdbcTemplate.update("INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?,?)",USER_ID_1,"","",USER_1_EMAIL,"","",CITY_ID,USER_LOCALE.toString(),USER_ROLE_1,IMAGE_ID);
        jdbcTemplate.update("INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?,?)",USER_ID_2,"","",USER_2_EMAIL,"","",CITY_ID,USER_LOCALE.toString(),USER_ROLE_2,IMAGE_ID);
        jdbcTemplate.update("INSERT INTO cars VALUES(?,?,?,?,?)",CAR_ID_1,CAR_PLATE,CAR_INFO,USER_ID_1,IMAGE_ID);
        jdbcTemplate.update("INSERT INTO trips VALUES (?,?,?,?,?,?,?,?,?,?)",TRIP_ID,MAX_SEATS,ORIGIN_ADDRESS,DESTINATION_ADDRESS,PRICE, Timestamp.valueOf(START),Timestamp.valueOf(END),START.getDayOfWeek().getValue(),CITY_ID,CITY_ID);
        jdbcTemplate.update("INSERT INTO trips_cars_drivers VALUES (?,?,?)",TRIP_ID,USER_ID_1,CAR_ID_1);
    }

    @Test
    @Rollback
    public void testCreate(){
        //No setUp

        //Execute
        final Review review = reviewDao.create(TRIP_ID,USER_1,RATING,REVIEW);

        //Assert
        Assert.assertEquals(REVIEW,review.getReview());
        Assert.assertEquals(RATING,review.getRating());
        Assert.assertEquals(TRIP_ID,review.getTripId());
        Assert.assertEquals(USER_ID_1,review.getDriver());
    }

    @Test
    @Rollback
    public void testFindByDriver(){
        //SetUp
        jdbcTemplate.update("INSERT INTO reviews VALUES (?,?,?,?,?)",1,TRIP_ID,USER_ID_1,RATING-1,REVIEW);
        jdbcTemplate.update("INSERT INTO reviews VALUES (?,?,?,?,?)",2,TRIP_ID,USER_ID_2,RATING+1,REVIEW);

        //Execute
        final List<Review> reviews = reviewDao.findByDriver(USER_1);

        //Assert
        Assert.assertEquals(2,reviews.size());
        Assert.assertTrue(reviews.stream().allMatch(r -> r.getReview().equals(REVIEW)));
        Assert.assertTrue(reviews.stream().anyMatch(r -> r.getRating() == RATING-1));
        Assert.assertTrue(reviews.stream().anyMatch(r -> r.getRating() == RATING+1));
        Assert.assertTrue(reviews.stream().anyMatch(r -> r.getUser().getUserId() == USER_ID_1));
        Assert.assertTrue(reviews.stream().anyMatch(r -> r.getUser().getUserId() == USER_ID_2));
    }

    @Rollback
    @Test
    public void testFindByUser(){
        //SetUp
        jdbcTemplate.update("INSERT INTO reviews VALUES (?,?,?,?,?)",1,TRIP_ID,USER_ID_1,RATING,REVIEW);

        //Execute
        final List<Review> reviews = reviewDao.findReviewsByUser(USER_1);

        //Assert
        Assert.assertEquals(1,reviews.size());
        Assert.assertEquals(REVIEW,reviews.get(0).getReview());
        Assert.assertEquals(RATING,reviews.get(0).getRating());
        Assert.assertEquals(USER_ID_1,reviews.get(0).getUser().getUserId());
    }

    @Rollback
    @Test
    public void testFindTripIdByUser(){
        //SetUp
        jdbcTemplate.update("INSERT INTO reviews VALUES (?,?,?,?,?)",1,TRIP_ID,USER_ID_1,RATING,REVIEW);

        //Execute
        final List<Long> tripsId = reviewDao.findTripIdByUser(USER_1);

        //Assert
        Assert.assertEquals(1,tripsId.size());
        Assert.assertEquals(TRIP_ID,tripsId.get(0).longValue());
    }

    @Rollback
    @Test
    public void testFindByTripAndPassengerEmpty(){
        //No setUp

        //Execute
        Optional<Review> review = reviewDao.reviewByTripAndPassanger(TRIP,new Passenger(USER_1,START,END));

        //Assert
        Assert.assertFalse(review.isPresent());
    }

    @Rollback
    @Test
    public void testFindByTripAndPassengerPresent(){
        //SetUp
        jdbcTemplate.update("INSERT INTO reviews VALUES (?,?,?,?,?)",1,TRIP_ID,USER_ID_1,RATING,REVIEW);
        //Execute
        Optional<Review> review = reviewDao.reviewByTripAndPassanger(TRIP,new Passenger(USER_1,START,END));

        //Assert
        Assert.assertTrue(review.isPresent());
        Assert.assertEquals(REVIEW,review.get().getReview());
        Assert.assertEquals(RATING,review.get().getRating());
        Assert.assertEquals(USER_ID_1,review.get().getUser().getUserId());
    }
}
