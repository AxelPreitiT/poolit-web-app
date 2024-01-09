package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.reviews.ItemReview;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;
import java.util.Optional;

public interface PassengerReviewService {

    PassengerReview createPassengerReview(final long tripId, final long reviewedId, final int rating, final String comment, final PassengerReviewOptions option) throws UserNotLoggedInException, TripNotFoundException, UserNotFoundException, PassengerNotFoundException;

    Optional<PassengerReview> findById(final long tripId);



    double getPassengerRating(final long userId) throws UserNotFoundException;

    double getPassengerRatingOwnUser() throws UserNotLoggedInException;

    PagedContent<PassengerReview> getPassengerReviews(final long userId, int page, int pageSize) throws UserNotFoundException;

    PagedContent<PassengerReview> getPassengerReviewsMadeByUserOnTrip(final long reviewerUserid, final long tripId, final int page, final  int pageSize) throws UserNotFoundException, TripNotFoundException;

    PagedContent<PassengerReview> getPassengerReviewsOwnUser( int page, int pageSize) throws UserNotLoggedInException;

    boolean canReviewPassenger(final Trip trip, final Passenger reviewed) throws UserNotLoggedInException, PassengerNotFoundException;

    //TODO Cambiar passengers a datos para obtenerlos aca?
    List<ItemReview<Passenger>> getPassengersReviewState(final long tripId, final List<Passenger> passengers) throws UserNotLoggedInException, TripNotFoundException, PassengerNotFoundException;
}
