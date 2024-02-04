package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.interfaces.persistence.DriverReviewDao;
import ar.edu.itba.paw.interfaces.services.DriverReviewService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.DriverReviewOptions;
import ar.edu.itba.paw.models.reviews.ReviewState;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DriverReviewServiceImpl implements DriverReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverReviewServiceImpl.class);

    private final DriverReviewDao driverReviewDao;
    private final TripService tripService;
    private final UserService userService;

    @Autowired
    public DriverReviewServiceImpl(DriverReviewDao driverReviewDao, TripService tripService, UserService userService) {
        this.driverReviewDao = driverReviewDao;
        this.tripService = tripService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public DriverReview createDriverReview(final long tripId, final long driverId, int rating, String comment, DriverReviewOptions option) throws TripNotFoundException, UserNotLoggedInException, PassengerNotFoundException, UserNotFoundException {
        Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        Passenger reviewer = tripService.getPassenger(trip,user).orElseThrow(PassengerNotFoundException::new);
        User driver = userService.findById(driverId).orElseThrow(UserNotFoundException::new);
        if(!canReviewDriver(trip, reviewer, driver)) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to review driver with id {}, but it's not finished yet or was already reviewed", reviewer.getUserId(), driver.getUserId(), e);
            throw e;
        }
        return driverReviewDao.createDriverReview(trip, reviewer, driver, rating, comment, option);
    }

    @Transactional
    @Override
    public Optional<DriverReview> findById(final long id){
        return driverReviewDao.findById(id);
    }


    @Transactional
    @Override
    public PagedContent<DriverReview> getDriverReviews(long userId, int page, int pageSize) throws UserNotFoundException {
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        return driverReviewDao.getDriverReviews(user, page, pageSize);
    }

    @Transactional
    @Override
    public PagedContent<DriverReview> getDriverReviewsMadeByUserOnTrip(final long reviewerId, final long tripId, final int page, final int pageSize) throws UserNotFoundException, TripNotFoundException {
        final User reviewer = userService.findById(reviewerId).orElseThrow(UserNotFoundException::new);
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        return driverReviewDao.getDriverReviewsMadeByUserOnTrip(reviewer,trip,page,pageSize);
    }


    private boolean canReviewDriver(Trip trip, Passenger reviewer, User driver) {
        if(!tripService.userIsDriver(trip.getTripId(), driver)) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to review user with id {}, but he's not the driver of the trip with id {}", reviewer.getUserId(), driver.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(!tripService.userIsPassenger(trip.getTripId(), reviewer.getUser())) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("User with id {} tried to review driver with id {}, but he's not a passenger of the trip with id {}", reviewer.getUserId(), driver.getUserId(), trip.getTripId(), e);
            throw e;
        }
        if(reviewer.getUserId() == driver.getUserId()) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to review himself", reviewer.getUserId(), e);
            throw e;
        }
        return getReviewState(trip, reviewer, driver) == ReviewState.PENDING;
    }

    private ReviewState getReviewState(Trip trip, Passenger reviewer, User driver) {
        LocalDateTime now = LocalDateTime.now();
        //if(now.isBefore(trip.getEndDateTime()) && now.isBefore(reviewer.getEndDateTime())) {
        //Si el pasajero todavía no terminó o no fue aceptado al viaje
        if(!reviewer.isTripEnded() || !reviewer.isAccepted()) {
            return ReviewState.DISABLED;
        }
        return driverReviewDao.canReviewDriver(trip, reviewer, driver) ? ReviewState.PENDING : ReviewState.DONE;
    }

}
