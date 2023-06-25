package ar.edu.itba.paw.services;


import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.ItemReview;
import ar.edu.itba.paw.models.reviews.TripReviewCollection;
import ar.edu.itba.paw.models.trips.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TripReviewServiceImpl implements TripReviewService {

    private final TripService tripService;
    private final PassengerReviewService passengerReviewService;

    private final DriverReviewService driverReviewService;

    private final CarReviewService carReviewService;

    @Autowired
    public TripReviewServiceImpl(final TripService tripService,
                                 final PassengerReviewService passengerReviewService,
                                 final DriverReviewService driverReviewService,
                                 final CarReviewService carReviewService){
        this.tripService = tripService;
        this.passengerReviewService = passengerReviewService;
        this.driverReviewService = driverReviewService;
        this.carReviewService = carReviewService;
    }


    @Transactional
    @Override
    public TripReviewCollection getReviewsForDriver(final long tripId) throws TripNotFoundException, PassengerNotFoundException, UserNotLoggedInException {
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        final List<Passenger> passengersComplete = tripService.getAcceptedPassengers(trip,trip.getStartDateTime(),trip.getEndDateTime());
        final List<ItemReview<Passenger>> passengersToReview = passengerReviewService.getPassengersReviewState(tripId, passengersComplete);
        return new TripReviewCollection(null, null, passengersToReview);
    }

    @Transactional
    @Override
    public TripReviewCollection getReviewsForPassenger(long tripId, long userId) throws TripNotFoundException, UserNotFoundException, CarNotFoundException, PassengerNotFoundException, UserNotLoggedInException {
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        final Passenger passenger = tripService.getPassenger(tripId,userId).orElseThrow(UserNotFoundException::new);
        if(!passenger.getAccepted()) {
            return TripReviewCollection.empty();
        }
        final List<Passenger> passengers = tripService.getAcceptedPassengers(trip, passenger.getStartDateTime(), passenger.getEndDateTime());
        final List<ItemReview<Passenger>> passengersToReview = passengerReviewService.getPassengersReviewState(tripId, passengers);
        final ItemReview<User> driver = driverReviewService.getDriverReviewState(tripId);
        final ItemReview<Car> car = carReviewService.getCarReviewState(tripId);
        return new TripReviewCollection(driver, car, passengersToReview);
    }

}
