package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String email, String phone, String password) {
        return userDao.create(email,phone, passwordEncoder.encode(password));
    }

    @Override
    public User createUserIfNotExists(String email, String phone, String password){
        Optional<User> current = userDao.findByEmail(email);
        return current.orElseGet(() -> userDao.create(email, phone, passwordEncoder.encode(password)));
    }

    @Override
    public Optional<User> findById(long userId){
        return userDao.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return userDao.findByEmail(email);
    }
}
