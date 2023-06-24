package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.models.*;
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
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CarDaoImplTest {

    @Autowired
    private CarHibernateDao carDaoImpl;

    @PersistenceContext
    private EntityManager em;

    private static final long KNOWN_USER_ID = 1;
    private static final long KNOWN_IMAGE_ID=1;

    private static final long KNOWN_CITY_ID = 1;

    private static final long CAR_ID = 1;
    private static final String PLATE = "AA000AA";

    private static final CarBrand BRAND = CarBrand.UNKNOWN;
    private static final String PLATE_2 = "BB000BB";

    private static final String UNKNOWN_PLATE = "CC000CC";
    private static final String INFO_CAR = "Fit azul";

    private User user = new User(1,"John","Doe","johndoe@mail.com","1234567800","1234",new City(1,"Recoleta",new Province(1,"CABA")),new Locale("en"),"USER",KNOWN_IMAGE_ID);


    @Rollback
    @Test
    public void testCreate(){
        final Car car = carDaoImpl.create(PLATE_2,INFO_CAR,user,KNOWN_IMAGE_ID,3,BRAND,new ArrayList<>());

        Assert.assertEquals(PLATE_2,car.getPlate());
        Assert.assertEquals(INFO_CAR,car.getInfoCar());
        Assert.assertEquals(user.getUserId(),car.getUser().getUserId());
        Assert.assertEquals(KNOWN_IMAGE_ID,car.getImage_id());
    }

    @Rollback
    @Test
    public void testFindByIdPresent(){

        //test
        Optional<Car> car = carDaoImpl.findById(CAR_ID);

        //assert
        Assert.assertTrue(car.isPresent());
        Assert.assertEquals(PLATE,car.get().getPlate());
        Assert.assertEquals(INFO_CAR, car.get().getInfoCar());
        Assert.assertEquals(KNOWN_USER_ID, car.get().getUser().getUserId());
        Assert.assertEquals(KNOWN_IMAGE_ID,car.get().getImage_id());
    }

    @Test
    @Rollback
    public void testFindByIdEmpty(){
        //No setup

        //test
        Optional<Car> car = carDaoImpl.findById(200);

        //assert
        Assert.assertFalse(car.isPresent());
    }

    @Test
    @Rollback
    public void testFindByPlateAndUserPresent(){
        //test
        Optional<Car> car = carDaoImpl.findByPlateAndUser(PLATE,user);

        //assert
        Assert.assertTrue(car.isPresent());
        Assert.assertEquals(PLATE,car.get().getPlate());
        Assert.assertEquals(INFO_CAR, car.get().getInfoCar());
        Assert.assertEquals(KNOWN_USER_ID, car.get().getUser().getUserId());
        Assert.assertEquals(KNOWN_IMAGE_ID,car.get().getImage_id());
    }

    @Test
    @Rollback
    public void testFindByPlateAndUserEmpty(){
        //No setup

        //test
        Optional<Car> car = carDaoImpl.findByPlateAndUser(UNKNOWN_PLATE,user);

        //assert
        Assert.assertFalse(car.isPresent());
    }

    @Test
    @Rollback
    public void testFindByUserEmpty(){
        //Setup
        User aux = new User(user.getUserId() + 1, user.getName(), user.getSurname(), user.getEmail(), user.getPhone(), user.getPassword(), user.getBornCity(),user.getMailLocale(),user.getRole(),user.getUserImageId());
        //test
        List<Car> cars = carDaoImpl.findByUser(aux);

        //assert
        Assert.assertTrue(cars.isEmpty());
    }

    @Rollback
    @Test
    public void testFindByUserPresent(){

        //test
        List<Car> cars= carDaoImpl.findByUser(user);

        //assert
        Assert.assertFalse(cars.isEmpty());
        Assert.assertEquals(2,cars.size());
        Assert.assertEquals(PLATE,cars.get(0).getPlate());
        Assert.assertEquals(INFO_CAR, cars.get(0).getInfoCar());
        Assert.assertEquals(KNOWN_USER_ID, cars.get(0).getUser().getUserId());
        Assert.assertEquals(KNOWN_IMAGE_ID,cars.get(0).getImage_id());
        Assert.assertEquals(PLATE_2,cars.get(1).getPlate());
        Assert.assertEquals(INFO_CAR, cars.get(1).getInfoCar());
        Assert.assertEquals(KNOWN_USER_ID, cars.get(1).getUser().getUserId());
        Assert.assertEquals(KNOWN_IMAGE_ID,cars.get(1).getImage_id());
    }

}
