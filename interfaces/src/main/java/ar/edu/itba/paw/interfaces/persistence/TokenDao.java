package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;

import java.time.LocalDate;
import java.util.Optional;

public interface TokenDao {
    VerificationToken createToken(User user, String token, LocalDate expiryDate);

    Optional<VerificationToken> getToken(String token);

//    void deleteToken(VerificationToken token);

    void updateToken(String token, User user, LocalDate date);
}
