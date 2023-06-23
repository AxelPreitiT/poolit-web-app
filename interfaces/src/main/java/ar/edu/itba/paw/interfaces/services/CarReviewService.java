package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.models.reviews.CarReviewOptions;
import ar.edu.itba.paw.models.reviews.ItemReview;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;

public interface CarReviewService {

    CarReview createCarReview(final long tripId, final long carId, final int rating, final String comment, CarReviewOptions option);

    double getCarsRating(final long carId);

    PagedContent<CarReview> getCarReviews(final long carId, int page, int pageSize);

    boolean canReviewCar(final Trip trip, final Passenger reviewer, final Car car);

    ItemReview<Car> getCarReviewState(final long tripId);
}
