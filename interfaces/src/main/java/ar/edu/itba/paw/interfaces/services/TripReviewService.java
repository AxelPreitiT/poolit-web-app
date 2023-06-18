package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.reviews.TripReviewCollection;

public interface TripReviewService {
    TripReviewCollection getReviewsForDriver(final long tripId) throws TripNotFoundException;

    TripReviewCollection getReviewsForPassenger(final long tripId, final long userId) throws TripNotFoundException, UserNotFoundException;
}
