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

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProvinceDaoImplTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProvinceHibernateDao provinceDao;

    private JdbcTemplate jdbcTemplate;

    private static final int PROVINCE_ID = 1;

    private static final String PROVINCE_NAME="CABA";

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Test
    public void testFindByIdEmpty(){
        //No Setup

        //Execute
        Optional<Province> province = provinceDao.findProvinceById(1);
        //Assert
        Assert.assertFalse(province.isPresent());
    }

    @Rollback
    @Test
    public void testFindByIdPresent(){
        //SetUp
        jdbcTemplate.update("INSERT INTO provinces VALUES(?,?)",PROVINCE_ID,PROVINCE_NAME);
        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate,"provinces");
        //Execute
        Optional<Province> province = provinceDao.findProvinceById(PROVINCE_ID);

        //Assert
        Assert.assertTrue(province.isPresent());
        Assert.assertEquals(PROVINCE_ID,province.get().getId());
        Assert.assertEquals(PROVINCE_NAME,province.get().getName());
        Assert.assertEquals(count,JdbcTestUtils.countRowsInTable(jdbcTemplate,"provinces"));
    }

    @Rollback
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
        for(Province province : provinces){
            jdbcTemplate.update("INSERT INTO provinces VALUES(?,?)",province.getId(),province.getName());
        }
        int count = JdbcTestUtils.countRowsInTable(jdbcTemplate,"provinces");

        //Execute
        List<Province> ans = provinceDao.getAllProvinces();

        //Assert
        for(Province province: provinces){
            Assert.assertTrue(ans.stream().anyMatch(p -> p.getId() == province.getId() && p.getName().equals(province.getName())));
        }
        Assert.assertEquals(provinces.length,ans.size());
        Assert.assertEquals(count,JdbcTestUtils.countRowsInTable(jdbcTemplate,"provinces"));
    }

}
