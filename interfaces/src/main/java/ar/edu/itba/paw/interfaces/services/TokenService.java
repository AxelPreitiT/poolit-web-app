package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;

import java.util.Optional;

public interface TokenService {
    VerificationToken createToken(User user);

    Optional<VerificationToken> getToken(String token);

    void deleteToken(VerificationToken token);

    boolean isValidToken(VerificationToken token);

    void renewToken(VerificationToken token);
}
