package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class CarDaoImpl implements CarDao {

    private static final RowMapper<Car> ROW_MAPPER = (resultSet, rowNum) -> new Car(resultSet.getLong("car_id"),resultSet.getString("plate"),resultSet.getString("info_car"),new User(resultSet.getLong("user_id"),resultSet.getString("email"),resultSet.getString("phone")));

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public CarDaoImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns("car_id")
                .withTableName("cars");
    }

    @Override
    public Car create(final String plate, String infoCar, final User user) {
        Map<String,Object> data = new HashMap<>();
        data.put("plate",plate);
        data.put("user_id", user.getUserId());
        data.put("info_car", infoCar);
        Number key = jdbcInsert.executeAndReturnKey(data);
        return new Car(key.longValue(),plate,infoCar,user);
    }

    @Override
    public Optional<Car> findById(long carId) {
        return jdbcTemplate.query("SELECT * FROM cars NATURAL JOIN users WHERE car_id = ?",ROW_MAPPER,carId).stream().findFirst();
    }

    @Override
    public Optional<Car> findByPlateAndUser(String plate, User user) {
        return jdbcTemplate.query("SELECT * FROM cars NATURAL JOIN users WHERE plate = ? and user_id = ?",ROW_MAPPER,plate, user.getUserId()).stream().findFirst();
    }

    @Override
    public Optional<List<Car>> findByUser(User user) {
        return Optional.of(new ArrayList<>(jdbcTemplate.query("SELECT * FROM users NATURAL JOIN cars WHERE user_id = ?", ROW_MAPPER, user.getUserId())));
    }
}
