package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.interfaces.persistence.PassengerReviewDao;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.services.PassengerReviewServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PassengerReviewServiceImplTest {
    private static final City ORIGIN_CITY = new City(1, "Agronomía", 1);
    private static final long TRIP_ID = 1L;
    private static final City DESTINATION_CITY = new City(1, "Agronomía", 1);
    private static final LocalDateTime DATE_TIME = LocalDateTime.now().minusYears(1);
    private static final User DRIVER = new User(2, "DRIVER", "SURNAME", "driver@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "DRIVER", 1L);
    private static final User PASSENGER_USER= new User(3,"PASSENGER","SURNAME","passenger@gmail.com","PHONE","PASSWORD",new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final User REVIEWED_USER = new User(1, "USER", "SURNAME", "user@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final Car CAR = new Car(1, "ABC123", "INFO", DRIVER, 1L);
    private static final Trip TRIP = new Trip(TRIP_ID, ORIGIN_CITY, "ORIGIN ADDRESS", DESTINATION_CITY, "DESTINATION ADDRESS", DATE_TIME, DATE_TIME.plusDays(7), 4, DRIVER, CAR, 0, BigDecimal.valueOf(10.0).doubleValue());
    private static final Passenger PASSENGER = new Passenger(REVIEWED_USER, TRIP, TRIP.getStartDateTime(), TRIP.getEndDateTime());
    private static final Passenger REVIEWER_PASSENGER = new Passenger(PASSENGER_USER, TRIP, TRIP.getStartDateTime(), TRIP.getEndDateTime());
    private static final int RATING = 4;
    private static final String COMMENT = "Really good car";
    private static final PassengerReviewOptions OPTION = PassengerReviewOptions.VERY_FRIENDLY;
    private static final PassengerReview REVIEW_PASSENGER = new PassengerReview(TRIP,PASSENGER_USER,REVIEWED_USER,RATING,COMMENT,OPTION);
    private static final PassengerReview REVIEW_DRIVER = new PassengerReview(TRIP,DRIVER,REVIEWED_USER,RATING,COMMENT,OPTION);

    @Mock
    private PassengerReviewDao passengerReviewDao;

    @Mock
    private TripService tripService;

    @Mock
    private UserService userService;

    @InjectMocks
    private PassengerReviewServiceImpl passengerReviewService;

    @Before
    public void setup(){
        PASSENGER.setPassengerState(Passenger.PassengerState.ACCEPTED);
        REVIEWER_PASSENGER.setPassengerState(Passenger.PassengerState.ACCEPTED);
    }

    @Test
    public void testCreateAsDriver() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(userService.getCurrentUser()).thenReturn(Optional.of(DRIVER));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.findById(anyLong())).thenReturn(Optional.of(REVIEWED_USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(true);
        when(passengerReviewDao.canReviewPassenger(any(),any(),any())).thenReturn(true);
        when(passengerReviewDao.createPassengerReview(any(),any(),any(),anyInt(),anyString(),any())).thenReturn(REVIEW_DRIVER);

        PassengerReview passengerReview = passengerReviewService.createPassengerReview(TRIP_ID,PASSENGER.getUserId(),RATING,COMMENT,OPTION);

        Assert.assertNotNull(passengerReview);
        Assert.assertEquals(passengerReview.getTrip().getTripId(),TRIP_ID);
        Assert.assertEquals(passengerReview.getReviewer().getUserId(),DRIVER.getUserId());
        Assert.assertEquals(passengerReview.getReviewed().getUserId(),PASSENGER.getUserId());
        Assert.assertEquals(passengerReview.getComment(),COMMENT);
        Assert.assertEquals(passengerReview.getRating(),RATING);
        Assert.assertEquals(passengerReview.getOption(),OPTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNotBeingDriverOrPassenger() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(userService.getCurrentUser()).thenReturn(Optional.of(DRIVER));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.findById(anyLong())).thenReturn(Optional.of(REVIEWED_USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(false);
        when(tripService.userIsPassenger(anyLong(),any())).thenReturn(false);

        passengerReviewService.createPassengerReview(TRIP_ID,PASSENGER.getUserId(),RATING,COMMENT,OPTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAsDriverAlreadyMade() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(userService.getCurrentUser()).thenReturn(Optional.of(DRIVER));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.findById(anyLong())).thenReturn(Optional.of(REVIEWED_USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(true);
        when(passengerReviewDao.canReviewPassenger(any(),any(),any())).thenReturn(false);

        passengerReviewService.createPassengerReview(TRIP_ID,PASSENGER.getUserId(),RATING,COMMENT,OPTION);
    }


    @Test
    public void testCreateAsPassenger() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(userService.getCurrentUser()).thenReturn(Optional.of(DRIVER));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.findById(anyLong())).thenReturn(Optional.of(REVIEWED_USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(false);
        when(tripService.userIsPassenger(anyLong(),any())).thenReturn(true);
        when(tripService.getPassenger(anyLong(),any())).thenReturn(Optional.of(REVIEWER_PASSENGER));
        when(passengerReviewDao.canReviewPassenger(any(),any(),any())).thenReturn(true);
        when(passengerReviewDao.createPassengerReview(any(),any(),any(),anyInt(),anyString(),any())).thenReturn(REVIEW_PASSENGER);

        PassengerReview passengerReview = passengerReviewService.createPassengerReview(TRIP_ID,PASSENGER.getUserId(),RATING,COMMENT,OPTION);

        Assert.assertNotNull(passengerReview);
        Assert.assertEquals(passengerReview.getTrip().getTripId(),TRIP_ID);
        Assert.assertEquals(passengerReview.getReviewer().getUserId(),PASSENGER_USER.getUserId());
        Assert.assertEquals(passengerReview.getReviewed().getUserId(),PASSENGER.getUserId());
        Assert.assertEquals(passengerReview.getComment(),COMMENT);
        Assert.assertEquals(passengerReview.getRating(),RATING);
        Assert.assertEquals(passengerReview.getOption(),OPTION);
    }

    @Test(expected = PassengerNotFoundException.class)
    public void testCreateAsPassengerNotBeingPassenger() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(userService.getCurrentUser()).thenReturn(Optional.of(DRIVER));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.findById(anyLong())).thenReturn(Optional.of(REVIEWED_USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(false);
        when(tripService.userIsPassenger(anyLong(),any())).thenReturn(true);
        when(tripService.getPassenger(anyLong(),any())).thenReturn(Optional.empty());

        passengerReviewService.createPassengerReview(TRIP_ID,PASSENGER.getUserId(),RATING,COMMENT,OPTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateAsPassengerForSelf() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(userService.getCurrentUser()).thenReturn(Optional.of(DRIVER));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.findById(anyLong())).thenReturn(Optional.of(REVIEWED_USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(false);
        when(tripService.userIsPassenger(anyLong(),any())).thenReturn(true);
        when(tripService.getPassenger(anyLong(),any())).thenReturn(Optional.of(PASSENGER));

        passengerReviewService.createPassengerReview(TRIP_ID,PASSENGER.getUserId(),RATING,COMMENT,OPTION);
    }


}
