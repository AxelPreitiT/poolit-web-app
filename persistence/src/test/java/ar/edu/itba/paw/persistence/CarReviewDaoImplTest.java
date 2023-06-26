package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.models.reviews.CarReviewOptions;
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
public class CarReviewDaoImplTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CarReviewHibernateDao carReviewDao;

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

    private static final String COMMENT = "The space was very good";
    private static final int RATING = 4;
    private static final CarReviewOptions CAR_REVIEW_OPTION = CarReviewOptions.BIG_TRUNK_SPACE;

    private static final int PAGE_SIZE = 10;

    @Test
    @Rollback
    public void testCreateCarReview(){
        //Setup
        Trip auxTrip = em.merge(TRIP_2);
        Passenger auxPassenger = em.merge(PASSENGER_1);
        //Execute
        CarReview ans = carReviewDao.createCarReview(auxTrip,auxPassenger,CAR_1,RATING,COMMENT,CAR_REVIEW_OPTION);
        //Assert
        Assert.assertEquals(CAR_1.getCarId(),ans.getCar().getCarId());
        Assert.assertEquals(RATING,ans.getRating());
        Assert.assertEquals(COMMENT,ans.getComment());
        Assert.assertEquals(CAR_REVIEW_OPTION,ans.getOption());
    }

    @Test
    public void testGetCarRating(){
        //Execute
        double ans = carReviewDao.getCarRating(CAR_1);
        //Assert
        Assert.assertEquals(0, Double.compare(RATING, ans));
    }

    @Test
    public void testGetCarReviews(){
        //Execute
        PagedContent<CarReview> ans = carReviewDao.getCarReviews(CAR_1,0,PAGE_SIZE);
        //Assert
        Assert.assertEquals(1,ans.getTotalCount());
        Assert.assertEquals(RATING,ans.getElements().get(0).getRating());
        Assert.assertEquals(COMMENT,ans.getElements().get(0).getComment());
        Assert.assertEquals(CAR_REVIEW_OPTION,ans.getElements().get(0).getOption());
    }

    @Test
    public void testCanReviewCarTrue(){
        //Execute
        boolean ans = carReviewDao.canReviewCar(TRIP_2,PASSENGER_1,CAR_1);
        //Assert
        Assert.assertTrue(ans);
    }

    @Test
    public void testCanReviewCarFalse(){
        //Execute
        boolean ans = carReviewDao.canReviewCar(TRIP_2,PASSENGER_2,CAR_1);
        //Assert
        Assert.assertFalse(ans);
    }



}
