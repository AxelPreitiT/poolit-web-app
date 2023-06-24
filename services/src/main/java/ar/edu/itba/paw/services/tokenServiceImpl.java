package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.TokenDao;
import ar.edu.itba.paw.interfaces.services.TokenService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class tokenServiceImpl implements TokenService {
    static final int EXPIRATION = 60 * 24;

    private final TokenDao tokenDao;

    @Autowired
    public tokenServiceImpl(final TokenDao tokenDao){
        this.tokenDao = tokenDao;
    }


    @Transactional
    @Override
    public VerificationToken createToken(User user) {
        String token = UUID.randomUUID().toString();
        return tokenDao.createToken(user, token, calculateExpiryDate(EXPIRATION));
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }


    @Transactional
    @Override
    public Optional<VerificationToken> getToken(String token) {
        return tokenDao.getToken(token);
    }

    @Transactional
    @Override
    public void deleteToken(VerificationToken token) {
        tokenDao.deleteToken(token);
    }

    @Transactional
    @Override
    public boolean isValidToken(VerificationToken token) {
        Calendar calendar = Calendar.getInstance();
        return token.getDate().getTime() >= calendar.getTime().getTime();
    }

    @Transactional
    @Override
    public void renewToken(VerificationToken token) {
        token.setExpiryDate(calculateExpiryDate(EXPIRATION));
    }

    @Transactional
    @Override
    public String updateToken(User user) {
        String token = UUID.randomUUID().toString();
        tokenDao.updateToken(token, user, calculateExpiryDate(EXPIRATION));
        return token;
    }
}
