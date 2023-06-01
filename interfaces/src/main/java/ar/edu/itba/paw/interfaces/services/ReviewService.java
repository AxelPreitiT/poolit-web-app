package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;

public interface ReviewService {
    Review createReview(Trip trip, Passenger user, int rating, String review, User receiver);

    double getDriverRating(final User driver);

    double getUserRating(final User user);

    List<Review> getReceiverReviews(User receiver);

    List<Review> getDriverReviews(User driver);

    List<Review> getUserReviews(User user);

    boolean canReview(Passenger passenger);

    boolean haveReview(Trip tripId, Passenger passenger);
}
