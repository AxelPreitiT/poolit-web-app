package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.services.PassengerReviewService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.ItemReview;
import ar.edu.itba.paw.models.reviews.TripReviewCollection;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.services.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TripReviewServiceImplTest {


    private static final long TRIP_ID = 1L;

    @Mock
    private TripServiceImpl tripService;
    @Mock
    private PassengerReviewService passengerReviewService;
    @Mock
    private DriverReviewServiceImpl driverReviewService;
    @Mock
    private CarReviewServiceImpl carReviewService;
    @InjectMocks
    private TripReviewServiceImpl tripReviewService;


    @Test(expected = TripNotFoundException.class)
    public void TestNotTripGetReviewsForPassenger() throws UserNotFoundException, TripNotFoundException {
        when(tripService.findById(TRIP_ID)).thenReturn(Optional.empty());
        tripReviewService.getReviewsForPassenger(TRIP_ID, 1L);
        Assert.fail();
    }

    /*
    @Test(expected = UserNotFoundException.class)
    public void TestNotUserGetReviewsForPassenger2() throws UserNotFoundException, TripNotFoundException {
        when(tripService.findById(TRIP_ID)).thenReturn(Optional.of(new Trip()));
        when(tripService.getPassenger(TRIP_ID, 1L)).thenReturn(Optional.empty());
        tripReviewService.getReviewsForPassenger(TRIP_ID, 1L);
        Assert.fail();
    }
    */



}
