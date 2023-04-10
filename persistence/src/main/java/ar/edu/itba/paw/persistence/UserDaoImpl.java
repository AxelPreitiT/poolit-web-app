package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private static final RowMapper<User> ROW_MAPPER = (resultSet, rowNum) -> new User(resultSet.getLong("user_id"),resultSet.getString("email"),resultSet.getString("phone"));

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
    public User create(final String email, final String phone) {
        Map<String,Object> data = new HashMap<>();
        data.put("email",email);
        data.put("phone",phone);
        Number key = jdbcInsert.executeAndReturnKey(data);
        return new User(key.longValue(),email,phone);
    }

    @Override
    public Optional<User> findById(long userId){
        return jdbcTemplate.query("SELECT * FROM users WHERE user_id = ?",ROW_MAPPER,userId).stream().findFirst();
    }

    @Override
    public Optional<User> findByEmail(String email){
        return jdbcTemplate.query("SELECT * FROM users WHERE email = ?",ROW_MAPPER,email).stream().findFirst();
    }
}
