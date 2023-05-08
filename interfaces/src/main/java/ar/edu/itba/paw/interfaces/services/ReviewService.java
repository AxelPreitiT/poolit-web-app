package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface ReviewService {
    Review createReview(long travelId, User user, int rating, String review);

    double getDriverRating(final User driver);

    List<Review> getDriverReviews(User driver);

    List<Review> getUsersIdReviews(User user);
}
