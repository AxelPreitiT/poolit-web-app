package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    protected static final RowMapper<User> ROW_MAPPER = (resultSet, rowNum) -> new User(resultSet.getLong("user_id"), resultSet.getString("username"),
            resultSet.getString("surname"), resultSet.getString("email"),
            resultSet.getString("phone"), resultSet.getString("password"),
            new City(resultSet.getLong("city_id"), resultSet.getString("name"), resultSet.getLong("province_id")),
            new Locale(resultSet.getString("mail_locale")),
            resultSet.getString("user_role"), resultSet.getLong("user_image_id"));

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
                       final String phone, final String password, final City bornCity, final Locale mailLocale, final String role, long user_image_id) {
        Map<String, Object> data = new HashMap<>();
        String savedEmail = email.toLowerCase().replaceAll("\\s", "");
        String savedPhone = phone.replaceAll("\\s", "");
        String savedPassword = password.replaceAll("\\s", "");
        data.put("username", username);
        data.put("surname", surname);
        data.put("email", savedEmail);
        data.put("phone", savedPhone);
        data.put("password", savedPassword);
        data.put("city_id", bornCity.getId());
        data.put("mail_locale", mailLocale.toString());
        data.put("user_role", role);
        data.put("user_image_id", user_image_id);
        LOGGER.debug("Adding new user with email '{}' to the database", savedEmail);
        Number key = 0;
        try {
            key = jdbcInsert.executeAndReturnKey(data);
            LOGGER.info("User with email '{}' added to the database with id {}", savedEmail, key.longValue());
        } catch (DuplicateKeyException e) {
            if (e.getMessage().contains("users_email_key")) {
                LOGGER.debug("Email '{}' already exists in the database", savedEmail);
                if (findByEmail(email).get().getPassword() != null) {
                    final EmailAlreadyExistsException exception = new EmailAlreadyExistsException();
                    LOGGER.error("Email '{}' already exists in the database and has already registered", savedEmail, exception);
                    throw exception;
                } else {
                    LOGGER.debug("Email '{}' already exists in the database but has not registered yet, updating user", savedEmail);
                    updateProfile(username, surname, email, password, bornCity, mailLocale.toString(), role, user_image_id);
                    LOGGER.info("User with email '{}' updated in the database", savedEmail);
                }
            }
        }
        final User user = new User(key.longValue(), username, surname, savedEmail, savedPhone, savedPassword, bornCity, mailLocale, role, user_image_id);
        LOGGER.debug("New {}", user);
        return user;
    }

    @Override
    public Optional<User> findById(long userId) {
        LOGGER.debug("Looking for user with id {} in the database", userId);
        final Optional<User> result = jdbcTemplate.query("SELECT * FROM users LEFT OUTER JOIN cities ON cities.city_id = users.city_id WHERE user_id = ?", ROW_MAPPER, userId).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        LOGGER.debug("Looking for user with email '{}' in the database", email);
        String searchEmail = email.toLowerCase().replaceAll("\\s", "");
        final Optional<User> result = jdbcTemplate.query("SELECT * FROM users LEFT OUTER JOIN cities ON cities.city_id = users.city_id WHERE email = ?", ROW_MAPPER, searchEmail).stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public void changeRole(long userId, String role) {
        LOGGER.debug("Changing role of user with id {} to '{}'", userId, role);
        jdbcTemplate.update("UPDATE users SET user_role = ? WHERE user_id = ?", role, userId);
    }

    public void updateProfile(final String username, final String surname, final String email,
                              final String password, final City bornCity, final String mailLocale, final String role, long user_image_id){
        LOGGER.debug("Updating user with email '{}' in the database", email);
        jdbcTemplate.update("UPDATE users SET username = ?, surname = ?, password = ?, city_id = ?, mail_locale = ?, user_role = ?, user_image_id = ?  WHERE email = ?",
                username, surname, password, bornCity.getId(), mailLocale, role, user_image_id, email);
    }
}
