package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CarDao;
import ar.edu.itba.paw.models.Car;
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
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CarDaoImplTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CarDaoImpl carDaoImpl;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    private static final long KNOWN_USER_ID = 1;
    private static final long KNOWN_IMAGE_ID=1;

    private static final long KNOWN_CITY_ID = 1;

    private static final int CAR_ID = 1;
    private static final String PLATE = "AE062TP";
    private static final String PLATE_2 = "AE026TE";
    private static final String INFO_CAR = "Honda Fit azul";

    private User user;


    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("cars")
                .usingGeneratedKeyColumns("car_id");
        jdbcTemplate.update("INSERT INTO provinces VALUES(?,?)",1,"CABA");
        jdbcTemplate.update("INSERT INTO cities values (?,?,?)",KNOWN_CITY_ID,"Recoleta",1);
        jdbcTemplate.update("INSERT INTO images values (?,?)",KNOWN_IMAGE_ID,null);
        LocalDateTime birthDate = LocalDateTime.now();
        jdbcTemplate.update("INSERT INTO users VALUES (?,?,?,?,?,?,?,?,?,?)",KNOWN_USER_ID,"Poolit","Trips","poolit@gmail.com","1139150686","", Timestamp.valueOf(birthDate),KNOWN_CITY_ID,"DRIVER",KNOWN_IMAGE_ID);
        user = new User(KNOWN_USER_ID,"Poolit","Trips","poolit@gmail.com","1139150686","",birthDate,new City(KNOWN_CITY_ID,"",1),"USER",KNOWN_IMAGE_ID);
    }

    @Rollback
    @Test
    public void testCreate(){
        final Car car = carDaoImpl.create(PLATE,INFO_CAR,user,KNOWN_IMAGE_ID);

        Assert.assertEquals(PLATE,car.getPlate());
        Assert.assertEquals(INFO_CAR,car.getInfoCar());
        Assert.assertEquals(user.getUserId(),car.getUser().getUserId());
        Assert.assertEquals(KNOWN_IMAGE_ID,car.getImage_id());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate,"cars"));
    }

    @Rollback
    @Test
    public void testFindByIdPresent(){
        //setup
        final Map<String,Object> data = new HashMap<>();
        data.put("plate",PLATE);
        data.put("info_car",INFO_CAR);
        data.put("user_id",KNOWN_USER_ID);
        data.put("image_id",KNOWN_IMAGE_ID);
        long key = jdbcInsert.executeAndReturnKey(data).longValue();

        //test
        Optional<Car> car = carDaoImpl.findById(key);

        //assert
        Assert.assertTrue(car.isPresent());
        Assert.assertEquals(PLATE,car.get().getPlate());
        Assert.assertEquals(INFO_CAR, car.get().getInfoCar());
        Assert.assertEquals(KNOWN_USER_ID, car.get().getUser().getUserId());
        Assert.assertEquals(KNOWN_IMAGE_ID,car.get().getImage_id());
    }

    @Test
    public void testFindByIdEmpty(){
        //No setup

        //test
        Optional<Car> car = carDaoImpl.findById(CAR_ID);

        //assert
        Assert.assertFalse(car.isPresent());
    }

    @Test
    @Rollback
    public void testFindByPlateAndUserPresent(){
        //setup
        final Map<String,Object> data = new HashMap<>();
        data.put("car_id",CAR_ID);
        data.put("plate",PLATE);
        data.put("info_car",INFO_CAR);
        data.put("user_id",KNOWN_USER_ID);
        data.put("image_id",KNOWN_IMAGE_ID);
        jdbcInsert.execute(data);

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
    public void testFindByPlateAndUserEmpty(){
        //No setup

        //test
        Optional<Car> car = carDaoImpl.findByPlateAndUser(PLATE,user);

        //assert
        Assert.assertFalse(car.isPresent());
    }

    @Test
    public void testFindByUserEmpty(){
        //No setup

        //test
        List<Car> cars = carDaoImpl.findByUser(user);

        //assert
        Assert.assertTrue(cars.isEmpty());
    }

    @Rollback
    @Test
    public void testFindByUserPresent(){
        //Setup
        final Map<String,Object> data = new HashMap<>();
        data.put("car_id",CAR_ID);
        data.put("plate",PLATE);
        data.put("info_car",INFO_CAR);
        data.put("user_id",KNOWN_USER_ID);
        data.put("image_id",KNOWN_IMAGE_ID);
        jdbcInsert.execute(data);

        //test
        List<Car> cars= carDaoImpl.findByUser(user);

        //assert
        Assert.assertFalse(cars.isEmpty());
        Assert.assertEquals(1,cars.size());
        Assert.assertEquals(PLATE,cars.get(0).getPlate());
        Assert.assertEquals(INFO_CAR, cars.get(0).getInfoCar());
        Assert.assertEquals(KNOWN_USER_ID, cars.get(0).getUser().getUserId());
        Assert.assertEquals(KNOWN_IMAGE_ID,cars.get(0).getImage_id());
    }
}
