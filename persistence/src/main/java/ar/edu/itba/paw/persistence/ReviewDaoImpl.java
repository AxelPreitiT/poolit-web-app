package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ReviewDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class ReviewDaoImpl implements ReviewDao {

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

    @Autowired
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
        Number key = jdbcInsert.executeAndReturnKey(data);
        return new Review(key.longValue(), TripId, user, rating, review);
    }

    @Override
    public double getRating(User driver) {
        return jdbcTemplate.query("SELECT avg(rating) FROM reviews NATURAL JOIN trips_cars_drivers as trips(trip_id, driver_id, car_id) WHERE driver_id = ?", RATING_ROW_MAPPER, driver.getUserId()).stream().findFirst().orElse(0.0);
    }

    @Override
    public List<Review> findByDriver(User driver) {
        return jdbcTemplate.query("SELECT city_id, user_id, review_id, trip_id, rating, review, username, surname, email, phone, password, user_role, user_image_id, name, province_id FROM reviews NATURAL JOIN trips_cars_drivers as trips(trip_id, driver_id, car_id) NATURAL JOIN users NATURAL JOIN cities where driver_id = ?", ROW_MAPPER, driver.getUserId());
    }

    @Override
    public List<Review> findReviewsByUser(User user) {
        return jdbcTemplate.query("SELECT * FROM reviews NATURAL JOIN users NATURAL JOIN cities WHERE user_id = ?", ROW_MAPPER, user.getUserId());
    }

    @Override
    public List<Long> findTripIdByUser(User user) {
        return jdbcTemplate.query("SELECT trip_id FROM reviews WHERE user_id = ?", TRIPS_ROW_MAPPER, user.getUserId());
    }

    @Override
    public Optional<Review> reviewByTripAndPassanger(Trip trip, Passenger passenger){
        return jdbcTemplate.query("SELECT * FROM reviews NATURAL JOIN users NATURAL JOIN cities WHERE trip_id = ? AND user_id = ?", ROW_MAPPER, trip.getTripId(), passenger.getUserId()).stream().findFirst();
    }
}
