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

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/*
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDaoImplTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserHibernateDao userDao;

    private SimpleJdbcInsert jdbcInsert;

    private JdbcTemplate jdbcTemplate;


    private static final long KNOWN_IMAGE_ID = 1;
    private static final long KNOWN_CITY_ID = 1;
    private static final long KNOWN_PROVINCE_ID = 1;

    private static final String USERNAME = "POOLIT";
    private static final String SURNAME = "Trips";
    private static final String EMAIL = "poolit.noreply@gmail.com";
    private static final String PHONE = "1139150686";
    private static final String PASSWORD = "PASS";
    private static final Locale LOCALE = Locale.US;
    private static final City city = new City(KNOWN_CITY_ID,"",KNOWN_PROVINCE_ID);

    private static final String ROLE = "USER";

    @Test
    public void a(){

    }

    @Before
    public void setUp(){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");
        jdbcTemplate.update("INSERT INTO provinces VALUES(?,?)",1,"CABA");
        jdbcTemplate.update("INSERT INTO cities values (?,?,?)",KNOWN_CITY_ID,"Recoleta",1);
        jdbcTemplate.update("INSERT INTO images values (?,?)",KNOWN_IMAGE_ID,null);
    }

    private User addUser(final String username, final String surname, final String email,
                         final String phone, final String password, final Locale locale, final City bornCity, String role, long user_image_id){
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("surname", surname);
        data.put("email", email);
        data.put("phone", phone);
        data.put("password", password);
        data.put("city_id", bornCity.getId());
        data.put("mail_locale",locale.toString());
        data.put("user_role", role);
        data.put("user_image_id", user_image_id);
        Number key = jdbcInsert.executeAndReturnKey(data);
        return new User(key.longValue(),username,surname,email,phone,password,city,locale,role,user_image_id);
    }

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

    @Rollback
    @Test
    public void testFindById(){
        //SetUp
        User aux = addUser(USERNAME,SURNAME,EMAIL,PHONE,PASSWORD,LOCALE,city,ROLE,KNOWN_IMAGE_ID);

        //Execute
        final Optional<User> user = userDao.findById(aux.getUserId());

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
        Assert.assertEquals(aux.getUserId(),user.get().getUserId());
    }

    @Rollback
    @Test
    public void testFindByEmail(){
        //SetUp
        User aux = addUser(USERNAME,SURNAME,EMAIL,PHONE,PASSWORD,LOCALE,city,ROLE,KNOWN_IMAGE_ID);

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
        Assert.assertEquals(aux.getUserId(),user.get().getUserId());
    }

    @Rollback
    @Test
    public void testChangeRole(){
        //SetUp
        User aux = addUser(USERNAME,SURNAME,EMAIL,PHONE,PASSWORD,LOCALE,city,ROLE,KNOWN_IMAGE_ID);

        final String TEST_ROLE = "DRIVER";
        //Execute
        userDao.changeRole(aux.getUserId(),TEST_ROLE);

        //Assert
        //We do this here to avoid depending in the method of the userDAO
        Optional<User> user = jdbcTemplate.query("SELECT * FROM users WHERE user_id=?",(rs,rn)->
                new User(rs.getLong("user_id"), rs.getString("username"),
                        rs.getString("surname"), rs.getString("email"),
                        rs.getString("phone"), rs.getString("password"),
                        new City(rs.getLong("city_id"),city.getName(),city.getProvinceId()),
                        new Locale(rs.getString("mail_locale")),
                        rs.getString("user_role"), rs.getLong("user_image_id")),aux.getUserId()).stream().findFirst();

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
        Assert.assertEquals(aux.getUserId(),user.get().getUserId());
    }
    @Rollback
    @Test
    public void testUpdateUser(){
        //SetUp
        User aux = addUser(USERNAME,SURNAME,EMAIL,PHONE,PASSWORD,LOCALE,city,ROLE,KNOWN_IMAGE_ID);
        final String TEST_NAME = "TestName";
        final String TEST_SURNAME = "TestSurname";
        final String TEST_PASSWORD = "Pass";
        final Locale TEST_LOCALE = Locale.UK;
        final String TEST_ROLE = "OTHER_ROLE";
        //Execute
        userDao.updateProfile(TEST_NAME,TEST_SURNAME,EMAIL,TEST_PASSWORD,city,TEST_LOCALE.toString(),TEST_ROLE,KNOWN_IMAGE_ID);

        //Assert
        //Hacemos esto para evitar depender de los metodos del userDao
        Optional<User> user = jdbcTemplate.query("SELECT * FROM users WHERE user_id=?",(rs,rn)->
                new User(rs.getLong("user_id"), rs.getString("username"),
                        rs.getString("surname"), rs.getString("email"),
                        rs.getString("phone"), rs.getString("password"),
                        new City(rs.getLong("city_id"),city.getName(),city.getProvinceId()),
                        new Locale(rs.getString("mail_locale")),
                        rs.getString("user_role"), rs.getLong("user_image_id")),aux.getUserId()).stream().findFirst();

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
        Assert.assertEquals(aux.getUserId(),user.get().getUserId());
    }
}

 */
