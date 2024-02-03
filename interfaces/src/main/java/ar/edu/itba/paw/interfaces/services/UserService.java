package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(final String username, final String surname, final String email,
                    final String phone, final String password, final long bornCityId, final String mailLocaleString, String role, byte[] imgData) throws EmailAlreadyExistsException, CityNotFoundException;

    Image getUserImage(final long userId,final Image.Size size) throws UserNotFoundException, ImageNotFoundException;

    void updateUserImage(final long userId, final byte[] content) throws UserNotFoundException, ImageNotFoundException;

    void modifyUser(final long userId, final String username, final String surname, final String phone, final Integer bornCityId, final String mailLocaleString, final String role) throws CityNotFoundException, UserNotFoundException, RoleAlreadyChangedException;

    Optional<User> getCurrentUser();
    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);
    void confirmRegister(String token, final User user)throws InvalidTokenException;

    boolean sendVerificationEmail(String email);

    List<User> getAdmins();
    void banUser(User user);
}
