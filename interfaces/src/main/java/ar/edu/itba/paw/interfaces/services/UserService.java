package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

public interface UserService {
    User createUser(final String username, final String surname, final String email,
                    final String phone, final String password, final long bornCityId, final Locale mailLocale, String role, byte[] imgData) throws EmailAlreadyExistsException;

    Optional<User> getCurrentUser();
    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);
    void loginUser(final String email, final String password);

    void changeToDriver();

    boolean confirmRegister(String token);

    void blockUser( long blockedId);
    void unblockUser( long blockedId);
    boolean isBlocked( long blockedId);
    void modifyUser(String username, String surname, String phone, long bornCityId, Locale mailLocale, byte[] imgData);

    boolean isOwnUser(long userId);

    boolean sendVerificationEmail(String email);
}
