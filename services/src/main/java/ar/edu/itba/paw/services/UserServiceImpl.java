package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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

import java.io.IOException;
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

    @Value("classpath:images/profile.jpeg")
    private Resource defaultImg;

    private static final long DEFAULT_IMAGE_ID = 66;


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
        emailService.sendVerificationEmail(finalUser, token.getToken());
        return finalUser;
    }


    @Transactional
    @Override
    public Image getUserImage(final long userId, Image.Size size) throws UserNotFoundException, ImageNotFoundException {
        final User user = findById(userId).orElseThrow(UserNotFoundException::new);
        try {
            return imageService.getImageOrDefault(user.getUserImageId(),size,defaultImg.getInputStream());
//            return imageService.getImageByteaOrDefault(user.getUserImageId(),size,defaultImg.getInputStream());
        }catch (IOException e){
            throw new ImageNotFoundException();
        }
    }

    @Transactional
    @Override
    public void updateUserImage(final long userId, final byte[] content) throws UserNotFoundException, ImageNotFoundException{
        final User user = findById(userId).orElseThrow(UserNotFoundException::new);
        final long oldImageId = user.getUserImageId();
        final long imageId = imageService.createImage(content).getImageId();
        if(oldImageId != DEFAULT_IMAGE_ID){
            try {
                imageService.deleteImage(oldImageId);
            }catch (Exception e){
                LOGGER.error("There was an error trying to delete image with id {} for user {}",oldImageId,userId,e);
            }
        }
        userDao.modifyUser(user.getUserId(), user.getName(),user.getSurname(),user.getPhone(),user.getBornCity(),user.getMailLocale(),imageId);
//        if(user.getUserImageId() == DEFAULT_IMAGE_ID){ //fix migration in pawserver
//            //creamos una imagen para no pisar la default
//            final long imageId = imageService.createImage(content).getImageId();
//            userDao.modifyUser(user.getUserId(), user.getName(),user.getSurname(),user.getPhone(),user.getBornCity(),user.getMailLocale(),imageId);
//            return;
//        }
//        imageService.updateImage(content,user.getUserImageId());
    }

//    @Transactional
//    @Override
//    public boolean isDriver(User user){
//        return user.getIsDriver();
//    }

//    @Transactional
//    @Override
//    public boolean isUser(User user){
//        return user.getIsUser();
//    }

//    @Transactional
//    @Override
//    public void loginUser(final String email, final String password){
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
//        Authentication auth = authenticationManager.authenticate(authRequest);
//        SecurityContextHolder.getContext().setAuthentication(auth);
//    }

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


//    @Transactional
//    @Override
//    public void changeToDriver() throws UserNotFoundException {
//        User user = getCurrentUser().orElseThrow(UserNotFoundException::new);
//        userDao.changeRole(user.getUserId(), UserRole.DRIVER.getText());
//    }



//    @Transactional
//    @Override
//    public void blockUser( long blockedId) throws UserNotFoundException{
//        User blocker = getCurrentUser().orElseThrow(UserNotFoundException::new);
//        userDao.blockUser(blocker.getUserId(),blockedId);
//    }

//    @Transactional
//    @Override
//    public void unblockUser( long blockedId) throws UserNotFoundException{
//        User blocker = getCurrentUser().orElseThrow(UserNotFoundException::new);
//        userDao.unblockUser(blocker.getUserId(),blockedId);
//    }

//    @Transactional
//    @Override
//    public boolean isBlocked( long blockedId) throws UserNotFoundException {
//        User blocker = getCurrentUser().orElseThrow(UserNotFoundException::new);
//        return userDao.isBlocked(blocker.getUserId(),blockedId);
//    }

//    @Transactional
//    @Override
//    public boolean isCurrentUser(long userId) throws UserNotFoundException {
//        Optional<User> user = getCurrentUser();
//        if(user.isPresent()){
//            return user.orElseThrow(UserNotFoundException::new).getUserId() == userId;
//        }
//        return false;
//    }

    @Transactional
    @Override
    public boolean sendVerificationEmail(String email){
        Optional<User> finalUser = findByEmail(email);
        if(finalUser.isPresent()){
            if (finalUser.get().isEnabled()){
                return false;
            }
            String token = tokenService.updateToken(finalUser.get());
            emailService.sendVerificationEmail(finalUser.get(), token);
        }
        return true;
    }

    @Transactional
    @Override
    public void confirmRegister(String token, final User tokenUser) throws InvalidTokenException {
        VerificationToken verificationToken = tokenService.getToken(token).orElse(null);
        if(verificationToken == null){
            throw new InvalidTokenException();
        }
        final User user = verificationToken.getUser();
        if(user.getUserId() != tokenUser.getUserId()){
            throw new InvalidTokenException();
        }
        if (!tokenService.isValidToken(verificationToken)){
            throw new InvalidTokenException();
        }
        //verificationToken != null and it is a valid token

        tokenUser.setEnabled(true);
        //TODO: delete
//        authWithoutPassword(user);
    }


//    void authWithoutPassword(User user) {
//        final Collection<GrantedAuthority> authorities = new HashSet<>();
//
//        if(Objects.equals(user.getRole(), UserRole.ADMIN.getText())){
//            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN_ROLE.getText()));
//        }else{
//            if(Objects.equals(user.getRole(), UserRole.DRIVER.getText())){
//                authorities.add(new SimpleGrantedAuthority(UserRole.DRIVER_ROLE.getText()));
//            } else {
//                authorities.add(new SimpleGrantedAuthority(UserRole.USER_ROLE.getText()));
//            }
//        }
//
//        //TODO: revisar usos, y ver si hay que poner lo de banned acá
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword(), authorities);
//        Authentication authRequest = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
//        SecurityContextHolder.getContext().setAuthentication(authRequest);
//    }

//    @Transactional
//    @Override
//    public void modifyUser(final long userId,String username, String surname, String phone, long bornCityId, String mailLocaleString, byte[] imgData) throws CityNotFoundException, UserNotFoundException {
//        User user = findById(userId).orElseThrow(UserNotFoundException::new);
//            if(imgData.length==0){
//                userDao.modifyUser(user.getUserId(), username,surname,phone,cityService.findCityById(bornCityId).orElseThrow(CityNotFoundException::new),new Locale(mailLocaleString),user.getUserImageId());
//                return;
//            }
//            long imageId = imageService.createImage(imgData).getImageId();
//            userDao.modifyUser(user.getUserId(), username,surname,phone,cityService.findCityById(bornCityId).orElseThrow(CityNotFoundException::new),new Locale(mailLocaleString),imageId);
//    }

    @Transactional
    @Override
    public void modifyUser(final long userId, final String username, final String surname, final String phone, final Integer bornCityId, final String mailLocaleString, final String role) throws CityNotFoundException, UserNotFoundException, RoleAlreadyChangedException {
        User user = findById(userId).orElseThrow(UserNotFoundException::new);
        if(role!=null) {
            if (!role.equals(UserRole.USER.getText()) && !role.equals(UserRole.DRIVER.getText())) {
                throw new IllegalArgumentException();
            }
            if (!user.getRole().equals(UserRole.USER.getText())) {
                //role has already been changed
                throw new RoleAlreadyChangedException();
            }
            userDao.changeRole(user.getUserId(), role);
        }
        userDao.modifyUser(userId,
                username!=null?username:user.getName(),
                surname!=null?surname:user.getSurname(),
                phone!=null?phone:user.getPhone(),
                bornCityId!=null?cityService.findCityById(bornCityId).orElseThrow(CityNotFoundException::new):user.getBornCity(),
                mailLocaleString!=null?new Locale(mailLocaleString):user.getMailLocale(),
                user.getUserImageId());
    }

//    @Transactional
//    @Override
//    public void changeRole(final long userId, final String role) throws UserNotFoundException,RoleAlreadyChangedException {
//        User user = findById(userId).orElseThrow(UserNotFoundException::new);
//        if(!role.equals(UserRole.USER.getText()) && !role.equals(UserRole.DRIVER.getText())){
//            throw new IllegalArgumentException();
//        }
//        if(!user.getRole().equals(UserRole.USER.getText())){
//            //role has already been changed
//            throw new RoleAlreadyChangedException();
//        }
//        userDao.changeRole(user.getUserId(), role);
//    }

    @Transactional
    @Override
    public List<User> getAdmins(){
        return userDao.getAdmins();
    }


    //TODO: revisar cómo es el ban, cómo funciona
    @Transactional
    @Override
    public void banUser(User user) {
        userDao.banUser(user.getUserId());
    }
}
