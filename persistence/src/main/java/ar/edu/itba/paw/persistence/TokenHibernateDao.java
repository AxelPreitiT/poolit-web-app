package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.TokenDao;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Primary
@Repository
public class TokenHibernateDao implements TokenDao {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenHibernateDao.class);

    @Override
    public VerificationToken createToken(User user, String token, LocalDate date) {
        final VerificationToken tokenPersist =  new VerificationToken(user, token, date);
        em.persist(tokenPersist);
        return tokenPersist;
    }

    @Override
    public Optional<VerificationToken> getToken(String token) {
        final TypedQuery<VerificationToken> query = em.createQuery("from VerificationToken where token = :token", VerificationToken.class);
        query.setParameter("token", token);
        return query.getResultList().stream().findFirst();
    }

//    @Override
//    public void deleteToken(VerificationToken token) {
//        em.remove(token);
//    }

    @Override
    public void updateToken(String token, User user, LocalDate date) {
        final TypedQuery<VerificationToken> query = em.createQuery("from VerificationToken where user = :user", VerificationToken.class);
        query.setParameter("user", user);
        query.getResultList().stream().findFirst().ifPresent(t -> {
            t.setToken(token);
            t.setDate(date);
            em.merge(t);
        });
    }

}
