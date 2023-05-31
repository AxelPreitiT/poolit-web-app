package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;

import java.util.Date;
import java.util.Optional;

public interface TokenDao {
    VerificationToken createToken(User user, String token, Date expiryDate);

    Optional<VerificationToken> getToken(String token);

    void deleteToken(VerificationToken token);
}
