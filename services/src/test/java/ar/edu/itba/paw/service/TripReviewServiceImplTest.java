package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.services.PassengerReviewService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
//import ar.edu.itba.paw.models.reviews.ItemReview;
//import ar.edu.itba.paw.models.reviews.TripReviewCollection;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.services.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
//public class TripReviewServiceImplTest {
//
//    private static final City originCity = new City(1, "Agronomía", 1);
//    private static final long tripId = 1L;
//    private static final String originAddress = "ORIGIN ADDRESS";
//    private static final City destinationCity = new City(1, "Agronomía", 1);
//    private static final String destinationAddress = "DESTINATION ADDRESS";
//    private static final LocalDateTime dateTime = LocalDateTime.now();
//    private static final int maxSeats = 4;
//
//    private static final User driver = new User(2, "DRIVER", "SURNAME", "driver@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "DRIVER", 1L);
//    private static final User user = new User(1, "USER", "SURNAME", "user@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
//
//    private static final Car car = new Car(1, "ABC123", "INFO", user, 1L);
//    private static final BigDecimal price = BigDecimal.valueOf(10.0);
//    private static final Trip trip = new Trip(tripId, originCity, originAddress, destinationCity, destinationAddress, dateTime, dateTime.plusDays(7), maxSeats, driver, car, 0, price.doubleValue());
//
//
//
//    private static final long TRIP_ID = 1L;
//
//    @Mock
//    private TripServiceImpl tripService;
//    @Mock
//    private PassengerReviewService passengerReviewService;
//    @Mock
//    private DriverReviewServiceImpl driverReviewService;
//    @Mock
//    private CarReviewServiceImpl carReviewService;
////    @InjectMocks
////    private TripReviewServiceImpl tripReviewService;
//
//
////    @Test(expected = TripNotFoundException.class)
////    public void TestNotTripGetReviewsForPassenger() throws UserNotFoundException, TripNotFoundException, CarNotFoundException, PassengerNotFoundException, UserNotLoggedInException {
////        when(tripService.findById(TRIP_ID)).thenReturn(Optional.empty());
////        tripReviewService.getReviewsForPassenger(TRIP_ID, 1L);
////        Assert.fail();
////    }
//
////    @Test(expected = UserNotFoundException.class)
////    public void TestNotUserGetReviewsForPassenger2() throws UserNotFoundException, TripNotFoundException, CarNotFoundException, PassengerNotFoundException, UserNotLoggedInException {
////        when(tripService.findById(TRIP_ID)).thenReturn(Optional.of(trip));
////        when(tripService.getPassenger(TRIP_ID, 1L)).thenReturn(Optional.empty());
////        tripReviewService.getReviewsForPassenger(TRIP_ID, 1L);
////        Assert.fail();
////    }
//
//
//}
