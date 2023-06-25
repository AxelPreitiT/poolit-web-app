package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
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


    @Autowired
    public UserServiceImpl(final UserDao userDao, final PasswordEncoder passwordEncoder,
                           final AuthenticationManager authenticationManager,
                           final TokenService tokenService,final ImageService imageService, final CityService cityService,
                           final EmailService emailService){
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.imageService = imageService;
        this.cityService = cityService;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public User createUser(final String username, final String surname, final String email,
                           final String phone, final String password, final long bornCityId, final String mailLocaleString, final String role, byte[] imgData) throws EmailAlreadyExistsException, CityNotFoundException {
        final City bornCity = cityService.findCityById(bornCityId).orElseThrow(CityNotFoundException::new);
        final Image image = imageService.createImage(imgData);
        final long userImageId = image.getImageId();
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
                User ans = userDao.updateProfile(username, surname, email, passwordEncoder.encode(password), bornCity, mailLocaleString, finalRole, userImageId);
                LOGGER.info("User with email '{}' updated in the database", email);
                return ans;
            }
        }
        User finalUser = userDao.create(username,surname,email, phone, passwordEncoder.encode(password), bornCity, new Locale(mailLocaleString), finalRole, userImageId);
        VerificationToken token = tokenService.createToken(finalUser);
        try {
            emailService.sendVerificationEmail(finalUser, token.getToken());
        } catch (Exception e) {
            LOGGER.error("There was an error sending verification email for new user with id {}", finalUser.getUserId(), e);
        }
        return finalUser;
    }

    @Transactional
    @Override
    public boolean isDriver(User user){
        return user.getIsDriver();
    }

    @Transactional
    @Override
    public boolean isUser(User user){
        return user.getIsUser();
    }

    @Transactional
    @Override
    public void loginUser(final String email, final String password){
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Transactional
    @Override
    public Optional<User> getCurrentUser(){
        final Object authUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (! (authUser instanceof org.springframework.security.core.userdetails.User)){
            return Optional.empty();
        }
        final org.springframework.security.core.userdetails.User aux = (org.springframework.security.core.userdetails.User) authUser;
        return findByEmail(aux.getUsername());
    }

    @Transactional
    @Override
    public Optional<User> findById(long userId){
        return userDao.findById(userId);
    }

    @Transactional
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

    @Transactional
    @Override
    public boolean isBlocked( long blockedId) throws UserNotFoundException {
        User blocker = getCurrentUser().orElseThrow(UserNotFoundException::new);
        return userDao.isBlocked(blocker.getUserId(),blockedId);
    }

    @Transactional
    @Override
    public boolean isCurrentUser(long userId) throws UserNotFoundException {
        Optional<User> user = getCurrentUser();
        if(user.isPresent()){
            return user.orElseThrow(UserNotFoundException::new).getUserId() == userId;
        }
        return false;
    }

    @Transactional
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
        }
        return isValidToken;
    }


    void authWithoutPassword(User user) {
        final Collection<GrantedAuthority> authorities = new HashSet<>();

        if(Objects.equals(user.getRole(), UserRole.ADMIN.getText())){
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN_ROLE.getText()));
        }else{
            if(Objects.equals(user.getRole(), UserRole.DRIVER.getText())){
                authorities.add(new SimpleGrantedAuthority(UserRole.DRIVER_ROLE.getText()));
            } else {
                authorities.add(new SimpleGrantedAuthority(UserRole.USER_ROLE.getText()));
            }
        }


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
            if(imgData.length<=0){
                userDao.modifyUser(user.get().getUserId(), username,surname,phone,cityService.findCityById(bornCityId).orElseThrow(CityNotFoundException::new),new Locale(mailLocaleString),user.get().getUserImageId());
                return;
            }
            long imageId = imageService.createImage(imgData).getImageId();
            userDao.modifyUser(user.get().getUserId(), username,surname,phone,cityService.findCityById(bornCityId).orElseThrow(CityNotFoundException::new),new Locale(mailLocaleString),imageId);

        }
    }

    @Transactional
    @Override
    public List<User> getAdmins(){
        return userDao.getAdmins();
    }

    @Transactional
    @Override
    public void banUser(User user) {
        userDao.banUser(user.getUserId());
    }
}
