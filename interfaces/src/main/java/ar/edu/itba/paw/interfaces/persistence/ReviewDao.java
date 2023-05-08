package ar.edu.itba.paw.interfaces.persistence;


import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;

import java.util.List;

public interface ReviewDao {

    Review create(long travelId, User user, int rating, String review);

    double getRating(final User driver);

    List<Review> findByDriver(User driver);

    List<Review> findByUser(User user);
}
