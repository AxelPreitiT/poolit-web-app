package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Image;
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
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ImageDaoImplTest {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private  ImageHibernateDao imageDao;

    private JdbcTemplate jdbcTemplate;
//    private SimpleJdbcInsert jdbcInsert;

    private static final byte[] BYTE_ARRAY = new byte[]{1,2,3,4,5,6,7,8,9,10};
    private static final long IMAGE_ID = 1;
    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
//        jdbcInsert = new SimpleJdbcInsert(dataSource)
//                .withTableName("images")
//                .usingGeneratedKeyColumns("image_id");
    }

    @Rollback
    @Test
    public void testCreate(){
        //No setup

        //Execute
        final Image image = imageDao.create(BYTE_ARRAY);

        //Assert
        Assert.assertArrayEquals(BYTE_ARRAY,image.getData());
        Assert.assertTrue(image.getImageId()>0);
    }

    @Rollback
    @Test
    public void testFindByIdPresent(){
        //Setup
        String query = "INSERT INTO images(image_id,bytea) VALUES(?,?)";
        em.createNativeQuery(query)
                .setParameter(1,IMAGE_ID)
                .setParameter(2,BYTE_ARRAY)
                .executeUpdate();
        em.flush();
//        final Map<String,Object> data = new HashMap<>();
//        data.put("image_id",IMAGE_ID);
//        data.put("bytea",BYTE_ARRAY);
//        Number key = jdbcInsert.executeAndReturnKey(data);

        //Execute
        final Optional<Image> image = imageDao.findById(IMAGE_ID);

        //Assert
        Assert.assertTrue(image.isPresent());
        Assert.assertEquals(IMAGE_ID,image.get().getImageId().longValue());
        Assert.assertArrayEquals(BYTE_ARRAY,image.get().getData());
    }

    @Test
    public void testFindByIdEmpty(){
        //No setup

        //Execute
        final Optional<Image> image = imageDao.findById(4);

        //Assert
        Assert.assertFalse(image.isPresent());
    }

    @Rollback
    @Test
    public void testFindByIdOfOther(){
        //Setup
        String query = "INSERT INTO images(image_id,bytea) VALUES(?,?)";
        em.createNativeQuery(query)
                .setParameter(1,IMAGE_ID)
                .setParameter(2,BYTE_ARRAY)
                .executeUpdate();
        em.flush();

        //Execute
        final Optional<Image> image = imageDao.findById(IMAGE_ID+1);

        //Assert
        Assert.assertFalse(image.isPresent());
    }

}
*/