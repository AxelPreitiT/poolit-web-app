package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(final String username, final String surname, final String email,
                    final String phone, final String password, final long bornCityId, final String mailLocaleString, String role, byte[] imgData) throws EmailAlreadyExistsException, CityNotFoundException;

    byte[] getUserImage(final long userId) throws UserNotFoundException, ImageNotFoundException;

    void updateUserImage(final long userId, final byte[] content) throws UserNotFoundException, ImageNotFoundException;

    Optional<User> getCurrentUser();
    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);
    void loginUser(final String email, final String password);

    void changeToDriver() throws UserNotFoundException;

    void changeRole(final long userId, final String role) throws UserNotFoundException;

    boolean confirmRegister(String token);

    void blockUser( long blockedId) throws UserNotFoundException;
    void unblockUser( long blockedId) throws UserNotFoundException;
    boolean isBlocked( long blockedId) throws UserNotFoundException;
    public void modifyUser(final long userId,String username, String surname, String phone, long bornCityId, String mailLocaleString, byte[] imgData) throws CityNotFoundException, UserNotFoundException;

    boolean isCurrentUser(long userId) throws UserNotFoundException;

    boolean sendVerificationEmail(String email);

    public boolean isDriver(User user);

    public boolean isUser(User user);

    public List<User> getAdmins();
    void banUser(User user);
}
