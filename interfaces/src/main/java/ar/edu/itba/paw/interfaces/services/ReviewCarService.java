package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.ReviewCar;
import ar.edu.itba.paw.models.User;

import java.util.List;


public interface ReviewCarService {

    ReviewCar createReviewCar(User user, int rating, String review, Car car);

    double getCarRating(final Car car);

    List<ReviewCar> getReviews(final Car car);
}
