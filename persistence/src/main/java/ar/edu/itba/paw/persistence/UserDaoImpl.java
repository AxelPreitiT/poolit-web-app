package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    protected static final RowMapper<User> ROW_MAPPER = (resultSet, rowNum) ->
            new User(resultSet.getLong("user_id"),resultSet.getString("username"),
                    resultSet.getString("surname"),resultSet.getString("email"),
                    resultSet.getString("phone"),resultSet.getString("password"),
                    resultSet.getTimestamp("birthdate").toLocalDateTime(),
                    new City(resultSet.getLong("city_id"),resultSet.getString("name"), resultSet.getLong("province_id")),
                    resultSet.getString("user_role"));

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public UserDaoImpl(final DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns("user_id")
                .withTableName("users");
    }
    @Override
    public User create(final String username, final String surname, final String email,
                       final String phone, final String password, final LocalDateTime birthdate, final City bornCity, String role) {
        Map<String,Object> data = new HashMap<>();
        String savedEmail = email.toLowerCase().replaceAll("\\s","");
        String savedPhone = phone.replaceAll("\\s","");
        String savedPassword = password.replaceAll("\\s","");
        data.put("username",username);
        data.put("surname",surname);
        data.put("email",savedEmail);
        data.put("phone",savedPhone);
        data.put("password",savedPassword);
        data.put("birthdate",birthdate);
        data.put("city_id",bornCity.getId());
        data.put("user_role",role);
        Number key = jdbcInsert.executeAndReturnKey(data);
        return new User(key.longValue(),username,surname, savedEmail, savedPhone, savedPassword, birthdate, bornCity, role);
    }

    @Override
    public Optional<User> findById(long userId){
        return jdbcTemplate.query("SELECT * FROM users NATURAL JOIN cities WHERE user_id = ?",ROW_MAPPER,userId).stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email){
        String searchEmail = email.toLowerCase().replaceAll("\\s","");
        return jdbcTemplate.query("SELECT * FROM users NATURAL JOIN cities WHERE email = ?",ROW_MAPPER,searchEmail).stream().findFirst();
    }

    @Override
    public void changeRole(long userId, String role) {
        jdbcTemplate.update("UPDATE users SET user_role = ? WHERE user_id = ?", role ,userId);
    }
}
