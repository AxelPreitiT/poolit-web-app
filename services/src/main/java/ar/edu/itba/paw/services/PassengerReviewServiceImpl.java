package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.PassengerReviewDao;
import ar.edu.itba.paw.interfaces.services.PassengerReviewService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PassengerReviewServiceImpl implements PassengerReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerReviewServiceImpl.class);

    private final PassengerReviewDao passengerReviewDao;
    private final TripService tripService;

    @Autowired
    public PassengerReviewServiceImpl(PassengerReviewDao passengerReviewDao, TripService tripService) {
        this.passengerReviewDao = passengerReviewDao;
        this.tripService = tripService;
    }

    @Transactional
    @Override
    public PassengerReview createPassengerReview(Trip trip, User reviewer, Passenger reviewed, int rating, String comment, PassengerReviewOptions option) {
        if(!canReviewPassenger(trip, reviewer, reviewed)) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review passenger with id {}, but it's not finished yet or was already reviewed", reviewer.getUserId(), reviewed.getUserId(), e);
            throw e;
        }
        return passengerReviewDao.createPassengerReview(trip, reviewer, reviewed, rating, comment, option);
    }

    @Override
    public double getPassengerRating(User user) {
        return passengerReviewDao.getPassengerRating(user);
    }

    @Override
    public List<PassengerReview> getPassengerReviews(User user) {
        return passengerReviewDao.getPassengerReviews(user);
    }

    @Override
    public boolean canReviewPassenger(final Trip trip, User reviewer, Passenger reviewed) {
        if(tripService.userIsDriver(trip.getTripId(), reviewer)) {
            return canDriverReviewPassenger(trip, reviewer, reviewed);
        } else if (tripService.userIsPassenger(trip.getTripId(), reviewer)) {
            final Optional<Passenger> reviewerPassenger = tripService.getPassenger(trip.getTripId(), reviewer);
            if(!reviewerPassenger.isPresent()) {
                IllegalStateException e = new IllegalStateException();
                LOGGER.error("Can't find passenger with id {} in trip with id {}", reviewer.getUserId(), trip.getTripId(), e);
                throw e;
            }
            return canPassengerReviewPassenger(reviewerPassenger.get(), reviewed);
        } else {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("User with id {} tried to review passenger with id {}, but he is not a passenger nor the driver in the trip", reviewer.getUserId(), reviewed.getUserId(), e);
            throw e;
        }
    }

    private boolean canDriverReviewPassenger(Trip trip, User reviewer, Passenger reviewed) {
        if(trip.getTripId() != reviewed.getTrip().getTripId()) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Driver with id {} tried to review passenger with id {}, but they are not in the same trip", reviewer.getUserId(), reviewed.getUserId(), e);
            throw e;
        }
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(trip.getEndDateTime()) && now.isBefore(reviewed.getEndDateTime())){
            return false;
        }
        return passengerReviewDao.canReviewPassenger(trip, reviewer, reviewed);
    }

    private boolean canPassengerReviewPassenger(Passenger reviewer, Passenger reviewed) {
        if(reviewer.getTrip().getTripId() != reviewed.getTrip().getTripId()){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review passenger with id {}, but they are not in the same trip", reviewer.getUserId(), reviewed.getUserId(), e);
            throw e;
        }
        if(reviewer.getUserId() == reviewed.getUserId()){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review himself", reviewer.getUserId(), e);
            throw e;
        }
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(reviewer.getEndDateTime()) && now.isBefore(reviewed.getEndDateTime())){
            return false;
        }
        return passengerReviewDao.canReviewPassenger(reviewer.getTrip(), reviewer.getUser(), reviewed);
    }

    @Override
    public List<Passenger> getPassengersToReview(final Trip trip, User reviewer) {
        List<Passenger> passengers;
        if(tripService.userIsDriver(trip.getTripId(), reviewer)) {
            passengers = tripService.getPassengers(trip);
            passengers.removeIf(passenger -> !canDriverReviewPassenger(trip, reviewer, passenger));
        } else if (tripService.userIsPassenger(trip.getTripId(), reviewer)) {
            final Optional<Passenger> reviewerPassenger = tripService.getPassenger(trip.getTripId(), reviewer);
            if(!reviewerPassenger.isPresent()) {
                IllegalStateException e = new IllegalStateException();
                LOGGER.error("Can't find passenger with id {} in trip with id {}", reviewer.getUserId(), trip.getTripId(), e);
                throw e;
            }
            passengers = tripService.getPassengersRecurrent(trip, reviewerPassenger.get().getEndDateTime(), reviewerPassenger.get().getStartDateTime());
            passengers.removeIf(passenger -> !canPassengerReviewPassenger(reviewerPassenger.get(), passenger));
        } else {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("User with id {} is not a passenger nor the driver in trip with id {}", reviewer.getUserId(), trip.getTripId(), e);
            throw e;
        }
        return passengers;
    }
}
