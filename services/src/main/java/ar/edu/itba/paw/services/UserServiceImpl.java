package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import ar.edu.itba.paw.models.VerificationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ImageService imageService;

    private final CityService cityService;

    private final EmailService emailService;

    private final UserDao userDao;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    //private final UserDetailsService userDetailsService;

    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder,
                           final AuthenticationManager authenticationManager,
                           final TokenService tokenService,final ImageService imageService1, final CityService cityService1,
                           final EmailService emailService1){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        //this.userDetailsService = userDetailsService;
        this.imageService = imageService1;
        this.cityService = cityService1;
        this.emailService = emailService1;
    }

    @Transactional
    @Override
    public User createUser(final String username, final String surname, final String email,
                           final String phone, final String password, final long bornCityId, final String mailLocaleString, final String role, byte[] imgData) throws EmailAlreadyExistsException, CityNotFoundException {
        final City bornCity = cityService.findCityById(bornCityId).orElseThrow(CityNotFoundException::new);
        final long user_image_id = imageService.createImage(imgData).getImageId();
        String finalRole = (role == null) ? UserRole.USER.getText() : role;
        Optional<User> possibleUser = userDao.findByEmail(email);
        if(possibleUser.isPresent()){
            LOGGER.debug("Email '{}' already exists in the database", email);
            if(possibleUser.get().getPassword()!=null){
                final EmailAlreadyExistsException exception = new EmailAlreadyExistsException();
                LOGGER.error("Email '{}' already exists in the database and has already registered", email, exception);
                throw exception;
            }else{
                LOGGER.debug("Email '{}' already exists in the database but has not registered yet, updating user", email);
                User ans = userDao.updateProfile(username, surname, email, passwordEncoder.encode(password), bornCity, mailLocaleString, finalRole, user_image_id);
                LOGGER.info("User with email '{}' updated in the database", email);
                return ans;
            }
        }
        User finalUser = userDao.create(username,surname,email, phone, passwordEncoder.encode(password), bornCity, new Locale(mailLocaleString), finalRole, user_image_id);
        VerificationToken token = tokenService.createToken(finalUser);
        try {
            emailService.sendVerificationEmail(finalUser, token.getToken());
        } catch (Exception e) {
            LOGGER.error("There was an error sending verification email for new user with id {}", finalUser.getUserId(), e);
        }
        return finalUser;
    }


    @Override
    public boolean isDriver(User user){
        return user.getRole().equals(UserRole.DRIVER.getText());
    }

    @Override
    public boolean isUser(User user){
        return user.getRole().equals(UserRole.USER.getText());
    }
    @Override
    public void loginUser(final String email, final String password){
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
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
    public void changeToDriver() throws UserNotFoundException {
        User user = getCurrentUser().orElseThrow(UserNotFoundException::new);
        userDao.changeRole(user.getUserId(), UserRole.DRIVER.getText());
    }

    @Transactional
    @Override
    public void blockUser( long blockedId) throws UserNotFoundException{
        User blocker = getCurrentUser().orElseThrow(UserNotFoundException::new);
        userDao.blockUser(blocker.getUserId(),blockedId);
    }

    @Transactional
    @Override
    public void unblockUser( long blockedId) throws UserNotFoundException{
        User blocker = getCurrentUser().orElseThrow(UserNotFoundException::new);
        userDao.unblockUser(blocker.getUserId(),blockedId);
    }

    @Override
    public boolean isBlocked( long blockedId) throws UserNotFoundException {
        User blocker = getCurrentUser().orElseThrow(UserNotFoundException::new);
        return userDao.isBlocked(blocker.getUserId(),blockedId);
    }

    @Override
    public boolean isCurrentUser(long userId) throws UserNotFoundException {
        Optional<User> user = getCurrentUser();
        if(user.isPresent()){
            return user.orElseThrow(UserNotFoundException::new).getUserId() == userId;
        }
        return false;
    }

    @Override
    public boolean sendVerificationEmail(String email){
        Optional<User> finalUser = findByEmail(email);
        if(finalUser.isPresent()){
            if (finalUser.get().isEnabled()){
                return false;
            }
            String token = tokenService.updateToken(finalUser.get());
            try {
                emailService.sendVerificationEmail(finalUser.get(), token);
            } catch (Exception e) {
                LOGGER.error("There was an error sending verification email for new user with id {}", finalUser.get().getUserId(), e);
            }
        }
        return true;
    }

    //TODO: usar el otro enum
    @Transactional
    @Override
    public boolean confirmRegister(String token) {
        VerificationToken verificationToken = tokenService.getToken(token).orElse(null);
        if(verificationToken == null){
            return false;
        }
        boolean isValidToken = tokenService.isValidToken(verificationToken);
        if (isValidToken) {
            final User user = verificationToken.getUser();
            user.setEnabled(true);
            authWithoutPassword(user);
            /*
            final Collection<GrantedAuthority> authorities = new HashSet<>();
            if(Objects.equals(user.getRole(), "DRIVER")){
                authorities.add(new SimpleGrantedAuthority(AuthRoles.DRIVER.role));
            } else {
                authorities.add(new SimpleGrantedAuthority(AuthRoles.USER.role));
            }

            tokenService.deleteToken(verificationToken);



            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(user.getEmail()), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            */
        }
        return isValidToken;
    }



    void authWithoutPassword(User user) {
        final Collection<GrantedAuthority> authorities = new HashSet<>();
        if(Objects.equals(user.getRole(), UserRole.DRIVER.getText())){
            authorities.add(new SimpleGrantedAuthority(UserRole.DRIVER_ROLE.getText()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER_ROLE.getText()));
        }

        //Authentication authentication = new UsernamePasswordAuthenticationToken(userDetailsService.loadUserByUsername(user.getEmail()), null, authorities);
        //SecurityContextHolder.getContext().setAuthentication(authentication);


        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
        Authentication authRequest = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authRequest);
    }
    @Transactional
    @Override
    public void modifyUser(String username, String surname, String phone, long bornCityId, String mailLocaleString, byte[] imgData) throws CityNotFoundException {
        Optional<User> user = getCurrentUser();
        if(user.isPresent()){
            imageService.replaceImage(user.get().getUserImageId(),imgData);
            userDao.modifyUser(user.get().getUserId(), username,surname,phone,cityService.findCityById(bornCityId).orElseThrow(CityNotFoundException::new),new Locale(mailLocaleString));
        }
    }

    @Transactional
    @Override
    public List<User> getAdmins(){
        return userDao.getAdmins();
    }

    @Override
    public void banUser(User user) {
        userDao.banUser(user.getUserId());
    }
}
