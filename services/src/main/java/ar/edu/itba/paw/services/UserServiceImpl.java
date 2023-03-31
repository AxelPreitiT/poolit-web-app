package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(final UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public User createUser(String email, String password) {
        //Esto esta mal, solo se deberian crear usuarios en la capa de peristencia
        //pero es para un ejemplo
//        return new User(email,password);
        //Con esto si lo crea la capa de persistencia
        return userDao.create(email,password);
    }
}
