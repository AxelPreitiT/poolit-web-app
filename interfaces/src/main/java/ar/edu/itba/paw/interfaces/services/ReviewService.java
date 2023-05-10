package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;

public interface ReviewService {
    Review createReview(long travelId, Passenger user, int rating, String review);

    double getDriverRating(final User driver);

    List<Review> getDriverReviews(User driver);

    List<Review> getUsersIdReviews(User user);

    List<Long> getTravelsIdReviewedByUser(User user);

    boolean canReview(Passenger passenger);

    boolean haveReview(Trip tripId, Passenger passenger);
}
