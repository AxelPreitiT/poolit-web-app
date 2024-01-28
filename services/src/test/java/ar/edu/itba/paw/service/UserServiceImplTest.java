package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.exceptions.RoleAlreadyChangedException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.VerificationToken;
import ar.edu.itba.paw.services.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String NAME = "USER";
    private static final String SURNAME = "SURNAME";
    private static final String MAIL = "prueba@email.com";
    private static final String PHONE = "3424394741";
    private static final String PASSWORD = "password";
    private static final City BORN_CITY = new City(1, "Agronomía", 1);
    private static final long BORN_CITY_ID = 1;
    private static final String MAIL_LOCALE = "es";
    private static final String ROLE = "USER";
    private static final String TOKEN = "Token";
    private static final long USER_IMAGE_ID = 1;
    private static final long USER_ID = 8;
    private static User USER = new User(NAME, SURNAME, MAIL, PHONE, PASSWORD, BORN_CITY, new Locale(MAIL_LOCALE), ROLE, USER_IMAGE_ID);
    private static final Image IMAGEN = new Image(0L, null);
    private static final LocalDate EXPIRY_DATE = LocalDate.now();

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private CityServiceImpl cityService;
    @Mock
    private ImageServiceImpl imageService;
    @Mock
    private TokenServiceImpl tokenService;
    @Mock
    private EmailServiceImpl emailService;

    @InjectMocks
    private UserServiceImpl us;

    @Test
    public void testCreateUser() throws EmailAlreadyExistsException, CityNotFoundException, MessagingException, IOException {
        // precondiciones
        when(userDao.create(eq(NAME), eq(SURNAME), eq(MAIL), eq(PHONE), eq(PASSWORD), eq(BORN_CITY), eq(new Locale(MAIL_LOCALE)), eq(ROLE), anyLong()))
                .thenReturn(new User(USER_ID, NAME, SURNAME, MAIL, PHONE, PASSWORD, BORN_CITY, new Locale(MAIL_LOCALE), ROLE, USER_IMAGE_ID));
        when(passwordEncoder.encode(Mockito.anyString())).thenReturn(PASSWORD);
        when(cityService.findCityById(Mockito.anyLong())).thenReturn(Optional.of(BORN_CITY));
        when(userDao.findByEmail(any())).thenReturn(Optional.empty());
        when(tokenService.createToken(any())).thenReturn(new VerificationToken(USER, TOKEN, EXPIRY_DATE));
        doNothing().when(emailService).sendVerificationEmail(any(), any());
        when(imageService.createImage(any())).thenReturn(IMAGEN);

        // ejercitar la clase
        User newUSer = us.createUser(NAME, SURNAME, MAIL, PHONE, PASSWORD, BORN_CITY_ID, MAIL_LOCALE, ROLE, null);

        // assertions
        Assert.assertNotNull(newUSer);
        Assert.assertEquals(USER_ID, newUSer.getUserId());
        Assert.assertEquals(NAME, newUSer.getName());
        Assert.assertEquals(SURNAME, newUSer.getSurname());
        Assert.assertEquals(MAIL, newUSer.getEmail());
        Assert.assertEquals(PHONE, newUSer.getPhone());
        Assert.assertEquals(PASSWORD, newUSer.getPassword());
        Assert.assertEquals(BORN_CITY, newUSer.getBornCity());
        Assert.assertEquals(new Locale(MAIL_LOCALE), newUSer.getMailLocale());
        Assert.assertEquals(ROLE, newUSer.getRole());
        Assert.assertEquals(USER_IMAGE_ID, newUSer.getUserImageId());
    }

    @Test(expected = EmailAlreadyExistsException.class)
    public void testAlreadyExistUserCreateUser() throws EmailAlreadyExistsException, CityNotFoundException, MessagingException, IOException {
        // precondiciones
        when(cityService.findCityById(Mockito.anyLong())).thenReturn(Optional.of(BORN_CITY));
        when(userDao.findByEmail(any())).thenReturn(Optional.of(USER));
        when(imageService.createImage(any())).thenReturn(IMAGEN);

        // ejercitar la clase
        us.createUser(NAME, SURNAME, MAIL, PHONE, PASSWORD, BORN_CITY_ID, MAIL_LOCALE, ROLE, null);

        // assertions
        Assert.fail();
    }

    @Test(expected = RoleAlreadyChangedException.class)
    public void testChangeRoleAlreadyChanged() throws UserNotFoundException, RoleAlreadyChangedException {
        User aux =new User(NAME, SURNAME, MAIL, PHONE, PASSWORD, BORN_CITY, new Locale(MAIL_LOCALE), "DRIVER", USER_IMAGE_ID);
        when(userDao.findById(anyLong())).thenReturn(Optional.of(aux));

        us.changeRole(1,"DRIVER");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeRoleInvalidRole() throws UserNotFoundException, RoleAlreadyChangedException {
        when(userDao.findById(anyLong())).thenReturn(Optional.of(USER));

        us.changeRole(1,"ADMIN");
    }

}
