package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;

import java.util.Optional;

public interface UserService {
    User createUser(String email, String phone, String password);

    User createUserIfNotExists(String email, String phone, String password);

    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);
}
