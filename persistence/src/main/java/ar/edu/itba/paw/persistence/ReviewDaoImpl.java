package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ReviewDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

//@Repository
/*
public class ReviewDaoImpl implements ReviewDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewDaoImpl.class);

    private static final RowMapper<Review> ROW_MAPPER = (resultSet, rowNum) ->
            new Review(
                    resultSet.getLong("review_id"), resultSet.getLong("trip_id"),
                    new User(resultSet.getLong("user_id"),resultSet.getString("username"),
                            resultSet.getString("surname"),resultSet.getString("email"),
                            resultSet.getString("phone"),resultSet.getString("password"),
                            new City(resultSet.getLong("city_id"),resultSet.getString("name"), resultSet.getLong("province_id")),
                            new Locale(resultSet.getString("mail_locale")),
                            resultSet.getString("user_role"), resultSet.getLong("user_image_id")),
                    resultSet.getInt("rating"), resultSet.getString("review"));

    private static final RowMapper<Long> TRIPS_ROW_MAPPER = (resultSet, rowNum)-> resultSet.getLong("trip_id");

    private static final RowMapper<Double> RATING_ROW_MAPPER = (resultSet, rowNum)-> resultSet.getDouble("avg");

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    //@Autowired
    public ReviewDaoImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns("review_id")
                .withTableName("reviews");
    }


    @Override
    public Review create(long TripId, User user, int rating, String review) {
        Map<String,Object> data = new HashMap<>();
        data.put("trip_id", TripId);
        data.put("user_id", user.getUserId());
        data.put("rating", rating);
        data.put("review", review);
        LOGGER.debug("Adding new review to the trip with id {} and written by user with id {} to the database", TripId, user.getUserId());
        Number key = jdbcInsert.executeAndReturnKey(data);
        LOGGER.info("Review with id {} added to the database", key.longValue());
        final Review reviewObj = new Review(key.longValue(), TripId, user, rating, review);
        LOGGER.debug("New {}", reviewObj);
        return reviewObj;
    }

    @Override
    public double getRating(User driver) {
        LOGGER.debug("Looking for rating of driver with id {} in the database", driver.getUserId());
        final double result = jdbcTemplate.query("SELECT avg(rating) FROM reviews NATURAL JOIN trips_cars_drivers as trips(trip_id, driver_id, car_id) WHERE driver_id = ?", RATING_ROW_MAPPER, driver.getUserId()).stream().findFirst().orElse(0.0);
        LOGGER.debug("Found {} in the database", result);
        return result;
    }

    @Override
    public List<Review> findByDriver(User driver) {
        LOGGER.debug("Looking for reviews of driver with id {} in the database", driver.getUserId());
        final List<Review> result = jdbcTemplate.query("SELECT city_id, user_id, review_id, trip_id, rating, review, username, surname, email, phone, password, user_role, mail_locale, user_image_id, name, province_id FROM reviews NATURAL JOIN trips_cars_drivers as trips(trip_id, driver_id, car_id) NATURAL JOIN users NATURAL JOIN cities where driver_id = ?", ROW_MAPPER, driver.getUserId());
        LOGGER.debug("Found {} in the database", result);
        return result;
    }

    @Override
    public List<Review> findReviewsByUser(User user) {
        LOGGER.debug("Looking for reviews written by user with id {} in the database", user.getUserId());
        final List<Review> result = jdbcTemplate.query("SELECT * FROM reviews NATURAL JOIN users NATURAL JOIN cities WHERE user_id = ?", ROW_MAPPER, user.getUserId());
        LOGGER.debug("Found {} in the database", result);
        return result;
    }

    @Override
    public List<Long> findTripIdByUser(User user) {
        LOGGER.debug("Looking for trips that were reviewed by user with id {} in the database", user.getUserId());
        final List<Long> result = jdbcTemplate.query("SELECT trip_id FROM reviews WHERE user_id = ?", TRIPS_ROW_MAPPER, user.getUserId());
        LOGGER.debug("Found {} in the database", result);
        return result;
    }

    @Override
    public Optional<Review> reviewByTripAndPassanger(Trip trip, Passenger passenger){
        LOGGER.debug("Looking for review of trip {} written by passenger with id {} in the database", trip, passenger.getUserId());
        final Optional<Review> result = jdbcTemplate.query("SELECT * FROM reviews NATURAL JOIN users NATURAL JOIN cities WHERE trip_id = ? AND user_id = ?", ROW_MAPPER, trip.getTripId(), passenger.getUserId()).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }
}
 */