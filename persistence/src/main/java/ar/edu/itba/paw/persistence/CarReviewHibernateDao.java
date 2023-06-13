package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarReviewDao;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.models.reviews.CarReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CarReviewHibernateDao implements CarReviewDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarReviewHibernateDao.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public CarReview createCarReview(Trip trip, Passenger reviewer, Car car, int rating, String comment, CarReviewOptions option) {
        LOGGER.debug("Adding new car review for trip with id {} and car with id {}, written by passenger with id {}", trip.getTripId(), car.getCarId(), reviewer.getUserId());
        final CarReview carReview = new CarReview(trip, reviewer.getUser(), car, rating, comment, option);
        em.persist(carReview);
        LOGGER.info("Car review with id {} added to the database", carReview.getReviewId());
        LOGGER.debug("New {}", carReview);
        return carReview;
    }

    @Override
    public double getCarRating(Car car) {
        LOGGER.debug("Looking for the average car rating of the car with id {}", car.getCarId());
        final TypedQuery<Double> avgRatingQuery = em.createQuery("SELECT coalesce(AVG(cr.rating), 0.0) FROM CarReview cr WHERE cr.car = :car", Double.class);
        avgRatingQuery.setParameter("car", car);
        Double result = avgRatingQuery.getSingleResult();
        LOGGER.debug("Average car rating of the car with id {} is {}", car.getCarId(), result);
        return result;
    }

    @Override
    public PagedContent<CarReview> getCarReviews(Car car, int page, int pageSize) {
        LOGGER.debug("Looking for all the car reviews of the car with id {} in page {} with page size {}", car.getCarId(), page, pageSize);
        Query nativeCountQuery = em.createNativeQuery("SELECT COUNT(review_id) FROM car_reviews WHERE car_id = :car_id");
        nativeCountQuery.setParameter("car_id", car.getCarId());
        final int totalCount = ((Number) nativeCountQuery.getSingleResult()).intValue();
        if (totalCount == 0) {
            LOGGER.debug("No car reviews found for the car with id {}", car.getCarId());
            return PagedContent.emptyPagedContent();
        }

        // 1+1 query
        Query nativeQuery = em.createNativeQuery("SELECT review_id FROM car_reviews WHERE car_id = :car_id ORDER BY date DESC");
        nativeQuery.setParameter("car_id", car.getCarId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult(page * pageSize);
        final List<?> maybeReviewIdList = nativeQuery.getResultList();
        final List<Long> reviewIdList = maybeReviewIdList.stream().map(id -> ((Number) id).longValue()).collect(Collectors.toList());

        final TypedQuery<CarReview> carReviewsQuery = em.createQuery("FROM CarReview cr WHERE cr.reviewId IN :reviewIdList", CarReview.class);
        carReviewsQuery.setParameter("reviewIdList", reviewIdList);
        final List<CarReview> result = carReviewsQuery.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return new PagedContent<>(result, page, pageSize, totalCount);
    }

    @Override
    public boolean canReviewCar(Trip trip, Passenger reviewer, Car car) {
        LOGGER.debug("Checking if passenger with id {} can review car with id {} in the trip with id {}", reviewer.getUserId(), car.getCarId(), trip.getTripId());
        final TypedQuery<CarReview> carReviewQuery = em.createQuery("FROM CarReview cr WHERE cr.trip = :trip AND cr.reviewer = :passenger AND cr.car = :car", CarReview.class);
        carReviewQuery.setParameter("trip", trip);
        carReviewQuery.setParameter("passenger", reviewer.getUser());
        carReviewQuery.setParameter("car", car);
        boolean result = carReviewQuery.getResultList().isEmpty();
        LOGGER.debug("Passenger with id {} can{} review car with id {} in the trip with id {}", reviewer.getUserId(), result ? "" : "not", car.getCarId(), trip.getTripId());
        return result;
    }
}
