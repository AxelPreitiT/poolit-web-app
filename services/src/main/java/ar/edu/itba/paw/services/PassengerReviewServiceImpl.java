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
import java.util.Optional;

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
        final User reviewer = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        final User userReviewed = userService.findById(reviewedId).orElseThrow(UserNotFoundException::new);
        final Passenger reviewed = tripService.getPassenger(trip,userReviewed).orElseThrow(PassengerNotFoundException::new);
        if(!canReviewPassenger(trip, reviewed)) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to review passenger with id {}, but it's not finished yet or was already reviewed", reviewer.getUserId(), reviewed.getUserId(), e);
            throw e;
        }
        return passengerReviewDao.createPassengerReview(trip, reviewer, reviewed, rating, comment, option);
    }

    @Transactional
    @Override
    public Optional<PassengerReview> findById(final long reviewId){
        return passengerReviewDao.findById(reviewId);
    }

    @Transactional
    @Override
    public PagedContent<PassengerReview> getPassengerReviews(long userId, int page, int pageSize) throws UserNotFoundException {
        final User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        return passengerReviewDao.getPassengerReviews(user, page, pageSize);
    }

    @Transactional
    @Override
    public PagedContent<PassengerReview> getPassengerReviewsMadeByUserOnTrip(final long reviewerUserid, final long tripId, final int page, final  int pageSize) throws UserNotFoundException, TripNotFoundException {
        final User reviewer = userService.findById(reviewerUserid).orElseThrow(UserNotFoundException::new);
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        return passengerReviewDao.getPassengerReviewsMadeByUserOnTrip(reviewer,trip,page,pageSize);
    }

    @Transactional
    @Override
    public PagedContent<PassengerReview> getPassengerReview(final long reviewedId,final long reviewerId, final  long tripId) throws UserNotFoundException, TripNotFoundException {
        final User reviewed = userService.findById(reviewedId).orElseThrow(UserNotFoundException::new);
        final User reviewer = userService.findById(reviewerId).orElseThrow(UserNotFoundException::new);
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        Optional<PassengerReview> ans = passengerReviewDao.getPassengerReview(reviewed,reviewer,trip);
        return PagedContent.fromOptional(ans);
    }

    private boolean canReviewPassenger(final Trip trip, Passenger reviewed) throws UserNotLoggedInException, PassengerNotFoundException {
        User reviewer = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        if(tripService.userIsDriver(trip.getTripId(), reviewer)) {
            return canDriverReviewPassenger(trip, reviewer, reviewed);
        } else if (tripService.userIsPassenger(trip.getTripId(), reviewer)) {
            final Passenger reviewerPassenger = tripService.getPassenger(trip.getTripId(), reviewer).orElseThrow(PassengerNotFoundException::new);
            return canPassengerReviewPassenger(reviewerPassenger, reviewed);
        } else {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("User with id {} tried to review passenger with id {}, but he is not a passenger nor the driver in the trip", reviewer.getUserId(), reviewed.getUserId(), e);
            throw e;
        }
    }

    private boolean canDriverReviewPassenger(Trip trip, User reviewer, Passenger reviewed) {
        if(trip.getTripId() != reviewed.getTrip().getTripId()) {
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Driver with id {} tried to review passenger with id {}, but they are not in the same trip", reviewer.getUserId(), reviewed.getUserId(), e);
            throw e;
        }
        return getReviewStateForDriver(trip, reviewer, reviewed) == ReviewState.PENDING;
    }

    private ReviewState getReviewStateForDriver(Trip trip, User reviewer, Passenger reviewed) {
        LocalDateTime now = LocalDateTime.now();
        //if now.isBefore(trip.getEndDateTime()) (termino el viaje) ->now.isBefore(reviewed.getEndDateTime()) para todos los pasajeros
        //Cambio la primera condición porque sólo me interesa la segunda
//        if(now.isBefore(trip.getEndDateTime()) && now.isBefore(reviewed.getEndDateTime())){
        if(!reviewed.isTripEnded() || !reviewed.isAccepted()){
            return ReviewState.DISABLED;
        }
        return passengerReviewDao.canReviewPassenger(trip, reviewer, reviewed) ? ReviewState.PENDING : ReviewState.DONE;
    }

    private boolean canPassengerReviewPassenger(Passenger reviewer, Passenger reviewed) {
        if(reviewer.getTrip().getTripId() != reviewed.getTrip().getTripId()){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to review passenger with id {}, but they are not in the same trip", reviewer.getUserId(), reviewed.getUserId(), e);
            throw e;
        }
        if(reviewer.getUserId() == reviewed.getUserId()){
            IllegalArgumentException e = new IllegalArgumentException();
            LOGGER.error("Passenger with id {} tried to review himself", reviewer.getUserId(), e);
            throw e;
        }
        return getReviewStateForPassenger(reviewer.getTrip(), reviewer, reviewed) == ReviewState.PENDING;
    }

    private ReviewState getReviewStateForPassenger(Trip trip, Passenger reviewer, Passenger reviewed) {
        LocalDateTime now = LocalDateTime.now();
//        if(now.isBefore(reviewer.getEndDateTime()) && now.isBefore(reviewed.getEndDateTime())){
        //Si el reviewer todavía no terminó su período o no fue aceptado o el pasajero a quien va a reseñar no fue aceptado
        if(!reviewer.isTripEnded() || !reviewed.isAccepted() || !reviewer.isAccepted()){
            return ReviewState.DISABLED;
        }
        return passengerReviewDao.canReviewPassenger(trip, reviewer.getUser(), reviewed) ? ReviewState.PENDING : ReviewState.DONE;
    }

}
