package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private static final String name = "USER";
    private static final String surname = "SURNAME";
    private static final String email = "prueba@email.com";
    private static final String phone = "3424394741";
    private static final String password = "password";
    private static final String birthdate = "2023-05-02";
    private static final City bornCity = new City(1, "Agronomía", 1);
    private static final String role = "USER";
    private static final long userImageId = 1;
    private static final long userId = 8;

    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl us;

    //
    //private final UserServiceImpl us;

    @Test
    public void testCreateUser() {
        // precondiciones
        when(userDao.create(eq(name), eq(surname), eq(email), eq(phone), eq(password), any(), any(), eq(role), anyLong()))
                .thenReturn(new User(userId, name, surname, email, phone, password, LocalDateTime.now(), bornCity, role, userImageId));
        when(passwordEncoder.encode(Mockito.anyString())).thenReturn(password);

        // ejercitar la clase
        User newUSer = us.createUser(name, surname, email, phone, password, birthdate, bornCity, role, userImageId);

        // assertions
        Assert.assertNotNull(newUSer);
        Assert.assertEquals(userId, newUSer.getUserId());
        Assert.assertEquals(name, newUSer.getName());
        Assert.assertEquals(name, newUSer.getName());
    }

}
