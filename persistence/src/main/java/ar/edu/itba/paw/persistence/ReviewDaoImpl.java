package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ReviewDao;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Review;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    private static final RowMapper<Review> ROW_MAPPER = (resultSet, rowNum) ->
            new Review(
                    resultSet.getLong("review_id"), resultSet.getLong("trip_id"),
                    new User(resultSet.getLong("user_id"),resultSet.getString("username"),
                            resultSet.getString("surname"),resultSet.getString("email"),
                            resultSet.getString("phone"),resultSet.getString("password"),
                            resultSet.getTimestamp("birthdate").toLocalDateTime(),
                            new City(resultSet.getLong("city_id"),resultSet.getString("name"), resultSet.getLong("province_id")),
                            resultSet.getString("user_role"), resultSet.getLong("user_image_id")),
                    resultSet.getInt("rating"), resultSet.getString("review"));

    private static final RowMapper<Long> TRIPS_ROW_MAPPER = (resultSet, rowNum)-> resultSet.getLong("trip_id");

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
    public Review create(long travelId, User user, int rating, String review) {
        Map<String,Object> data = new HashMap<>();
        data.put("trip_id", travelId);
        data.put("user_id", user.getUserId());
        data.put("rating", rating);
        data.put("review", review);
        Number key = jdbcInsert.executeAndReturnKey(data);
        return new Review(key.longValue(), travelId, user, rating, review);
    }

    @Override
    public double getRating(User driver) {
        return 0;
    }

    @Override
    public List<Review> findByDriver(User driver) {
        return null;
    }

    @Override
    public List<Review> findReviewsByUser(User user) {
        return jdbcTemplate.query("SELECT * FROM reviews WHERE user_id = ?", ROW_MAPPER, user.getUserId());
    }

    @Override
    public List<Long> findTravelIdByUser(User user) {
        return jdbcTemplate.query("SELECT trip_id FROM reviews WHERE user_id = ?", TRIPS_ROW_MAPPER, user.getUserId());
    }
}
