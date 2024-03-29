package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PassengerReviewDao;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;
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
public class PassengerReviewHibernateDao implements PassengerReviewDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerReviewHibernateDao.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public PassengerReview createPassengerReview(Trip trip, User reviewer, Passenger reviewed, int rating, String comment, PassengerReviewOptions option) {
        LOGGER.debug("Adding new passenger review to the trip with id {}, written by the user with id {}, about the passenger with id {}", trip.getTripId(), reviewer.getUserId(), reviewed.getUserId());
        final PassengerReview passengerReview = new PassengerReview(trip, reviewer, reviewed.getUser(), rating, comment, option);
        em.persist(passengerReview);
        LOGGER.info("Passenger review with id {} added to the database", passengerReview.getReviewId());
        LOGGER.debug("New {}", passengerReview);
        return passengerReview;
    }

    @Override
    public Optional<PassengerReview> findById(long reviewId) {
        LOGGER.debug("Looking for passenger review with id {}",reviewId);
        final Optional<PassengerReview> result = Optional.ofNullable(em.find(PassengerReview.class,reviewId));
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }


    @Override
    public PagedContent<PassengerReview> getPassengerReviews(User user, int page, int pageSize) {
        LOGGER.debug("Looking for passenger reviews of the user with id {} in page {} with page size {}", user.getUserId(), page, pageSize);
        if(page < 0 || pageSize <= 0) {
            LOGGER.debug("Invalid page or page size");
            return PagedContent.emptyPagedContent();
        }
        Query nativeCountQuery = em.createNativeQuery("SELECT COUNT(review_id) FROM passenger_reviews NATURAL JOIN user_reviews WHERE reviewed_id = :user_id");
        nativeCountQuery.setParameter("user_id", user.getUserId());
        final int totalCount = ((Number) nativeCountQuery.getSingleResult()).intValue();
        if(totalCount == 0) {
            LOGGER.debug("No passenger reviews found for the user with id {}", user.getUserId());
            return PagedContent.emptyPagedContent();
        }
        // 1+1 query
        Query nativeQuery = em.createNativeQuery("SELECT review_id FROM passenger_reviews NATURAL JOIN user_reviews WHERE reviewed_id = :user_id ORDER BY date DESC");
        nativeQuery.setParameter("user_id", user.getUserId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult(page * pageSize);

        final List<?> maybeReviewIdList = nativeQuery.getResultList();
        if(maybeReviewIdList.isEmpty()) {
            LOGGER.debug("No passenger reviews found for the user with id {} in page {} with page size {}", user.getUserId(), page, pageSize);
            return PagedContent.emptyPagedContent();
        }
        final List<Long> reviewIdList = maybeReviewIdList.stream().map(id -> ((Number) id).longValue()).collect(Collectors.toList());

        final TypedQuery<PassengerReview> reviewsQuery = em.createQuery("FROM PassengerReview pr WHERE pr.reviewId IN :reviewIdList ORDER BY date DESC", PassengerReview.class);
        reviewsQuery.setParameter("reviewIdList", reviewIdList);
        List<PassengerReview> result = reviewsQuery.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return new PagedContent<>(result, page, pageSize, totalCount);
    }

    @Override
    public PagedContent<PassengerReview> getPassengerReviewsMadeByUserOnTrip(final User reviewer, final Trip trip, final int page, final int pageSize){
        LOGGER.debug("Looking for passenger reviews made by user {} on trip {} in page {} with page size {}", reviewer.getUserId(), trip.getTripId(), page, pageSize);
        if(page < 0 || pageSize <= 0) {
            LOGGER.debug("Invalid page or page size");
            return PagedContent.emptyPagedContent();
        }
        Query nativeCountQuery = em.createNativeQuery("SELECT COUNT(review_id) FROM passenger_reviews NATURAL JOIN user_reviews WHERE reviewer_id = :reviewer_id AND trip_id = :trip_id");
        nativeCountQuery.setParameter("reviewer_id", reviewer.getUserId());
        nativeCountQuery.setParameter("trip_id",trip.getTripId());
        final int totalCount = ((Number) nativeCountQuery.getSingleResult()).intValue();
        if(totalCount == 0) {
            LOGGER.debug("No passenger reviews found made by user {} on trip {}", reviewer.getUserId(), trip.getTripId());
            return PagedContent.emptyPagedContent();
        }
        // 1+1 query
        Query nativeQuery = em.createNativeQuery("SELECT review_id FROM passenger_reviews NATURAL JOIN user_reviews WHERE reviewer_id = :reviewer_id AND trip_id = :trip_id ORDER BY date DESC");
        nativeQuery.setParameter("reviewer_id", reviewer.getUserId());
        nativeQuery.setParameter("trip_id",trip.getTripId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult(page * pageSize);

        final List<?> maybeReviewIdList = nativeQuery.getResultList();
        if(maybeReviewIdList.isEmpty()) {
            LOGGER.debug("No passenger reviews found made by user {} on trip {} in page {} with page size {}", reviewer.getUserId(), trip.getTripId(), page, pageSize);
            return PagedContent.emptyPagedContent();
        }
        final List<Long> reviewIdList = maybeReviewIdList.stream().map(id -> ((Number) id).longValue()).collect(Collectors.toList());

        final TypedQuery<PassengerReview> reviewsQuery = em.createQuery("FROM PassengerReview pr WHERE pr.reviewId IN :reviewIdList ORDER BY date DESC", PassengerReview.class);
        reviewsQuery.setParameter("reviewIdList", reviewIdList);
        List<PassengerReview> result = reviewsQuery.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return new PagedContent<>(result, page, pageSize, totalCount);
    }

    @Override
    public Optional<PassengerReview> getPassengerReview(final User reviewed, final User reviewer, final Trip trip){
        LOGGER.debug("Looking for passenger review from user with id {} to passenger with id {} in the trip with id {}", reviewer.getUserId(), reviewed.getUserId(), trip.getTripId());
        final TypedQuery<PassengerReview> passengerReviewQuery = em.createQuery("FROM PassengerReview pr WHERE pr.trip = :trip AND pr.reviewer = :reviewer AND pr.reviewed = :reviewed", PassengerReview.class);
        passengerReviewQuery.setParameter("trip", trip);
        passengerReviewQuery.setParameter("reviewer", reviewer);
        passengerReviewQuery.setParameter("reviewed", reviewed);
        Optional<PassengerReview> result = passengerReviewQuery.getResultList().stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public boolean canReviewPassenger(Trip trip, User reviewer, Passenger reviewed) {
        LOGGER.debug("Checking if the user with id {} can review the passenger with id {} in the trip with id {}", reviewer.getUserId(), reviewed.getUserId(), trip.getTripId());
        return !getPassengerReview(reviewed.getUser(),reviewer,trip).isPresent();
    }
}
