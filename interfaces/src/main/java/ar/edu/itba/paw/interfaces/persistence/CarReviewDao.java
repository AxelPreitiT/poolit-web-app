package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.models.reviews.CarReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;
import java.util.Optional;

public interface CarReviewDao {

    CarReview createCarReview(final Trip trip, final Passenger reviewer, final Car car, final int rating, final String comment, CarReviewOptions option);

//    double getCarRating(final Car car);

    PagedContent<CarReview> getCarReviews(final Car car, int page, int pageSize);

    PagedContent<CarReview> getCarReviewsMadeByUserOnTrip(Car car, User user, Trip trip, int page, int pageSize);

    boolean canReviewCar(final Trip trip, final Passenger reviewer, final Car car);

    Optional<CarReview> findById(final long reviewId);
}
