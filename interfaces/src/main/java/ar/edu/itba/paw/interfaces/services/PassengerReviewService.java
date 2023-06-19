package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.ItemReview;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;

public interface PassengerReviewService {

    PassengerReview createPassengerReview(final long tripId, final long reviewedId, final int rating, final String comment, final PassengerReviewOptions option);

    double getPassengerRating(final long userId);

    double getPassengerRatingOwnUser();

    PagedContent<PassengerReview> getPassengerReviews(final long userId, int page, int pageSize);

    PagedContent<PassengerReview> getPassengerReviewsOwnUser( int page, int pageSize);

    boolean canReviewPassenger(final Trip trip, final Passenger reviewed);

    //TODO Cambiar passengers a datos para obtenerlos aca?
    List<ItemReview<Passenger>> getPassengersReviewState(final long tripId, final List<Passenger> passengers);
}
