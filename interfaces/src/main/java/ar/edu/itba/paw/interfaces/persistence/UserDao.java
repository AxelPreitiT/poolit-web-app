package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserDao {
    User create(final String username, final String surname, final String email,
                final String phone, final String password, final City bornCityId, final Locale mailLocale, final String role, long userImageId);

    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);

    void changeRole(long userId, String role);

    User updateProfile(final String username, final String surname, final String email,
                              final String password, final City bornCity, final String mailLocale, final String role, long userImageId);
    void modifyUser(long userId, String username, String surname, String phone, City bornCity, Locale mailLocale,long imageId);

    List<User> getAdmins();
    void banUser(long userId);
}
