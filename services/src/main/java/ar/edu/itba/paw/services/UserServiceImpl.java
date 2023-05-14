package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private enum Roles{
        USER("USER"),
        DRIVER("DRIVER");
        private final String role;
        private Roles(String role){
            this.role = role;
        }

        public String getRole() {
            return role;
        }
    }

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder,final AuthenticationManager authenticationManager){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    @Override
    public User createUser(final String username, final String surname, final String email,
                           final String phone, final String password, final City bornCity, final Locale mailLocale, final String role, long user_image_id) {

        String finalRole = (role == null) ? Roles.USER.role : role;
        User ans = userDao.create(username,surname,email, phone, passwordEncoder.encode(password), bornCity, mailLocale, finalRole, user_image_id);
        return ans;
    }

    @Override
    public void loginUser(final String email, final String password){
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Override
    public User createUserIfNotExists(final String username, final String surname, final String email,
                                      final String phone, final String password, final City bornCity, final Locale mailLocale, final String role, long user_image_id){

        return userDao.findByEmail(email).orElseGet(() -> createUser(username, surname, email, phone, password, bornCity, mailLocale, role, user_image_id));
    }

    @Override
    public Optional<User> getCurrentUser(){
        final Object authUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (! (authUser instanceof org.springframework.security.core.userdetails.User)){
            return Optional.empty();
        }
        final org.springframework.security.core.userdetails.User aux = (org.springframework.security.core.userdetails.User) authUser;
        return findByEmail(aux.getUsername());
    }

    @Override
    public Optional<User> findById(long userId){
        return userDao.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return userDao.findByEmail(email);
    }

    @Transactional
    @Override
    public void changeRole(long userId, String role) {
        if(Objects.equals(role, Roles.USER.role)){
            role = Roles.DRIVER.role;
        }else{
            role = Roles.USER.role;
        }
        userDao.changeRole(userId, role);
    }

    @Transactional
    @Override
    public void changeToDriver(User user) {
        userDao.changeRole(user.getUserId(), Roles.DRIVER.role);
    }

}
