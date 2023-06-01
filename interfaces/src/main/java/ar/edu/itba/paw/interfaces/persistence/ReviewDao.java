package ar.edu.itba.paw.interfaces.persistence;


import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {

    Review create(Trip trip, User user, int rating, String review, User receiver);

    double getDriverRating(final User driver);

    double getUserRating(final User user);

    List<Review> findByReceiver(User receiver);

    List<Review> findByDriver(User driver);

    List<Review> findByUser(User user);

    Optional<Review> reviewByTripAndPassanger(Trip trip, Passenger passenger);


}
