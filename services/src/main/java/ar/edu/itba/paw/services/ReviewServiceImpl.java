package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ReviewDao;
import ar.edu.itba.paw.interfaces.services.ReviewService;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewServiceImpl(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    @Override
    public Review createReview(long travelId, User user, int rating, String review) {
        return reviewDao.create(travelId, user, rating, review);
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
    public List<Long> getTravelsIdReviewedByUser(User user) {
        return reviewDao.findTravelIdByUser(user);
    }
}
