package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.models.reviews.CarReviewOptions;
import ar.edu.itba.paw.models.reviews.ItemReview;
import ar.edu.itba.paw.models.trips.Trip;

import java.util.List;

public interface CarReviewService {

    CarReview createCarReview(final long tripId, final long carId, final int rating, final String comment, CarReviewOptions option) throws TripNotFoundException, UserNotFoundException, CarNotFoundException, PassengerNotFoundException;

    double getCarsRating(final long carId) throws CarNotFoundException;

    PagedContent<CarReview> getCarReviews(final long carId, int page, int pageSize) throws CarNotFoundException;

    boolean canReviewCar(final Trip trip, final Passenger reviewer, final Car car);

    ItemReview<Car> getCarReviewState(final long tripId) throws TripNotFoundException, UserNotFoundException, CarNotFoundException;
}
