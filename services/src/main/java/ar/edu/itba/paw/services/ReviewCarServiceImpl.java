package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.ReviewCarDao;
import ar.edu.itba.paw.interfaces.services.ReviewCarService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.ReviewCar;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewCarServiceImpl implements ReviewCarService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewCarDao reviewCarDao;

    @Autowired
    public ReviewCarServiceImpl(final ReviewCarDao reviewCarDao) {
        this.reviewCarDao = reviewCarDao;
    }

    @Override
    public ReviewCar createReviewCar(User user, int rating, String review, Car car) {
        return reviewCarDao.createReviewCar(user, rating, review, car);
    }

    @Transactional
    @Override
    public double getCarRating(Car car) {
        return reviewCarDao.getCarRating(car);
    }

    @Override
    public List<ReviewCar> getReviews(Car car) {
        return reviewCarDao.getReviews(car);
    }
}
