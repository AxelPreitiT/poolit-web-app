package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserDao {
    User create(final String username, final String surname, final String email,
                final String phone, final String password, final LocalDateTime birthdate, final City bornCityId, String role);

    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);

    void changeRole(long userId, String role);
}
