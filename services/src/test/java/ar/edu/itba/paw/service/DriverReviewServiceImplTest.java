package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.interfaces.persistence.DriverReviewDao;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.DriverReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.services.DriverReviewServiceImpl;
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
public class DriverReviewServiceImplTest {

    private static final City ORIGIN_CITY = new City(1, "Agronomía", 1);
    private static final long TRIP_ID = 1L;
    private static final City DESTINATION_CITY = new City(1, "Agronomía", 1);
    private static final LocalDateTime DATE_TIME = LocalDateTime.now().minusYears(1);
    private static final User DRIVER = new User(2, "DRIVER", "SURNAME", "driver@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "DRIVER", 1L);
    private static final User USER = new User(1, "USER", "SURNAME", "user@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final Car CAR = new Car(1, "ABC123", "INFO", DRIVER, 1L);
    private static final Trip TRIP = new Trip(TRIP_ID, ORIGIN_CITY, "ORIGIN ADDRESS", DESTINATION_CITY, "DESTINATION ADDRESS", DATE_TIME, DATE_TIME.plusDays(7), 4, DRIVER, CAR, 0, BigDecimal.valueOf(10.0).doubleValue());
    private static final Passenger PASSENGER = new Passenger(USER, TRIP, TRIP.getStartDateTime(), TRIP.getEndDateTime());
    private static final int RATING = 4;
    private static final String COMMENT = "Really good car";
    private static final DriverReviewOptions OPTION = DriverReviewOptions.SAFE_DRIVER;
    private static final DriverReview REVIEW = new DriverReview(TRIP,USER,DRIVER,RATING,COMMENT,OPTION);
    @Mock
    private DriverReviewDao driverReviewDao;

    @Mock
    private TripService tripService;

    @Mock
    private UserService userService;

    @InjectMocks
    private DriverReviewServiceImpl driverReviewService;

    @Before
    public void setup(){
        PASSENGER.setPassengerState(Passenger.PassengerState.ACCEPTED);
    }

    @Test
    public void testCreate() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(userService.findById(eq(DRIVER.getUserId()))).thenReturn(Optional.of(DRIVER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(true);
        when(tripService.userIsPassenger(anyLong(),any())).thenReturn(true);
        when(driverReviewDao.canReviewDriver(any(),any(),any())).thenReturn(true);
        when(driverReviewDao.createDriverReview(any(Trip.class),any(Passenger.class),any(User.class),anyInt(),anyString(),any(DriverReviewOptions.class))).thenReturn(REVIEW);

        DriverReview driverReview = driverReviewService.createDriverReview(TRIP_ID,DRIVER.getUserId(),RATING,COMMENT,OPTION);

        Assert.assertNotNull(driverReview);
        Assert.assertEquals(driverReview.getTrip().getTripId(),TRIP_ID);
        Assert.assertEquals(driverReview.getReviewer().getUserId(),USER.getUserId());
        Assert.assertEquals(driverReview.getReviewed().getUserId(),DRIVER.getUserId());
        Assert.assertEquals(driverReview.getComment(),COMMENT);
        Assert.assertEquals(driverReview.getOption(),OPTION);
        Assert.assertEquals(driverReview.getRating(),RATING);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNotDriver() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(userService.findById(eq(DRIVER.getUserId()))).thenReturn(Optional.of(DRIVER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(false);

        driverReviewService.createDriverReview(TRIP_ID,DRIVER.getUserId(),RATING,COMMENT,OPTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNotPassenger() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(userService.findById(eq(DRIVER.getUserId()))).thenReturn(Optional.of(DRIVER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(true);
        when(tripService.userIsPassenger(anyLong(),any())).thenReturn(false);

        driverReviewService.createDriverReview(TRIP_ID,DRIVER.getUserId(),RATING,COMMENT,OPTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateForSelf() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(userService.findById(eq(USER.getUserId()))).thenReturn(Optional.of(USER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(true);
        when(tripService.userIsPassenger(anyLong(),any())).thenReturn(true);

        driverReviewService.createDriverReview(TRIP_ID,USER.getUserId(),RATING,COMMENT,OPTION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateForAlreadyReviewed() throws UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER));
        when(userService.findById(eq(USER.getUserId()))).thenReturn(Optional.of(DRIVER));
        when(tripService.userIsDriver(anyLong(),any())).thenReturn(true);
        when(tripService.userIsPassenger(anyLong(),any())).thenReturn(true);
        when(driverReviewDao.canReviewDriver(any(Trip.class),any(Passenger.class),any(User.class))).thenReturn(false);
        driverReviewService.createDriverReview(TRIP_ID,USER.getUserId(),RATING,COMMENT,OPTION);
    }



}
