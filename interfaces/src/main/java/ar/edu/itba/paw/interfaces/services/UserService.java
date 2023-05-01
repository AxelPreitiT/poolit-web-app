package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserService {
    User createUser(final String username, final String surname, final String email,
                    final String phone, final String password, final String birthdate, final City bornCity, String role, long user_image_id);

    User createUserIfNotExists(final String username, final String surname, final String email,
                               final String phone, final String password, final String birthdate, final City bornCityId, String role, long user_image_id);

    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);

    public void changeRole(long userId, String role);

}
