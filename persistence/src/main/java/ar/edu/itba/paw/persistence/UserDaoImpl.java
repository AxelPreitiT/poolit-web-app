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
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {


    protected static final RowMapper<User> ROW_MAPPER = (resultSet, rowNum) ->{
            LocalDateTime localDateTime = resultSet.getTimestamp("birthdate")==null?LocalDateTime.now():resultSet.getTimestamp("birthdate").toLocalDateTime();
            return new User(resultSet.getLong("user_id"), resultSet.getString("name"),
                    resultSet.getString("surname"), resultSet.getString("email"),
                    resultSet.getString("phone"), resultSet.getString("password"),
                    localDateTime,
                    new City(resultSet.getLong("city_id"), resultSet.getString("name"), resultSet.getLong("province_id")),
                    resultSet.getString("user_role"), resultSet.getLong("user_image_id"));};

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public UserDaoImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns("user_id")
                .withTableName("users");
    }

    @Override
    public User create(final String username, final String surname, final String email,
                       final String phone, final String password, final LocalDateTime birthdate, final City bornCity, String role, long user_image_id) {
        Map<String, Object> data = new HashMap<>();
        String savedEmail = email.toLowerCase().replaceAll("\\s", "");
        String savedPhone = phone.replaceAll("\\s", "");
        String savedPassword = password.replaceAll("\\s", "");
        data.put("username", username);
        data.put("surname", surname);
        data.put("email", savedEmail);
        data.put("phone", savedPhone);
        data.put("password", savedPassword);
        data.put("birthdate", birthdate);
        data.put("city_id", bornCity.getId());
        data.put("user_role", role);
        data.put("user_image_id", user_image_id);
        Number key = 0;
        try {
            key = jdbcInsert.executeAndReturnKey(data);
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains("users_email_key")) {
                if (findByEmail(email).get().getPassword() != null) {
                    throw new EmailAlreadyExistsException();
                } else {
                    updateProfile(username, surname, email, password, birthdate, bornCity, role, user_image_id);
                }
            }
        }
        return new User(key.longValue(), username, surname, savedEmail, savedPhone, savedPassword, birthdate, bornCity, role, user_image_id);
    }

    @Override
    public Optional<User> findById(long userId) {
        return jdbcTemplate.query("SELECT * FROM users LEFT OUTER JOIN cities ON cities.city_id = users.city_id WHERE user_id = ?", ROW_MAPPER, userId).stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String searchEmail = email.toLowerCase().replaceAll("\\s", "");
        return jdbcTemplate.query("SELECT * FROM users LEFT OUTER JOIN cities ON cities.city_id = users.city_id WHERE email = ?", ROW_MAPPER, searchEmail).stream().findFirst();
    }

    @Override
    public void changeRole(long userId, String role) {
        jdbcTemplate.update("UPDATE users SET user_role = ? WHERE user_id = ?", role, userId);
    }

    public void updateProfile(final String username, final String surname, final String email,
                              final String password, final LocalDateTime birthdate, final City bornCity, String role, long user_image_id){
        jdbcTemplate.update("UPDATE users SET username = ?, surname = ?, password = ?, birthdate = ?, city_id = ?, user_role = ?, user_image_id = ?  WHERE email = ?",
                username, surname, password, birthdate, bornCity.getId(), role, user_image_id, email);
    }
}
