package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

public interface UserDao {
    User create(final String username, final String surname, final String email,
                final String phone, final String password, final City bornCityId, final Locale mailLocale, final String role, long user_image_id);

    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);

    void changeRole(long userId, String role);

    public User updateProfile(final String username, final String surname, final String email,
                              final String password, final City bornCity, final String mailLocale, final String role, long user_image_id);
    void modifyUser(long userId, String username, String surname, String phone, City bornCity, Locale mailLocale);

    void blockUser(long blockerId, long blockedId);
    void unblockUser(long blockerId, long blockedId);
    boolean isBlocked(long blockerId, long blockedId);
}
