package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
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
public class CarDaoImpl implements CarDao {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarDaoImpl.class);

    private static final RowMapper<Car> ROW_MAPPER = (resultSet, rowNum) ->
            new Car(resultSet.getLong("car_id"),resultSet.getString("plate"),resultSet.getString("info_car"),
                    new User(resultSet.getLong("user_id"),resultSet.getString("username"),
                            resultSet.getString("surname"),resultSet.getString("email"),
                            resultSet.getString("phone"),resultSet.getString("password"),
                            new City(resultSet.getLong("city_id"),resultSet.getString("name"), resultSet.getLong("province_id")),
                            new Locale(resultSet.getString("mail_locale")),
                            resultSet.getString("user_role"), resultSet.getLong("user_image_id")),
                            resultSet.getLong("image_id"));

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

//    @Autowired
    public CarDaoImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns("car_id")
                .withTableName("cars");
    }

    @Override
    public Car create(final String plate, String infoCar, final User user, long image_id) {
        Map<String,Object> data = new HashMap<>();
        final String savedPlate = plate.toUpperCase();
        data.put("plate",savedPlate);
        data.put("user_id", user.getUserId());
        data.put("info_car", infoCar);
        data.put("image_id", image_id);
        LOGGER.debug("Adding new car with plate '{}' from user with id {} to the database", plate, user.getUserId());
        Number key = jdbcInsert.executeAndReturnKey(data);
        LOGGER.info("Car with plate '{}' added to the database with id {}", plate, key.longValue());
        final Car car = new Car(key.longValue(),plate,infoCar,user, image_id);
        LOGGER.debug("New {}", car);
        return car;
    }

    @Override
    public Optional<Car> findById(long carId) {
        LOGGER.debug("Looking for car with id {} in the database", carId);
        final Optional<Car> result = jdbcTemplate.query("SELECT * FROM cars NATURAL JOIN users NATURAL JOIN cities WHERE car_id = ?",ROW_MAPPER,carId).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public Optional<Car> findByPlateAndUser(String plate, User user) {
        final String plateToSearch = plate.toUpperCase();
        LOGGER.debug("Looking for car with plate '{}' from user with id {} in the database", plate, user.getUserId());
        final Optional<Car> result = jdbcTemplate.query("SELECT * FROM cars NATURAL JOIN users NATURAL JOIN cities WHERE plate = ? and user_id = ?",ROW_MAPPER,plateToSearch, user.getUserId()).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public List<Car> findByUser(User user) {
        LOGGER.debug("Looking for cars from user with id {} in the database", user.getUserId());
        final List<Car> result = jdbcTemplate.query("SELECT * FROM users NATURAL JOIN cars NATURAL JOIN cities WHERE user_id = ?", ROW_MAPPER, user.getUserId());
        LOGGER.debug("Found {} in the database", result);
        return result;
    }
}
