package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.interfaces.persistence.PassengerReviewDao;
import ar.edu.itba.paw.interfaces.services.PassengerReviewService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.ItemReview;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;
import ar.edu.itba.paw.models.reviews.ReviewState;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerReviewServiceImpl implements PassengerReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerReviewServiceImpl.class);

    private final PassengerReviewDao passengerReviewDao;
    private final TripService tripService;

    private final UserService userService;

    @Autowired
    public PassengerReviewServiceImpl(PassengerReviewDao passengerReviewDao, TripService tripService, UserService userService) {
        this.passengerReviewDao = passengerReviewDao;
        this.tripService = tripService;
        this.userService = userService;
    }

    @Transactional
    @Override
    public PassengerReview createPassengerReview(long tripId, long reviewedId, int rating, String comment, PassengerReviewOptions option) throws UserNotLoggedInException, TripNotFoundException, UserNotFoundException, PassengerNotFoundException {
        User reviewer = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        User userReviewed = userService.findById(reviewedId).orElseThrow(UserNotFoundException::new);
        Passenger reviewed = tripService.getPassenger(trip,userReviewed).orElseThrow(RuntimeException::new);
        if(!canReviewPassenger(trip, reviewed)) {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review passenger with id {}, but it's not finished yet or was already reviewed", reviewer.getUserId(), reviewed.getUserId(), e);
            throw e;
        }
        return passengerReviewDao.createPassengerReview(trip, reviewer, reviewed, rating, comment, option);
    }

    @Transactional
    @Override
    public double getPassengerRating(long userId) throws UserNotFoundException {
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        return passengerReviewDao.getPassengerRating(user);
    }
    @Transactional
    @Override
    public double getPassengerRatingOwnUser() throws UserNotLoggedInException {
        User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        return passengerReviewDao.getPassengerRating(user);
    }

    @Transactional
    @Override
    public PagedContent<PassengerReview> getPassengerReviews(long userId, int page, int pageSize) throws UserNotFoundException {
        User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        return passengerReviewDao.getPassengerReviews(user, page, pageSize);
    }

    @Transactional
    @Override
    public PagedContent<PassengerReview> getPassengerReviewsOwnUser( int page, int pageSize) throws UserNotLoggedInException {
        User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        return passengerReviewDao.getPassengerReviews(user, page, pageSize);
    }

    @Transactional
    @Override
    public boolean canReviewPassenger(final Trip trip, Passenger reviewed) throws UserNotLoggedInException, PassengerNotFoundException {
        User reviewer = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        if(tripService.userIsDriver(trip.getTripId(), reviewer)) {
            return canDriverReviewPassenger(trip, reviewer, reviewed);
        } else if (tripService.userIsPassenger(trip.getTripId(), reviewer)) {
            final Passenger reviewerPassenger = tripService.getPassenger(trip.getTripId(), reviewer).orElseThrow(PassengerNotFoundException::new);
            return canPassengerReviewPassenger(reviewerPassenger, reviewed);
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
        return getReviewStateForDriver(trip, reviewer, reviewed) == ReviewState.PENDING;
    }

    private ReviewState getReviewStateForDriver(Trip trip, User reviewer, Passenger reviewed) {
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(trip.getEndDateTime()) && now.isBefore(reviewed.getEndDateTime())){
            return ReviewState.DISABLED;
        }
        return passengerReviewDao.canReviewPassenger(trip, reviewer, reviewed) ? ReviewState.PENDING : ReviewState.DONE;
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
        return getReviewStateForPassenger(reviewer.getTrip(), reviewer, reviewed) == ReviewState.PENDING;
    }

    private ReviewState getReviewStateForPassenger(Trip trip, Passenger reviewer, Passenger reviewed) {
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(reviewer.getEndDateTime()) && now.isBefore(reviewed.getEndDateTime())){
            return ReviewState.DISABLED;
        }
        return passengerReviewDao.canReviewPassenger(trip, reviewer.getUser(), reviewed) ? ReviewState.PENDING : ReviewState.DONE;
    }

    @Transactional
    @Override
    public List<ItemReview<Passenger>> getPassengersReviewState(final long tripId, List<Passenger> passengers) throws UserNotLoggedInException, TripNotFoundException, PassengerNotFoundException {
        User reviewer = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        if(tripService.userIsDriver(trip.getTripId(), reviewer)) {
            return passengers.stream().filter(
                    passenger -> reviewer.getUserId() != passenger.getUserId())
            .map(
                    passenger -> new ItemReview<>(passenger, getReviewStateForDriver(trip, reviewer, passenger)))
            .sorted(
                    Comparator.comparing(ItemReview::getState)
            )
            .collect(Collectors.toList());
        } else if (tripService.userIsPassenger(trip.getTripId(), reviewer)) {
            final Passenger reviewerPassenger = tripService.getPassenger(trip.getTripId(), reviewer).orElseThrow(PassengerNotFoundException::new);
            return passengers.stream().filter(
                    passenger -> reviewer.getUserId() != passenger.getUserId())
            .map(
                    passenger -> new ItemReview<>(passenger, getReviewStateForPassenger(trip, reviewerPassenger, passenger)))
            .sorted(
                    Comparator.comparing(ItemReview::getState)
            )
            .collect(Collectors.toList());
        } else {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("User with id {} is not a passenger nor the driver in trip with id {}", reviewer.getUserId(), trip.getTripId(), e);
            throw e;
        }
    }
}