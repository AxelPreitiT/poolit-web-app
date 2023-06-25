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

    @Autowired
    private  ImageHibernateDao imageDao;

    private static final byte[] BYTE_ARRAY = new byte[]{1,2,3,4,5,6,7,8,9,10};
    private static final long IMAGE_ID = 1;

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

    @Test
    public void testFindByIdPresent(){

        //Execute
        final Optional<Image> image = imageDao.findById(IMAGE_ID);

        //Assert
        Assert.assertTrue(image.isPresent());
        Assert.assertEquals(IMAGE_ID,image.get().getImageId().longValue());
        Assert.assertNull(image.get().getData());
    }

    @Test
    public void testFindByIdEmpty(){
        //No setup

        //Execute
        final Optional<Image> image = imageDao.findById(4);

        //Assert
        Assert.assertFalse(image.isPresent());
    }

    @Test
    public void testFindByIdOfOther(){

        //Execute
        final Optional<Image> image = imageDao.findById(IMAGE_ID+1);

        //Assert
        Assert.assertFalse(image.isPresent());
    }

}
*/