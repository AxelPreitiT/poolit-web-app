package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Transactional
    @Override
    public Review createReview(long tripId, Passenger passenger, int rating, String review) {
        if(!canReview(passenger)){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review trip with id {}, but it's not finished yet", passenger.getUserId(), tripId, e);
            throw e;
        }
        return reviewDao.create(tripId, passenger, rating, review);
    }

    @Override
    public double getDriverRating(User driver) {
        return reviewDao.getRating(driver);
    }

    @Override
    public List<Review> getDriverReviews(User driver) {
        return reviewDao.findByDriver(driver);
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
