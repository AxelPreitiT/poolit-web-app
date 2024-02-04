package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;

import java.util.Optional;

public interface PassengerReviewService {

    PassengerReview createPassengerReview(final long tripId, final long reviewedId, final int rating, final String comment, final PassengerReviewOptions option) throws UserNotLoggedInException, TripNotFoundException, UserNotFoundException, PassengerNotFoundException;

    Optional<PassengerReview> findById(final long tripId);

    PagedContent<PassengerReview> getPassengerReviews(final long userId, int page, int pageSize) throws UserNotFoundException;

    PagedContent<PassengerReview> getPassengerReviewsMadeByUserOnTrip(final long reviewerUserid, final long tripId, final int page, final  int pageSize) throws UserNotFoundException, TripNotFoundException;
    PagedContent<PassengerReview> getPassengerReview(final long reviewedId,final long reviewerId, final  long tripId) throws UserNotFoundException, TripNotFoundException;

}
