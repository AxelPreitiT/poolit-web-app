package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.CarReviewDao;
import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.models.reviews.CarReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.services.CarReviewServiceImpl;
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
public class CarReviewServiceImplTest {

    private static final City ORIGIN_CITY = new City(1, "Agronomía", 1);
    private static final long TRIP_ID = 1L;
    private static final String ORIGIN_ADDRESS = "ORIGIN ADDRESS";
    private static final City DESTINATION_CITY = new City(1, "Agronomía", 1);
    private static final String DESTINATION_ADDRESS = "DESTINATION ADDRESS";
    private static final LocalDateTime DATE_TIME = LocalDateTime.now().minusYears(1);
    private static final int MAX_SEATS = 4;
    private static final User DRIVER = new User(2, "DRIVER", "SURNAME", "driver@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "DRIVER", 1L);
    private static final User USER = new User(1, "USER", "SURNAME", "user@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final Car CAR = new Car(1, "ABC123", "INFO", DRIVER, 1L);
    private static final Car CAR_2 = new Car(2, "ABC123", "INFO", USER, 1L);
    private static final BigDecimal PRICE = BigDecimal.valueOf(10.0);
    private static final Trip TRIP = new Trip(TRIP_ID, ORIGIN_CITY, ORIGIN_ADDRESS, DESTINATION_CITY, DESTINATION_ADDRESS, DATE_TIME, DATE_TIME.plusDays(7), MAX_SEATS, DRIVER, CAR, 0, PRICE.doubleValue());
    private static final User REVIEWER = USER;
    private static final Car REVIEWED = CAR;
    private static final Passenger CURRENT_PASSENGER = new Passenger(USER, TRIP, TRIP.getStartDateTime(), TRIP.getEndDateTime());

    private static final int RATING = 4;
    private static final String COMMENT = "Really good car";
    private static final CarReviewOptions CAR_REVIEW_OPTION = CarReviewOptions.VERY_GOOD_STATE;

    private static final CarReview CAR_REVIEW = new CarReview(TRIP, REVIEWER, REVIEWED, RATING, COMMENT, CAR_REVIEW_OPTION, DATE_TIME.plusYears(1));


    @Mock
    private TripService tripService;

    @Mock
    private UserService userService;

    @Mock
    private CarService carService;

    @Mock
    private CarReviewDao carReviewDao;

    @InjectMocks
    private CarReviewServiceImpl carReviewService;


    @Before
    public void setup(){
        CURRENT_PASSENGER.setPassengerState(Passenger.PassengerState.ACCEPTED);
    }
    @Test
    public void testCreate() throws UserNotFoundException, CarNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(REVIEWER));
        when(tripService.getPassenger(any(), any())).thenReturn(Optional.of(CURRENT_PASSENGER));
        when(carService.findById(anyLong())).thenReturn(Optional.of(REVIEWED));
        when(tripService.userIsPassenger(anyLong(), any(User.class))).thenReturn(true);
        when(carReviewDao.canReviewCar(any(), any(), any())).thenReturn(true);
        when(carReviewDao.createCarReview(any(Trip.class), any(Passenger.class), any(Car.class), anyInt(), anyString(), any(CarReviewOptions.class)))
                .thenReturn(CAR_REVIEW);

        CarReview carReview = carReviewService.createCarReview(TRIP_ID, CAR.getCarId(), RATING, COMMENT, CAR_REVIEW_OPTION);

        Assert.assertNotNull(carReview);
        Assert.assertEquals(carReview.getReviewId(), CAR_REVIEW.getReviewId());
        Assert.assertEquals(carReview.getTrip().getTripId(), CAR_REVIEW.getTrip().getTripId());
        Assert.assertEquals(carReview.getReviewer().getUserId(), CAR_REVIEW.getReviewer().getUserId());
        Assert.assertEquals(carReview.getCar().getCarId(), CAR_REVIEW.getCar().getCarId());
        Assert.assertEquals(carReview.getRating(), CAR_REVIEW.getRating());
        Assert.assertEquals(carReview.getComment(), CAR_REVIEW.getComment());
        Assert.assertEquals(carReview.getOption(), CAR_REVIEW.getOption());
        Assert.assertEquals(carReview.getDate(), CAR_REVIEW.getDate());
    }

    @Test
    public void testCreateForOtherCar(){
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(REVIEWER));
        when(tripService.getPassenger(any(), any())).thenReturn(Optional.of(CURRENT_PASSENGER));
        when(carService.findById(anyLong())).thenReturn(Optional.of(CAR_2));

        Assert.assertThrows(IllegalArgumentException.class,()->carReviewService.createCarReview(TRIP_ID, CAR_2.getCarId(), RATING, COMMENT, CAR_REVIEW_OPTION));
   }

    @Test
    public void testCreateWhenNotPassenger(){
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(REVIEWER));
        when(tripService.getPassenger(any(), any())).thenReturn(Optional.of(CURRENT_PASSENGER));
        when(carService.findById(anyLong())).thenReturn(Optional.of(REVIEWED));
        when(tripService.userIsPassenger(anyLong(), any(User.class))).thenReturn(false);

        Assert.assertThrows(IllegalArgumentException.class,()->carReviewService.createCarReview(TRIP_ID, REVIEWED.getCarId(), RATING, COMMENT, CAR_REVIEW_OPTION));
    }

    @Test
    public void testCreateForOwnCar(){
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER));
        when(tripService.getPassenger(any(), any())).thenReturn(Optional.of(CURRENT_PASSENGER));
        when(carService.findById(anyLong())).thenReturn(Optional.of(REVIEWED));
        when(tripService.userIsPassenger(anyLong(), any(User.class))).thenReturn(true);

        Assert.assertThrows(IllegalArgumentException.class,()->carReviewService.createCarReview(TRIP_ID, CAR_2.getCarId(), RATING, COMMENT, CAR_REVIEW_OPTION));
    }

    @Test
    public void testCreateForAlreadyReviewed(){
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(REVIEWER));
        when(tripService.getPassenger(any(), any())).thenReturn(Optional.of(CURRENT_PASSENGER));
        when(carService.findById(anyLong())).thenReturn(Optional.of(REVIEWED));
        when(tripService.userIsPassenger(anyLong(), any(User.class))).thenReturn(true);
        when(carReviewDao.canReviewCar(any(), any(), any())).thenReturn(false);

        Assert.assertThrows(IllegalArgumentException.class,()->carReviewService.createCarReview(TRIP_ID, CAR_2.getCarId(), RATING, COMMENT, CAR_REVIEW_OPTION));
    }

}
