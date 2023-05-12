package ar.edu.itba.paw.interfaces.persistence;


import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Review create(long TripId, User user, int rating, String review);

    double getRating(final User driver);

    List<Review> findByDriver(User driver);

    List<Review> findReviewsByUser(User user);

    List<Long> findTripIdByUser(User user);

    Optional<Review> reviewByTripAndPassanger(Trip trip, Passenger passenger);
}
