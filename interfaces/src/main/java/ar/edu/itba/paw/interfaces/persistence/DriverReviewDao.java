package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.DriverReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;

public interface DriverReviewDao {

    DriverReview createDriverReview(final Trip trip, final Passenger reviewer, final User driver, final int rating, final String comment, final DriverReviewOptions option);

    double getDriverRating(final User user);

    List<DriverReview> getDriverReviews(final User user);

    boolean canReviewDriver(final Trip trip, final Passenger reviewer, final User driver);
}
