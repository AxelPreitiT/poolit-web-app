package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.models.reports.ReportOptions;
import ar.edu.itba.paw.models.reports.ReportRelations;
import ar.edu.itba.paw.models.reports.ReportState;
import ar.edu.itba.paw.models.reviews.PassengerReviewOptions;
import ar.edu.itba.paw.models.trips.Trip;
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
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class ReportDaoImplTest {
    @Autowired
    private ReportHibernateDao reportDao;
    @PersistenceContext
    private EntityManager em;

    private static final long PROVINCE_ID = 1;
    private static final long CITY_ID = 1;
    private static final City CITY = new City(CITY_ID,"Recoleta",PROVINCE_ID);
    private static final long KNOWN_IMAGE_ID = 3;
    private static final long USER_ID_1 = 3;
    private static final long USER_ID_2 = 4;
    private static final String USER_1_EMAIL = "jonhdoe@mail.com";
    private static final String USER_2_EMAIL = "jonhdoe2@mail.com";
    private static final Locale USER_LOCALE = Locale.ENGLISH;
    private static final String USER_ROLE_1 = "USER";
    private static final String USER_ROLE_2 = "DRIVER";
    private static final String USER_NAME = "John";
    private static final String USER_SURNAME = "Doe";
    private static final String USER_PHONE = "1234567800";
    private static final String USER_PASSWORD = "1234";
    private static final User USER_1 = new User(USER_ID_1,USER_NAME,USER_SURNAME,USER_1_EMAIL,USER_PHONE,USER_PASSWORD,CITY,USER_LOCALE,USER_ROLE_1, KNOWN_IMAGE_ID);
    private static final User USER_2 = new User(USER_ID_2,USER_NAME,USER_SURNAME,USER_2_EMAIL,USER_PHONE,USER_PASSWORD,CITY,USER_LOCALE,USER_ROLE_2, KNOWN_IMAGE_ID);
    private static final long CAR_ID_1 = 3;
    private static final Car CAR_1 = new Car(CAR_ID_1,"AA000AA","Fit Azul",USER_1, KNOWN_IMAGE_ID);
    private static final LocalDateTime START = LocalDateTime.of(2023,7,3,23,30,0);
    private static final LocalDateTime END = LocalDateTime.of(2023,7,17,23,30,0);
    private static final long KNOWN_RECURRENT_TRIP_ID = 4;
    private static final Trip TRIP_2 = new Trip(KNOWN_RECURRENT_TRIP_ID,CITY,"Av Callao 1348",CITY,"ITBA",START,END,3,USER_1,CAR_1,0,1200.0);
    private static final Passenger PASSENGER_1 = new Passenger(USER_1,TRIP_2,START,START);
    private static final Passenger PASSENGER_2 = new Passenger(USER_2,TRIP_2,START,END);
    private static final long KNOWN_REPORT_ID = 3;
    private static final String DESCRIPTION = "He did not do the trip";
    private static final ReportOptions REASON = ReportOptions.MISCONDUCT;
    private static final ReportRelations RELATION = ReportRelations.PASSENGER_2_DRIVER;
    private static final ReportState STATE = ReportState.IN_REVISION;
    private static final PassengerReviewOptions PASSENGER_REVIEW_OPTION = PassengerReviewOptions.VERY_FRIENDLY;
    private static final int PAGE_SIZE = 10;

    @Test
    @Rollback
    public void testCreateReport(){
        //Execute
        Report ans = reportDao.createReport(USER_2,USER_1,TRIP_2,DESCRIPTION,LocalDateTime.now(),RELATION,REASON);
        //Assert
        TypedQuery<Report> query = em.createQuery("from Report where id = :reportId",Report.class);
        query.setParameter("reportId",ans.getReportId());
        Optional<Report> res = query.getResultList().stream().findFirst();
        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(USER_2.getUserId(),res.get().getReporter().getUserId());
        Assert.assertEquals(USER_1.getUserId(),res.get().getReported().getUserId());
        Assert.assertEquals(TRIP_2.getTripId(),res.get().getTrip().getTripId());
        Assert.assertEquals(DESCRIPTION,ans.getDescription());
        Assert.assertEquals(RELATION,ans.getRelation());
        Assert.assertEquals(REASON,ans.getReason());
        Assert.assertEquals(res.get(),ans);
    }

    @Test
    public void testFindById(){
        //Execute
        Optional<Report> ans = reportDao.findById(KNOWN_REPORT_ID);
        //Assert
        Assert.assertTrue(ans.isPresent());
        Assert.assertEquals(USER_2.getUserId(),ans.get().getReporter().getUserId());
        Assert.assertEquals(USER_1.getUserId(),ans.get().getReported().getUserId());
        Assert.assertEquals(DESCRIPTION,ans.get().getDescription());
        Assert.assertEquals(RELATION,ans.get().getRelation());
        Assert.assertEquals(REASON,ans.get().getReason());
    }

    @Test
    public void testFindByIdEmpty(){
        //Execute
        Optional<Report> ans = reportDao.findById(100);
        //Assert
        Assert.assertFalse(ans.isPresent());
    }

    @Test
    @Rollback
    public void testResolveReport(){
        //Setup
        final String reason = "The driver did not complete the trip";
        //Execute
        reportDao.resolveReport(KNOWN_REPORT_ID,reason,ReportState.APPROVED);
        //Assert
        TypedQuery<Report> query = em.createQuery("from Report where reportId = :reportId",Report.class);
        query.setParameter("reportId",KNOWN_REPORT_ID);
        Optional<Report> ans = query.getResultList().stream().findFirst();
        Assert.assertTrue(ans.isPresent());
        Assert.assertEquals(ReportState.APPROVED,ans.get().getStatus());
        Assert.assertEquals(reason,ans.get().getAdminReason());
    }

    @Test
    @Rollback
    public void testRejectReport(){
        //Setup
        final String reason = "The driver did not complete the trip";
        //Execute
        reportDao.resolveReport(KNOWN_REPORT_ID,reason,ReportState.REJECTED);
        //Assert
        TypedQuery<Report> query = em.createQuery("from Report where reportId = :reportId",Report.class);
        query.setParameter("reportId",KNOWN_REPORT_ID);
        Optional<Report> ans = query.getResultList().stream().findFirst();
        Assert.assertTrue(ans.isPresent());
        Assert.assertEquals(ReportState.REJECTED,ans.get().getStatus());
        Assert.assertEquals(reason,ans.get().getAdminReason());
    }

    @Test
    public void testGetReports(){
        //Execute
        PagedContent<Report> ans = reportDao.getReports(0,PAGE_SIZE);
        //Assert
        Assert.assertEquals(1,ans.getTotalCount());
        Assert.assertEquals(USER_2.getUserId(),ans.getElements().get(0).getReporter().getUserId());
        Assert.assertEquals(USER_1.getUserId(),ans.getElements().get(0).getReported().getUserId());
        Assert.assertEquals(DESCRIPTION,ans.getElements().get(0).getDescription());
        Assert.assertEquals(RELATION,ans.getElements().get(0).getRelation());
        Assert.assertEquals(REASON,ans.getElements().get(0).getReason());
    }

    @Test
    public void testGetReportByTripAndUsersPresent(){
        //Execute
        Optional<Report> ans = reportDao.getReportByTripAndUsers(TRIP_2.getTripId(),USER_ID_2,USER_ID_1);
        //Assert
        Assert.assertTrue(ans.isPresent());
    }

    @Test
    public void testGetReportByTripAndUsersEmpty(){
        //Execute
        Optional<Report> ans = reportDao.getReportByTripAndUsers(TRIP_2.getTripId(),USER_ID_1,USER_ID_2);
        //Assert
        Assert.assertFalse(ans.isPresent());
    }

    @Test
    public void testGetReportsMadeByUserOnTrip(){
        //Setup
        Trip auxTrip = em.merge(TRIP_2);
        User auxUser = em.merge(USER_2);
        //Execute
        PagedContent<Report> ans = reportDao.getReportsMadeByUserOnTrip(auxUser,auxTrip,0,PAGE_SIZE);
        //Assert
        Assert.assertEquals(1,ans.getTotalCount());
        Assert.assertEquals(KNOWN_REPORT_ID,ans.getElements().get(0).getReportId());
    }

    @Test
    public void testGetReportsMadeByUserOnTripEmpty(){
        //Setup
        Trip auxTrip = em.merge(TRIP_2);
        User auxUser = em.merge(USER_1);
        //Execute
        PagedContent<Report> ans = reportDao.getReportsMadeByUserOnTrip(auxUser,auxTrip,0,PAGE_SIZE);
        //Assert
        Assert.assertEquals(0,ans.getTotalCount());
        Assert.assertTrue(ans.getElements().isEmpty());
    }

}
