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
    public Review createReview(Trip trip, Passenger passenger, int rating, String review, User receiver) {
        if(!canReview(passenger)){
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("Passenger with id {} tried to review trip with id {}, but it's not finished yet", passenger.getUserId(), trip.getTripId(), e);
            throw e;
        }
        return reviewDao.create(trip, passenger.getUser(), rating, review, receiver);
    }

    @Override
    public double getDriverRating(User driver) {
        return reviewDao.getDriverRating(driver);
    }

    @Override
    public double getUserRating(User user) {
        return reviewDao.getUserRating(user);
    }

    @Override
    public List<Review> getReceiverReviews(User receiver) {
        return reviewDao.findByReceiver(receiver);
    }

    @Override
    public List<Review> getDriverReviews(User driver) {
        return reviewDao.findByDriver(driver);
    }

    @Override
    public List<Review> getUserReviews(User user) {
        return reviewDao.findByUser(user);
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
