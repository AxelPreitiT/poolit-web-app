package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public Review createReview(long tripId, Passenger passenger, int rating, String review) {
        if(!canReview(passenger)){
            throw new IllegalStateException();
        }
        return reviewDao.create(tripId, passenger, rating, review);
    }

    @Override
    public double getDriverRating(User driver) {
        return reviewDao.getRating(driver);
    }

    @Override
    public List<Review> getDriverReviews(User driver) {
        return null;
    }

    @Override
    public List<Review> getUsersIdReviews(User user) {
        return reviewDao.findReviewsByUser(user);
    }

    @Override
    public List<Long> getTripIdReviewedByUser(User user) {
        return reviewDao.findTripIdByUser(user);
    }

    @Override
    public boolean canReview(Passenger passenger) {
        return passenger.getEndDateTime().isBefore(LocalDateTime.now());
    }

    @Override
    public boolean haveReview(Trip trip, Passenger passenger){
        return reviewDao.reviewByTripAndPassanger(trip, passenger).isPresent();
    }
}
