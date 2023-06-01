package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.ReviewCar;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface ReviewCarDao {

    ReviewCar createReviewCar(User user, int rating, String review, Car car);

    double getCarRating(final Car car);

    List<ReviewCar> getReviews(final Car car);
}
