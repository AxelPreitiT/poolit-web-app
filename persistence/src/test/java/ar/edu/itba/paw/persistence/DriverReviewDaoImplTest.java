package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.reviews.CarReviewOptions;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.DriverReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.Locale;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DriverReviewDaoImplTest {

    @Autowired
    private DriverReviewHibernateDao driverReviewDao;

    private static final long PROVINCE_ID = 1;
    private static final long CITY_ID = 1;
    private static final City CITY = new City(CITY_ID,"Recoleta",PROVINCE_ID);
    private static final long KNOWN_IMAGE_ID = 1;
    private static final long USER_ID_1 = 1;
    private static final long USER_ID_2 = 2;
    private static final String USER_1_EMAIL = "jonhdoe@mail.com";
    private static final String USER_2_EMAIL = "jonhdoe2@mail.com";
    private static final Locale USER_LOCALE = Locale.ENGLISH;
    private static final String USER_ROLE_1 = "USER";
    private static final String USER_ROLE_2 = "DRIVER";
    private static final String USER_NAME = "John";
    private static final String USER_SURNAME = "Doe";
    private static final String USER_PHONE = "1234567800";
    private static final String USER_PASSWORD = "1234";
    private static final User USER_1 = new User(USER_ID_1,USER_NAME,USER_SURNAME,USER_1_EMAIL,USER_PHONE,USER_PASSWORD,CITY,USER_LOCALE,USER_ROLE_1, KNOWN_IMAGE_ID);
    private static final User USER_2 = new User(USER_ID_2,USER_NAME,USER_SURNAME,USER_2_EMAIL,USER_PHONE,USER_PASSWORD,CITY,USER_LOCALE,USER_ROLE_2, KNOWN_IMAGE_ID);
    private static final long CAR_ID_1 = 1;
    private static final Car CAR_1 = new Car(CAR_ID_1,"AA000AA","Fit Azul",USER_1, KNOWN_IMAGE_ID);
    private static final LocalDateTime START = LocalDateTime.of(2023,7,3,23,30,0);
    private static final LocalDateTime END = LocalDateTime.of(2023,7,17,23,30,0);
    private static final long KNOWN_RECURRENT_TRIP_ID = 2;
    private static final Trip TRIP_2 = new Trip(KNOWN_RECURRENT_TRIP_ID,CITY,"Av Callao 1348",CITY,"ITBA",START,END,3,USER_1,CAR_1,0,1200.0);
    private static final Passenger PASSENGER_1 = new Passenger(USER_1,TRIP_2,START,START);
    private static final Passenger PASSENGER_2 = new Passenger(USER_2,TRIP_2,START,END);

    private static final String COMMENT = "The driver was very kind";
    private static final int RATING = 4;
    private static final DriverReviewOptions DRIVER_REVIEW_OPTION = DriverReviewOptions.VERY_FRIENDLY;
    private static final int PAGE_SIZE = 10;

    @Test
    @Rollback
    public void testCreateDriverReview(){

        //Execute
        DriverReview ans = driverReviewDao.createDriverReview(TRIP_2,PASSENGER_1,USER_1,RATING,COMMENT,DRIVER_REVIEW_OPTION);
        //Assert
        Assert.assertEquals(TRIP_2.getTripId(),ans.getTrip().getTripId());
        Assert.assertEquals(PASSENGER_1.getUser().getUserId(),ans.getReviewer().getUserId());
        Assert.assertEquals(USER_1.getUserId(),ans.getReviewed().getUserId());
        Assert.assertEquals(COMMENT,ans.getComment());
        Assert.assertEquals(RATING,ans.getRating());
        Assert.assertEquals(DRIVER_REVIEW_OPTION,ans.getOption());
    }

    @Test
    public void testGetDriverRating(){
        //Execute
        double ans = driverReviewDao.getDriverRating(USER_1);
        //Assert
        Assert.assertEquals(0,Double.compare(RATING,ans));
    }

    @Test
    public void testGetDriverReviews(){
        //Execute
        PagedContent<DriverReview> ans = driverReviewDao.getDriverReviews(USER_1,0,PAGE_SIZE);
        //Assert
        Assert.assertEquals(1,ans.getTotalCount());
        Assert.assertEquals(USER_2.getUserId(),ans.getElements().get(0).getReviewer().getUserId());
        Assert.assertEquals(RATING,ans.getElements().get(0).getRating());
        Assert.assertEquals(COMMENT,ans.getElements().get(0).getComment());
        Assert.assertEquals(DRIVER_REVIEW_OPTION,ans.getElements().get(0).getOption());
    }

    @Test
    public void testCanReviewDriverFalse(){
        //Execute
        boolean ans = driverReviewDao.canReviewDriver(TRIP_2,PASSENGER_2,USER_1);
        //Assert
        Assert.assertFalse(ans);
    }

    @Test
    public void testCanReviewDriverTrue(){
        //Execute
        boolean ans = driverReviewDao.canReviewDriver(TRIP_2,PASSENGER_1,USER_1);
        //Assert
        Assert.assertTrue(ans);
    }



}
