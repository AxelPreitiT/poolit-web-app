package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.DriverReviewDao;
import ar.edu.itba.paw.interfaces.services.DriverReviewService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.DriverReviewOptions;
import ar.edu.itba.paw.models.reviews.ItemReview;
import ar.edu.itba.paw.models.reviews.ReviewState;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DriverReviewServiceImpl implements DriverReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverReviewServiceImpl.class);

    private final DriverReviewDao driverReviewDao;
    private final TripService tripService;

    @Autowired
    public DriverReviewServiceImpl(DriverReviewDao driverReviewDao, TripService tripService) {
        this.driverReviewDao = driverReviewDao;
        this.tripService = tripService;
    }

    @Transactional
    @Override
    public DriverReview createDriverReview(Trip trip, Passenger reviewer, User driver, int rating, String comment, DriverReviewOptions option) {
        if(!canReviewDriver(trip, reviewer, driver)) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review driver with id {}, but it's not finished yet or was already reviewed", reviewer.getUserId(), driver.getUserId(), e);
            throw e;
        }
        return driverReviewDao.createDriverReview(trip, reviewer, driver, rating, comment, option);
    }

    @Override
    public double getDriverRating(User user) {
        return driverReviewDao.getDriverRating(user);
    }

    @Override
    public List<DriverReview> getDriverReviews(User user) {
        return driverReviewDao.getDriverReviews(user);
    }

    @Override
    public boolean canReviewDriver(Trip trip, Passenger reviewer, User driver) {
        if(!tripService.userIsDriver(trip.getTripId(), driver)) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review user with id {}, but he's not the driver of the trip with id {}", reviewer.getUserId(), driver.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(!tripService.userIsPassenger(trip.getTripId(), reviewer.getUser())) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("User with id {} tried to review driver with id {}, but he's not a passenger of the trip with id {}", reviewer.getUserId(), driver.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(reviewer.getUserId() == driver.getUserId()) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review himself", reviewer.getUserId(), e);
            throw e;
        }
        return getReviewState(trip, reviewer, driver) == ReviewState.PENDING;
    }

    private ReviewState getReviewState(Trip trip, Passenger reviewer, User driver) {
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(trip.getEndDateTime()) && now.isBefore(reviewer.getEndDateTime())) {
            return ReviewState.DISABLED;
        }
        return driverReviewDao.canReviewDriver(trip, reviewer, driver) ? ReviewState.PENDING : ReviewState.DONE;
    }

    @Override
    public ItemReview<User> getDriverReviewState(Trip trip, Passenger reviewer, User driver) {
        return new ItemReview<>(driver, getReviewState(trip, reviewer, driver));
    }
}
