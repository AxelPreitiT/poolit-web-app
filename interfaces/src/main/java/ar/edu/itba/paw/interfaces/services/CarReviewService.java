package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.models.reviews.CarReviewOptions;

import java.util.Optional;

public interface CarReviewService {

    CarReview createCarReview(final long tripId, final long carId, final int rating, final String comment, CarReviewOptions option) throws TripNotFoundException, UserNotFoundException, CarNotFoundException, PassengerNotFoundException;

    PagedContent<CarReview> getCarReviews(final long carId, int page, int pageSize) throws CarNotFoundException;

    PagedContent<CarReview> getCarReviewsMadeByUserOnTrip(final long carId, final long userId, final long tripId, final int page, final int pageSize) throws CarNotFoundException, UserNotFoundException, TripNotFoundException;

    Optional<CarReview> findById(final long reviewId);
}
