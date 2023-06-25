package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoImplTest {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserHibernateDao userDao;


    private static final long KNOWN_IMAGE_ID = 1;
    private static final long KNOWN_CITY_ID = 1;
    private static final long KNOWN_PROVINCE_ID = 1;

    private static final String USERNAME = "John";
    private static final String SURNAME = "Doe";
    private static final String EMAIL = "jonhdoe@mail.com";
    private static final String PHONE = "1234567800";
    private static final String PASSWORD = "1234";
    private static final Locale LOCALE = Locale.ENGLISH;

    private static final long KNOWN_USER_ID = 1;
    private static final City city = new City(KNOWN_CITY_ID,"CABA",KNOWN_PROVINCE_ID);
    private static final String ROLE = "USER";


    @Rollback
    @Test
    public void testCreateUser(){
        //No setup

        //Execute
        User user = userDao.create(USERNAME,SURNAME,EMAIL,PHONE,PASSWORD,city,LOCALE,ROLE,KNOWN_IMAGE_ID);

        Assert.assertEquals(USERNAME,user.getName());
        Assert.assertEquals(SURNAME,user.getSurname());
        Assert.assertEquals(EMAIL,user.getEmail());
        Assert.assertEquals(PHONE,user.getPhone());
        Assert.assertEquals(PASSWORD,user.getPassword());
        Assert.assertEquals(LOCALE,user.getMailLocale());
        Assert.assertEquals(ROLE,user.getRole());
        Assert.assertEquals(KNOWN_CITY_ID,user.getBornCity().getId());
        Assert.assertEquals(KNOWN_IMAGE_ID,user.getUserImageId());
        Assert.assertTrue(user.getUserId()>0);
    }

    @Test
    public void testFindById(){

        //Execute
        final Optional<User> user = userDao.findById(KNOWN_USER_ID);

        //Assert
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USERNAME,user.get().getName());
        Assert.assertEquals(SURNAME,user.get().getSurname());
        Assert.assertEquals(EMAIL,user.get().getEmail());
        Assert.assertEquals(PHONE,user.get().getPhone());
        Assert.assertEquals(PASSWORD,user.get().getPassword());
        Assert.assertEquals(ROLE,user.get().getRole());
        Assert.assertEquals(LOCALE.toString().toLowerCase(),user.get().getMailLocale().toString().toLowerCase());
        Assert.assertEquals(KNOWN_CITY_ID,user.get().getBornCity().getId());
        Assert.assertEquals(KNOWN_IMAGE_ID,user.get().getUserImageId());
        Assert.assertEquals(KNOWN_USER_ID,user.get().getUserId());
    }

    @Test
    public void testFindByEmail(){
        final Optional<User> user = userDao.findByEmail(EMAIL);

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USERNAME,user.get().getName());
        Assert.assertEquals(SURNAME,user.get().getSurname());
        Assert.assertEquals(EMAIL,user.get().getEmail());
        Assert.assertEquals(PHONE,user.get().getPhone());
        Assert.assertEquals(PASSWORD,user.get().getPassword());
        Assert.assertEquals(ROLE,user.get().getRole());
        Assert.assertEquals(LOCALE.toString().toLowerCase(),user.get().getMailLocale().toString().toLowerCase());
        Assert.assertEquals(KNOWN_CITY_ID,user.get().getBornCity().getId());
        Assert.assertEquals(KNOWN_IMAGE_ID,user.get().getUserImageId());
        Assert.assertEquals(KNOWN_USER_ID,user.get().getUserId());
    }

    @Test
    public void testChangeRole(){
        //SetUp
        final String TEST_ROLE = "DRIVER";
        //Execute
        userDao.changeRole(KNOWN_USER_ID,TEST_ROLE);

        //Assert
        //We do this here to avoid depending in the method of the userDAO
        TypedQuery<User> query = em.createQuery("from User where userId = :userId",User.class);
        query.setParameter("userId",KNOWN_USER_ID);
        Optional<User> user = query.getResultList().stream().findFirst();

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USERNAME,user.get().getName());
        Assert.assertEquals(SURNAME,user.get().getSurname());
        Assert.assertEquals(EMAIL,user.get().getEmail());
        Assert.assertEquals(PHONE,user.get().getPhone());
        Assert.assertEquals(PASSWORD,user.get().getPassword());
        Assert.assertEquals(TEST_ROLE,user.get().getRole());
        Assert.assertEquals(LOCALE.toString().toLowerCase(),user.get().getMailLocale().toString().toLowerCase());
        Assert.assertEquals(KNOWN_CITY_ID,user.get().getBornCity().getId());
        Assert.assertEquals(KNOWN_IMAGE_ID,user.get().getUserImageId());
        Assert.assertEquals(KNOWN_USER_ID,user.get().getUserId());
    }
    @Test
    public void testUpdateUser(){
        //SetUp
        final String TEST_NAME = "TestName";
        final String TEST_SURNAME = "TestSurname";
        final String TEST_PASSWORD = "Pass";
        final Locale TEST_LOCALE = Locale.UK;
        final String TEST_ROLE = "OTHER_ROLE";
        //Execute
        userDao.updateProfile(TEST_NAME,TEST_SURNAME,EMAIL,TEST_PASSWORD,city,TEST_LOCALE.toString(),TEST_ROLE,KNOWN_IMAGE_ID);

        //Assert
        //Hacemos esto para evitar depender de los metodos del userDao
        TypedQuery<User> query = em.createQuery("from User where userId = :userId",User.class);
        query.setParameter("userId",KNOWN_USER_ID);
        Optional<User> user = query.getResultList().stream().findFirst();

        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(TEST_NAME,user.get().getName());
        Assert.assertEquals(TEST_SURNAME,user.get().getSurname());
        Assert.assertEquals(EMAIL,user.get().getEmail());
        Assert.assertEquals(PHONE,user.get().getPhone());
        Assert.assertEquals(TEST_PASSWORD,user.get().getPassword());
        Assert.assertEquals(TEST_ROLE,user.get().getRole());
        Assert.assertEquals(TEST_LOCALE.toString().toLowerCase(),user.get().getMailLocale().toString().toLowerCase());
        Assert.assertEquals(KNOWN_CITY_ID,user.get().getBornCity().getId());
        Assert.assertEquals(KNOWN_IMAGE_ID,user.get().getUserImageId());
        Assert.assertEquals(KNOWN_USER_ID,user.get().getUserId());
    }
}