package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.DriverReviewDao;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.DriverReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DriverReviewHibernateDao implements DriverReviewDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverReviewHibernateDao.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public DriverReview createDriverReview(Trip trip, Passenger reviewer, User driver, int rating, String comment, DriverReviewOptions option) {
        LOGGER.debug("Adding new driver review for trip with id {} and driver with id {}, written by passenger with id {}", trip.getTripId(), driver.getUserId(), reviewer.getUserId());
        final DriverReview driverReview = new DriverReview(trip, reviewer.getUser(), driver, rating, comment, option);
        em.persist(driverReview);
        LOGGER.info("Driver review with id {} added to the database", driverReview.getReviewId());
        LOGGER.debug("New {}", driverReview);
        return driverReview;
    }

    @Override
    public Optional<DriverReview> findById(final long id){
        LOGGER.debug("Looking for driver review with id {}",id);
        final Optional<DriverReview> result = Optional.ofNullable(em.find(DriverReview.class,id));
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }


    @Override
    public PagedContent<DriverReview> getDriverReviews(User user, int page, int pageSize) {
        LOGGER.debug("Looking for all the driver reviews of the user with id {} in page {} with page size {}", user.getUserId(), page, pageSize);
        if (page < 0 || pageSize <= 0) {
            LOGGER.debug("Invalid page or page size");
            return PagedContent.emptyPagedContent();
        }
        Query nativeCountQuery = em.createNativeQuery("SELECT COUNT(review_id) FROM driver_reviews NATURAL JOIN user_reviews WHERE reviewed_id = :user_id");
        nativeCountQuery.setParameter("user_id", user.getUserId());
        final int totalCount = ((Number) nativeCountQuery.getSingleResult()).intValue();
        if (totalCount == 0) {
            LOGGER.debug("No driver reviews found for the user with id {}", user.getUserId());
            return PagedContent.emptyPagedContent();
        }

        // 1+1 query
        Query nativeQuery = em.createNativeQuery("SELECT review_id FROM driver_reviews NATURAL JOIN user_reviews WHERE reviewed_id = :user_id ORDER BY date DESC");
        nativeQuery.setParameter("user_id", user.getUserId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult(page * pageSize);

        final List<?> maybeReviewIdList = nativeQuery.getResultList();
        if (maybeReviewIdList.isEmpty()) {
            LOGGER.debug("No driver reviews found for the user with id {} in page {} with page size {}", user.getUserId(), page, pageSize);
            return PagedContent.emptyPagedContent();
        }
        final List<Long> reviewIdList = maybeReviewIdList.stream().map(id -> ((Number) id).longValue()).collect(Collectors.toList());

        final TypedQuery<DriverReview> driverReviewsQuery = em.createQuery("FROM DriverReview dr WHERE dr.reviewId IN :reviewIdList ORDER BY date DESC", DriverReview.class);
        driverReviewsQuery.setParameter("reviewIdList", reviewIdList);
        List<DriverReview> result = driverReviewsQuery.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return new PagedContent<>(result, page, pageSize, totalCount);
    }

    @Override
    public PagedContent<DriverReview> getDriverReviewsMadeByUserOnTrip(User reviewer, Trip trip, int page, int pageSize) {
        LOGGER.debug("Looking for all the driver reviews made by user {} for trip {} in page {} with page size {}", reviewer.getUserId(), trip.getTripId(), page, pageSize);
        if (page < 0 || pageSize <= 0) {
            LOGGER.debug("Invalid page or page size");
            return PagedContent.emptyPagedContent();
        }
        Query nativeCountQuery = em.createNativeQuery("SELECT COUNT(review_id) FROM driver_reviews NATURAL JOIN user_reviews WHERE reviewer_id = :reviewer_id AND trip_id = :trip_id");
        nativeCountQuery.setParameter("reviewer_id", reviewer.getUserId());
        nativeCountQuery.setParameter("trip_id", trip.getTripId());
        final int totalCount = ((Number) nativeCountQuery.getSingleResult()).intValue();
        if (totalCount == 0) {
            LOGGER.debug("No driver reviews found made by user {} for trip {}",reviewer.getUserId(), trip.getTripId());
            return PagedContent.emptyPagedContent();
        }

        // 1+1 query
        Query nativeQuery = em.createNativeQuery("SELECT review_id FROM driver_reviews NATURAL JOIN user_reviews WHERE reviewer_id = :reviewer_id AND trip_id = :trip_id ORDER BY date DESC");
        nativeQuery.setParameter("reviewer_id", reviewer.getUserId());
        nativeQuery.setParameter("trip_id", trip.getTripId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult(page * pageSize);

        final List<?> maybeReviewIdList = nativeQuery.getResultList();
        if (maybeReviewIdList.isEmpty()) {
            LOGGER.debug("No driver reviews found made by user {} for trip {} in page {} with page size {}",reviewer.getUserId(), trip.getTripId(), page, pageSize);
            return PagedContent.emptyPagedContent();
        }
        final List<Long> reviewIdList = maybeReviewIdList.stream().map(id -> ((Number) id).longValue()).collect(Collectors.toList());

        final TypedQuery<DriverReview> driverReviewsQuery = em.createQuery("FROM DriverReview dr WHERE dr.reviewId IN :reviewIdList ORDER BY date DESC", DriverReview.class);
        driverReviewsQuery.setParameter("reviewIdList", reviewIdList);
        List<DriverReview> result = driverReviewsQuery.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return new PagedContent<>(result, page, pageSize, totalCount);
    }

    @Override
    public boolean canReviewDriver(Trip trip, Passenger reviewer, User driver) {
        LOGGER.debug("Checking if passenger with id {} can review driver with id {} in the trip with id {}", reviewer.getUserId(), driver.getUserId(), trip.getTripId());
        final TypedQuery<DriverReview> driverReviewQuery = em.createQuery("FROM DriverReview dr WHERE dr.trip = :trip AND dr.reviewer = :reviewer AND dr.reviewed = :driver", DriverReview.class);
        driverReviewQuery.setParameter("trip", trip);
        driverReviewQuery.setParameter("reviewer", reviewer.getUser());
        driverReviewQuery.setParameter("driver", driver);
        boolean result = driverReviewQuery.getResultList().isEmpty();
        LOGGER.debug("User with id {} can{} review the driver with id {} in the trip with id {}", reviewer.getUserId(), result ? "" : " not", driver.getUserId(), trip.getTripId());
        return result;
    }
}
