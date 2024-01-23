package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
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
    public User create(String username, String surname, String email, String phone, String password, City bornCity, Locale mailLocale, String role, long userImageId) {
        String savedEmail = email.toLowerCase().replaceAll("\\s", "");
        String savedPhone = phone.replaceAll("\\s", "");
        String savedPassword = password.replaceAll("\\s", "");
        LOGGER.debug("Adding new user with email '{}' to the database", savedEmail);
        final User user = new User(username, surname, savedEmail, savedPhone, savedPassword, bornCity, mailLocale, role, userImageId);
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
        final TypedQuery<User> query = (em.createQuery("from User as u where u.email= :email", User.class));
        query.setParameter("email", searchEmail);
        final Optional<User> result = query.getResultList().stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public void changeRole(long userId, String role) {
        LOGGER.debug("Changing role of user with id {} to '{}'", userId, role);

        Optional<User> maybeUser = findById(userId);
        if (maybeUser.isPresent()) {
            final User user = maybeUser.get();
            user.setRole(role);
            em.persist(user);
        }
    }

    @Override
    public User updateProfile(String username, String surname, String email, String password, City bornCity, String mailLocale, String role, long userImageId) {
        Optional<User> maybeUser = findByEmail(email);
        LOGGER.debug("Updating user with email '{}' in the database", email);
        if (maybeUser.isPresent()) {
            final User user = maybeUser.get();
            user.setRole(role);
            user.setBornCity(bornCity);
            user.setMailLocale(new Locale(mailLocale));
            user.setName(username);
            user.setPassword(password);
            user.setSurname(surname);
            user.setUserImageId(userImageId);

            em.persist(user);
            return user;
        }
        return null;
    }

    @Override
    public void modifyUser(long userId, String username, String surname, String phone, City bornCity, Locale mailLocale, long imageId) {
        Optional<User> maybeUser = findById(userId);
        LOGGER.debug("Modifying user with id '{}' in the database", userId);
        if (maybeUser.isPresent()) {
            final User user = maybeUser.get();
            user.setName(username);
            user.setSurname(surname);
            user.setPhone(phone);
            user.setBornCity(bornCity);
            user.setMailLocale(mailLocale);
            user.setUserImageId(imageId);
            em.persist(user);
        }
    }

//    @Override
//    public void blockUser(long blockerId, long blockedId) {
//        String sql = "INSERT INTO blocks (blockedById, blockedId) VALUES (:blockerId, :blockedId)";
//        em.createNativeQuery(sql)
//                .setParameter("blockerId", blockerId)
//                .setParameter("blockedId", blockedId)
//                .executeUpdate();
//    }

//    @Override
//    public void unblockUser(long blockerId, long blockedId) {
//        String sql = "DELETE FROM blocks WHERE blockedById = :blockerId AND blockedId = :blockedId";
//        em.createNativeQuery(sql)
//                .setParameter("blockerId", blockerId)
//                .setParameter("blockedId", blockedId)
//                .executeUpdate();
//    }

//    @Override
//    public boolean isBlocked(long blockerId, long blockedId) {
//        String sql = "SELECT COUNT(*) FROM blocks WHERE blockedById = :blockerId AND blockedId = :blockedId";
//        int count = ((Number) em.createNativeQuery(sql)
//                .setParameter("blockerId", blockerId)
//                .setParameter("blockedId", blockedId)
//                .getSingleResult()).intValue();
//        return count > 0;
//    }

    @Override
    public List<User> getAdmins() {
        LOGGER.debug("Getting all admins from the database");
        final TypedQuery<User> query = (em.createQuery("from User as u where u.role= :role", User.class));
        query.setParameter("role", UserRole.ADMIN.getText());
        final List<User> result = query.getResultList();
        LOGGER.debug("Found {} admins in the database", result.size());
        return result;
    }

    @Override
    public void banUser(long userId) {
        Optional<User> maybeUser = findById(userId);
        LOGGER.debug("Modifying user banned with id '{}' in the database", userId);
        if (maybeUser.isPresent()) {
            final User user = maybeUser.get();
            user.setBanned(true);
            em.persist(user);
        }
    }


}
