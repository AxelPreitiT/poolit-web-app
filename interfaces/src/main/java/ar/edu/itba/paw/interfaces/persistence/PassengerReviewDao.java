package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;

public interface PassengerReviewDao {

    PassengerReview createPassengerReview(final Trip trip, final User reviewer, final Passenger reviewed, final int rating, final String comment, final PassengerReviewOptions option);

    double getPassengerRating(final User user);

    PagedContent<PassengerReview> getPassengerReviews(final User user, int page, int pageSize);

    boolean canReviewPassenger(final Trip trip, final User reviewer, final Passenger reviewed);
}