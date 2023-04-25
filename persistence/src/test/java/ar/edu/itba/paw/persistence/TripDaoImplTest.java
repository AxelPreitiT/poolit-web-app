package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TripDaoImplTest {
    @Autowired
    private DataSource ds;

    @Autowired
    private TripDaoImpl tripDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "trips","trips_cars_drivers","passengers");
    }

    @Test
    public void testRun(){
        User user = new User(1,"jmentasti@itba.edu.ar","1139150686");
        City city = new City(1,"Recoleta",1);
        Trip aux = new Trip(
                1,
                city,
                "Av Callao",
                city,
                "Av Callao",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(8),
                4,
                user,
                new Car(1,"AE062TP","Honda Fit",user),
                0,
                10.0
        );
        PagedContent<TripInstance> ans = tripDao.getTripInstances(aux,0,10);
    }
}
