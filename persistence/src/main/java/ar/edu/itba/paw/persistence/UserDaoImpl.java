package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public User create(final String email, final String phone) {
        return new User(email, phone);
    }
}
