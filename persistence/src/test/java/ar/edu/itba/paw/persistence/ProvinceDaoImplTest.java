package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Province;
import ar.edu.itba.paw.persistence.config.TestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

/*
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProvinceDaoImplTest {

    @Autowired
    private ProvinceHibernateDao provinceDao;

    private static final int PROVINCE_ID = 1;

    private static final String PROVINCE_NAME="CABA";

    @Test
    public void testFindByIdEmpty(){
        //No Setup

        //Execute
        Optional<Province> province = provinceDao.findProvinceById(20);
        //Assert
        Assert.assertFalse(province.isPresent());
    }

    @Test
    public void testFindByIdPresent(){
        //Execute
        Optional<Province> province = provinceDao.findProvinceById(PROVINCE_ID);

        //Assert
        Assert.assertTrue(province.isPresent());
        Assert.assertEquals(PROVINCE_ID,province.get().getId());
        Assert.assertEquals(PROVINCE_NAME,province.get().getName());
    }

    @Test
    public void testFindAllProvinces(){
        //SetUp
        Province[] provinces = new Province[]{
                new Province(1,"CABA"),
                new Province(2,"Buenos Aires"),
                new Province(3,"Santa Fe"),
                new Province(4,"Neuquen"),
                new Province(5,"Misiones")
        };
        //Execute
        List<Province> ans = provinceDao.getAllProvinces();

        //Assert
        for(Province province: provinces){
            Assert.assertTrue(ans.stream().anyMatch(p -> p.getId() == province.getId() && p.getName().equals(province.getName())));
        }
        Assert.assertEquals(provinces.length,ans.size());
    }

}
*/