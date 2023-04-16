package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {
    User createUser(String email, String phone);

    User createUserIfNotExists(String email, String phone);

    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);
}
