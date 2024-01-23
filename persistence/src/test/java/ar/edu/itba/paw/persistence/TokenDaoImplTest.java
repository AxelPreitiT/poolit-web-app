package ar.edu.itba.paw.persistence;

import antlr.Token;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Province;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.VerificationToken;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TokenDaoImplTest {

    @Autowired
    private TokenHibernateDao tokenDao;

    @PersistenceContext
    private EntityManager em;

    private static final long KNOWN_USER_ID = 1;
    private static final long KNOWN_IMAGE_ID=1;

    private static final long DEFAULT_TOKEN_ID = 1;

    private static final long DEFAULT_USER_ID = 2;
    private static final String TOKEN = "1234567890";
    private final User user = new User(1,"John","Doe","johndoe@mail.com","1234567800","1234",new City(1,"Recoleta",new Province(1,"CABA")),new Locale("en"),"USER",KNOWN_IMAGE_ID);

    private final User defaultUser = new User(2,"John","Doe","johndoe2@mail.com","1234567800","1234",new City(1,"Recoleta",new Province(1,"CABA")),new Locale("en"),"USER",KNOWN_IMAGE_ID);

    @Test
    @Rollback
    public void testCreate(){
        //Setup
        LocalDate date = LocalDate.now();
        //Execute
        VerificationToken token = tokenDao.createToken(user,TOKEN,date);
        //Assert
        Assert.assertEquals(user.getUserId(),token.getUser().getUserId());
        Assert.assertEquals(TOKEN,token.getToken());
        Assert.assertEquals(date,token.getDate());
    }

    @Test
    public void testGet(){
        //Execute
        Optional<VerificationToken> token = tokenDao.getToken(TOKEN);
        //Assert
        Assert.assertTrue(token.isPresent());
        Assert.assertEquals(TOKEN,token.get().getToken());
        Assert.assertEquals(DEFAULT_TOKEN_ID,token.get().getTokenId());
        Assert.assertEquals(DEFAULT_USER_ID,token.get().getUser().getUserId());
    }

//    @Test
//    @Rollback
//    public void testDelete(){
//        //SetUp
//        TypedQuery<VerificationToken> query = em.createQuery("from VerificationToken where token = :token",VerificationToken.class);
//        query.setParameter("token",TOKEN);
//        VerificationToken aux = query.getResultList().stream().findFirst().get();
//        //Execute
//        tokenDao.deleteToken(aux);
//        //Assert
//        Optional<VerificationToken> ans = query.getResultList().stream().findFirst();
//        Assert.assertFalse(ans.isPresent());
//    }

    @Test
    @Rollback
    public void testUpdate(){
        //Setup
        final LocalDate date = LocalDate.now();
        final String testToken = TOKEN + "ABCD";
        //Execute
        tokenDao.updateToken(testToken,defaultUser,date);

        //Assert
        TypedQuery<VerificationToken> query = em.createQuery("from VerificationToken where user.userId = :userId", VerificationToken.class);
        query.setParameter("userId",DEFAULT_USER_ID);
        VerificationToken ans = query.getResultList().stream().findFirst().get();
        Assert.assertEquals(date,ans.getDate());
        Assert.assertEquals(testToken,ans.getToken());
    }


}
