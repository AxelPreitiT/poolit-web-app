package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ReviewCarDao;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.ReviewCar;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ReviewCarHibernateDao implements ReviewCarDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReviewHibernateDao.class);

    @PersistenceContext
    private EntityManager em;


    @Override
    public ReviewCar createReviewCar(User user, int rating, String review, Car car) {
        final ReviewCar reviewModel= new ReviewCar(user, rating, review, car);
        em.persist(reviewModel);
        return reviewModel;
    }

    @Override
    public double getCarRating(Car car) {
        Query avgQuery = em.createNativeQuery("SELECT coalesce(AVG(rating),0) as avg FROM car_reviews WHERE car_id = :CarId");
        avgQuery.setParameter("CarId", car.getCarId());
        return ((List<Object>)avgQuery.getResultList()).stream().map(elem -> ((Number) elem).doubleValue()).findFirst().orElse(0.0);
    }

    @Override
    public List<ReviewCar> getReviews(Car car) {
        TypedQuery<ReviewCar> listQuery = em.createQuery("from ReviewCar where car.carId = :carId", ReviewCar.class);
        listQuery.setParameter("carId", car.getCarId());
        return listQuery.getResultList();
    }
}
