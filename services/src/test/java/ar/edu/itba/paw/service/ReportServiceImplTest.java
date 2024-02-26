package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.persistence.ReportDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.reports.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.services.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static ar.edu.itba.paw.models.reports.ReportOptions.OTHER;
import static ar.edu.itba.paw.models.reports.ReportRelations.*;
import static ar.edu.itba.paw.models.reports.ReportState.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceImplTest {
    private static final City originCity = new City(1, "Agronomía", 1);
    private static final long tripId = 1L;
    private static final String originAddress = "ORIGIN ADDRESS";
    private static final City destinationCity = new City(1, "Agronomía", 1);
    private static final String destinationAddress = "DESTINATION ADDRESS";
    private static final LocalDateTime dateTime = LocalDateTime.now();
    private static final int maxSeats = 4;
    private static final User USER_1 = new User(1, "USER", "SURNAME", "EMAIL", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final User USER_2 = new User(2, "USER", "SURNAME", "EMAIL2", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final User DRIVER = new User(3,"USER", "SURNAME", "EMAIL2", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final Car car = new Car(1, "ABC123", "INFO", DRIVER, 1L);
    private static final BigDecimal price = BigDecimal.valueOf(10.0);
    private static final Trip TRIP = new Trip(tripId, originCity, originAddress, destinationCity, destinationAddress, dateTime, dateTime.plusDays(7), maxSeats, DRIVER, car, 0, price.doubleValue());
    private static final Passenger PASSENGER_1 =new Passenger(USER_1, TRIP, TRIP.getStartDateTime(), TRIP.getEndDateTime());
    private static final Passenger PASSENGER_2 =new Passenger(USER_2, TRIP, TRIP.getStartDateTime(), TRIP.getEndDateTime());
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final ReportState STATE = IN_REVISION;
    private static final ReportOptions REASON = OTHER;
    private static final LocalDateTime DATE = LocalDateTime.now();
    private static final String REASON_ADMIN = "REASON ADMIN";
    private static final Report REPORT = new Report(USER_1,DRIVER, TRIP, DESCRIPTION, DATE, PASSENGER_2_PASSENGER, REASON);

    @Mock
    private TripService tripService;
    @Mock
    private UserService userService;
    @Mock
    private ReportDao reportDao;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private ReportServiceImpl reportService;


    @Before
    public void setup(){
        PASSENGER_1.setPassengerState(Passenger.PassengerState.ACCEPTED);
        PASSENGER_2.setPassengerState(Passenger.PassengerState.ACCEPTED);
    }
    @Test
    public void TestCreateReportDriverToPassenger() throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(USER_1));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(DRIVER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER_1));
        when(reportDao.getReportByTripAndUsers(anyLong(),anyLong(),anyLong())).thenReturn(Optional.empty());
        when(reportDao.createReport(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(new Report(DRIVER,USER_1, TRIP, DESCRIPTION, DATE, DRIVER_2_PASSENGER, REASON));
        when(userService.getAdmins()).thenReturn(Collections.emptyList());

        Report resp = reportService.createReport(0, 1, DESCRIPTION, DRIVER_2_PASSENGER, REASON);

        Assert.assertNotNull(resp);
        Assert.assertEquals(DRIVER, resp.getReporter());
        Assert.assertEquals(DESCRIPTION, resp.getDescription());
        Assert.assertEquals(USER_1, resp.getReported());
        Assert.assertEquals(DRIVER_2_PASSENGER, resp.getRelation());
        Assert.assertEquals(REASON, resp.getReason());

    }

    @Test(expected = PassengerNotFoundException.class)
    public void TestCreateReportDriverToNonPassenger() throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(USER_1));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(DRIVER));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.empty());

        reportService.createReport(0, 1, DESCRIPTION, DRIVER_2_PASSENGER, REASON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestCreateReportNonDriverToPassenger() throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(USER_1));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER_2));

        reportService.createReport(0, 1, DESCRIPTION, DRIVER_2_PASSENGER, REASON);
    }

    @Test
    public void TestCreateReportPassengerToDriver() throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(DRIVER));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER_1));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER_1));
        when(reportDao.getReportByTripAndUsers(anyLong(),anyLong(),anyLong())).thenReturn(Optional.empty());
        when(reportDao.createReport(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(new Report(USER_1,DRIVER, TRIP, DESCRIPTION, DATE, PASSENGER_2_DRIVER, REASON));
        when(userService.getAdmins()).thenReturn(Collections.emptyList());

        Report resp = reportService.createReport(0, 1, DESCRIPTION, PASSENGER_2_DRIVER, REASON);

        Assert.assertNotNull(resp);
        Assert.assertEquals(USER_1, resp.getReporter());
        Assert.assertEquals(DESCRIPTION, resp.getDescription());
        Assert.assertEquals(DRIVER, resp.getReported());
        Assert.assertEquals(PASSENGER_2_DRIVER, resp.getRelation());
        Assert.assertEquals(REASON, resp.getReason());

    }

    @Test(expected = PassengerNotFoundException.class)
    public void TestCreateReportNonPassengerToDriver() throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(DRIVER));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER_1));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.empty());

        reportService.createReport(0, 1, DESCRIPTION, PASSENGER_2_DRIVER, REASON);

    }

    @Test(expected = IllegalArgumentException.class)
    public void TestCreateReportPassengerToNonDriver() throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(USER_2));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER_1));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER_1));

        reportService.createReport(0, 1, DESCRIPTION, PASSENGER_2_DRIVER, REASON);

    }

    @Test(expected = IllegalArgumentException.class)
    public void TestCreateReportPassengerToDriverAlreadyMade() throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(DRIVER));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER_1));
        when(tripService.getPassenger(any(),any())).thenReturn(Optional.of(PASSENGER_1));
        when(reportDao.getReportByTripAndUsers(anyLong(),anyLong(),anyLong())).thenReturn(Optional.of(new Report(USER_1,DRIVER, TRIP, DESCRIPTION, DATE, PASSENGER_2_DRIVER, REASON)));

        reportService.createReport(0, 1, DESCRIPTION, PASSENGER_2_DRIVER, REASON);
    }

    @Test
    public void TestCreateReportPassengerToPassenger() throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(USER_2));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER_1));
        when(tripService.getPassenger(any(),eq(USER_1))).thenReturn(Optional.of(PASSENGER_1));
        when(tripService.getPassenger(any(),eq(USER_2))).thenReturn(Optional.of(PASSENGER_2));
        when(reportDao.getReportByTripAndUsers(anyLong(),anyLong(),anyLong())).thenReturn(Optional.empty());
        when(reportDao.createReport(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(new Report(USER_1,DRIVER, TRIP, DESCRIPTION, DATE, PASSENGER_2_PASSENGER, REASON));
        when(userService.getAdmins()).thenReturn(Collections.emptyList());

        Report resp = reportService.createReport(0, 1, DESCRIPTION, PASSENGER_2_PASSENGER, REASON);

        Assert.assertNotNull(resp);
        Assert.assertEquals(USER_1, resp.getReporter());
        Assert.assertEquals(DESCRIPTION, resp.getDescription());
        Assert.assertEquals(DRIVER, resp.getReported());
        Assert.assertEquals(PASSENGER_2_PASSENGER, resp.getRelation());
        Assert.assertEquals(REASON, resp.getReason());

    }

    @Test(expected = PassengerNotFoundException.class)
    public void TestCreateReportPassengerToNonPassenger() throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(USER_2));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER_1));
        when(tripService.getPassenger(any(),eq(USER_2))).thenReturn(Optional.empty());

        reportService.createReport(0, 1, DESCRIPTION, PASSENGER_2_PASSENGER, REASON);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestCreateReportPassengerToPassengerAlreadyMade() throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(USER_2));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(TRIP));
        when(userService.getCurrentUser()).thenReturn(Optional.of(USER_1));
        when(tripService.getPassenger(any(),eq(USER_1))).thenReturn(Optional.of(PASSENGER_1));
        when(tripService.getPassenger(any(),eq(USER_2))).thenReturn(Optional.of(PASSENGER_2));
        when(reportDao.getReportByTripAndUsers(anyLong(),anyLong(),anyLong())).thenReturn(Optional.of(new Report(USER_1,DRIVER, TRIP, DESCRIPTION, DATE, PASSENGER_2_PASSENGER, REASON)));

        reportService.createReport(0, 1, DESCRIPTION, PASSENGER_2_PASSENGER, REASON);
    }

    @Test(expected = ReportNotFoundException.class)
    public void testAcceptOrRejectNonExistentReport() throws UserNotFoundException, PassengerNotFoundException, ReportAlreadyProcessedException, TripNotFoundException, ReportNotFoundException {
        when(reportDao.findById(anyLong())).thenReturn(Optional.empty());

        reportService.acceptOrRejectReport(1,REJECTED,REASON_ADMIN);
    }

    @Test
    public void testAcceptReport() throws UserNotFoundException, PassengerNotFoundException, TripNotFoundException, ReportAlreadyProcessedException, ReportNotFoundException {
        when(reportDao.findById(anyLong())).thenReturn(Optional.of(REPORT));
        doNothing().when(reportDao).resolveReport(anyLong(),anyString(),any());
        doNothing().when(emailService).sendMailAcceptReport(any());
        doNothing().when(emailService).sendMailBanReport(any());
        when(tripService.getTripsCreatedByUserFuture(any(),eq(0),anyInt())).thenReturn(new PagedContent<>(Collections.singletonList(TRIP),0,10,1));
        when(tripService.getTripsCreatedByUserFuture(any(),eq(1),anyInt())).thenReturn(PagedContent.emptyPagedContent());
        when(tripService.getTripsWhereUserIsPassengerFuture(any(),eq(0),anyInt())).thenReturn(new PagedContent<>(Collections.singletonList(TRIP),0,10,1));
        when(tripService.getTripsWhereUserIsPassengerFuture(any(),eq(1),anyInt())).thenReturn(PagedContent.emptyPagedContent());

        reportService.acceptOrRejectReport(1,APPROVED,REASON_ADMIN);

        verify(tripService,times(1)).deleteTrip(1L);
        verify(tripService,times(1)).removePassenger(1L,REPORT.getReported().getUserId());
        verify(userService,times(1)).banUser(REPORT.getReported());
    }

}
