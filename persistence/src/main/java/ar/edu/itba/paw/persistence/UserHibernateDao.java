package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Repository
public class UserHibernateDao implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHibernateDao.class);

    @PersistenceContext
    private EntityManager em;


    @Override
    public User create(String username, String surname, String email, String phone, String password, City bornCity, Locale mailLocale, String role, long user_image_id) {
        String savedEmail = email.toLowerCase().replaceAll("\\s", "");
        String savedPhone = phone.replaceAll("\\s", "");
        String savedPassword = password.replaceAll("\\s", "");
        LOGGER.debug("Adding new user with email '{}' to the database", savedEmail);
        LOGGER.debug("need help '{}' to the database", user_image_id);
        final User user= new User(username, surname, savedEmail, savedPhone, savedPassword, bornCity, mailLocale, role, user_image_id);
        em.persist(user);
        LOGGER.debug("New {}", user);
        return user;
    }

    @Override
    public Optional<User> findById(long userId) {
        LOGGER.debug("Looking for user with id {} in the database", userId);
        final Optional<User> result = Optional.ofNullable(em.find(User.class, userId));
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        LOGGER.debug("Looking for user with email '{}' in the database", email);
        String searchEmail = email.toLowerCase().replaceAll("\\s", "");
        final TypedQuery<User> query= (em.createQuery("from User as u where u.email= :email",User.class));
        query.setParameter("email", searchEmail);
        final List<User> result= query.getResultList();
        LOGGER.debug("Found {} in the database", !result.isEmpty() ? result.get(0) : "nothing");
        return !result.isEmpty() ? Optional.of(result.get(0)) : Optional.empty();
    }

    @Override
    public void changeRole(long userId, String role) {
        LOGGER.debug("Changing role of user with id {} to '{}'", userId, role);

        Optional<User> maybeUser = findById(userId);
        if (maybeUser.isPresent()){
            final User user = maybeUser.get();
            user.setRole(role);
            em.persist(user);
        }
    }

    @Override
    public User updateProfile(String username, String surname, String email, String password, City bornCity, String mailLocale, String role, long user_image_id) {
        Optional<User> maybeUser = findByEmail(email);
        LOGGER.debug("Updating user with email '{}' in the database", email);
        if (maybeUser.isPresent()){
            final User user = maybeUser.get();
            user.setRole(role);
            user.setBornCity(bornCity);
            user.setMailLocale(Locale.forLanguageTag(mailLocale));
            user.setName(username);
            user.setPassword(password);
            user.setSurname(surname);
            user.setUserImageId(user_image_id);

            em.persist(user);
            return user;
        }
        return null;
    }
}
