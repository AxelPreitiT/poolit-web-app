package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ImageDaoImplTest {

    @Autowired
    private  ImageHibernateDao imageDao;

    private static final byte[] BYTE_ARRAY = new byte[]{1,2,3,4,5,6,7,8,9,10};
    private static final long IMAGE_ID = 3;
    private static final long IMAGE_ID_2 = 4;

    private static final Image IMAGE = new Image(IMAGE_ID,BYTE_ARRAY);
    private static final Image IMAGE_2 = new Image(IMAGE_ID_2,BYTE_ARRAY);

    @PersistenceContext
    private EntityManager em;

    @Rollback
    @Test
    public void testCreate(){
        //No setup

        //Execute
        final Image image = imageDao.create(BYTE_ARRAY);

        //Assert
        TypedQuery<Image> query = em.createQuery("from Image where id = :imageId",Image.class);
        query.setParameter("imageId",image.getImageId());
        Optional<Image> res = query.getResultList().stream().findFirst();
        Assert.assertTrue(res.isPresent());
        Assert.assertArrayEquals(BYTE_ARRAY,res.get().getData());
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
        final Optional<Image> image = imageDao.findById(200);

        //Assert
        Assert.assertFalse(image.isPresent());
    }

    @Rollback
    @Test
    public void testUpdate(){
        //Setup
        Image auxImage = em.merge(IMAGE);
        //Execute
        Image ans = imageDao.update(auxImage,BYTE_ARRAY);
        //Assert
        TypedQuery<Image> query = em.createQuery("from Image where id = :imageId",Image.class);
        query.setParameter("imageId",auxImage.getImageId());
        Optional<Image> res = query.getResultList().stream().findFirst();
        Assert.assertTrue(res.isPresent());
        Assert.assertArrayEquals(BYTE_ARRAY,res.get().getData());
        Assert.assertEquals(auxImage.getImageId(),ans.getImageId());
    }

    @Rollback
    @Test
    public void testDelete(){
        //Setup
        Image auxImage = em.merge(IMAGE_2);
        //Execute
        imageDao.delete(auxImage);
        //Assert
        TypedQuery<Image> query = em.createQuery("from Image where id = :imageId",Image.class);
        query.setParameter("imageId",auxImage.getImageId());
        Optional<Image> res = query.getResultList().stream().findFirst();
        Assert.assertFalse(res.isPresent());
    }

}