package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;

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
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(final String username, final String surname, final String email,
                           final String phone, final String password, final String birthdate, final City bornCity, String role) {
        if(role == null){
            role = Roles.USER.role;
        }
        String finalRole = role;
        LocalDateTime dateTime = getLocalDateTime(birthdate,"00:00").get();
        return userDao.create(username,surname,email, phone, passwordEncoder.encode(password), dateTime, bornCity, finalRole);
    }

    private Optional<LocalDateTime> getLocalDateTime(final String date, final String time){
        if(date.length()==0 || time.length()==0){
            return Optional.empty();
        }
        LocalDateTime ans;
        try{
            String[] timeTokens = time.split(":");
            ans = LocalDate.parse(date, DateTimeFormatter.ISO_DATE).atTime(Integer.parseInt(timeTokens[0]),Integer.parseInt(timeTokens[1]));
        }catch (Exception e){
            return Optional.empty();
        }
        return Optional.of(ans);
    }

    @Override
    public User createUserIfNotExists(final String username, final String surname, final String email,
                                      final String phone, final String password, final String birthdate, final City bornCity, String role){
        if(role == null){
            role = Roles.USER.role;
        }
        String finalRole = role;
        Optional<User> current = userDao.findByEmail(email);
        LocalDateTime dateTime = getLocalDateTime(birthdate,"00:00").get();
        return current.orElseGet(() -> userDao.create(username,surname,email, phone, passwordEncoder.encode(password), dateTime,bornCity, finalRole));
    }

    @Override
    public Optional<User> findById(long userId){
        return userDao.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return userDao.findByEmail(email);
    }

    @Override
    public void changeRole(long userId, String role) {
        if(Objects.equals(role, Roles.USER.role)){
            role = Roles.DRIVER.role;
        }else{
            role = Roles.USER.role;
        }
        userDao.changeRole(userId, role);
    }
}
