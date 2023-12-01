package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.persistence.ReportDao;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reports.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.services.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static ar.edu.itba.paw.models.reports.ReportOptions.OTHER;
import static ar.edu.itba.paw.models.reports.ReportRelations.DRIVER_2_PASSENGER;
import static ar.edu.itba.paw.models.reports.ReportState.IN_REVISION;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceImplTest {
    private static final City originCity = new City(1, "Agronomía", 1);
    private static final long tripId = 1L;
    private static final String originAddress = "ORIGIN ADDRESS";
    private static final City destinationCity = new City(1, "Agronomía", 1);
    private static final String destinationAddress = "DESTINATION ADDRESS";
    private static final LocalDateTime dateTime = LocalDateTime.now();
    private static final int maxSeats = 4;
    private static final User driver = new User(2, "DRIVER", "SURNAME", "driver@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "DRIVER", 1L);
    private static final User user = new User(1, "USER", "SURNAME", "user@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final Car car = new Car(1, "ABC123", "INFO", user, 1L);
    private static final BigDecimal price = BigDecimal.valueOf(10.0);
    private static final Trip trip = new Trip(tripId, originCity, originAddress, destinationCity, destinationAddress, dateTime, dateTime.plusDays(7), maxSeats, driver, car, 0, price.doubleValue());
    private static final User REPORTER = new User(1, "USER", "SURNAME", "EMAIL", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final User REPORTED = new User(2, "USER", "SURNAME", "EMAIL2", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final ReportState STATE = IN_REVISION;
    private static final ReportOptions REASON = OTHER;
    private static final ReportRelations RELATION = DRIVER_2_PASSENGER;
    private static final LocalDateTime DATE = LocalDateTime.now();
    private static final String REASONADMIN = "REASON ADMIN";
    //private static final User DRIVER = new User(3, "USER", "SURNAME", "EMAIL2", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    //private static final ItemReport<User> DRIVER_REPORT = new ItemReport<>(DRIVER, RELATION, STATE);
    //private static final ItemReport<User> REPORTER_REPORT = new ItemReport<>(REPORTER, RELATION, STATE);
    //private static final ItemReport<User> REPORTED_REPORT = new ItemReport<>(REPORTED, RELATION, STATE);
    //private static final List<ItemReport<Passenger>> LIST_REPORT = Arrays.asList(new ItemReport[]{REPORTER_REPORT, REPORTED_REPORT});
    //private static final TripReportCollection TRIP_REPORT_COLLECTION = new TripReportCollection(DRIVER_REPORT, LIST_REPORT);


    @Mock
    private TripService tripService;
    @Mock
    private UserService userService;
    @Mock
    private ReportDao reportDao;
    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    public void TestCreateReport() throws UserNotFoundException, TripNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(REPORTED));
        when(userService.getCurrentUser()).thenReturn(Optional.of(REPORTER));
        when(tripService.findById(anyLong())).thenReturn(Optional.of(trip));
        when(reportDao.createReport(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(new Report(REPORTER, REPORTED, trip, DESCRIPTION, DATE, RELATION, REASON));
        when(userService.getAdmins()).thenReturn(new ArrayList<>());

        Report resp = reportService.createReport(0, 1, DESCRIPTION, RELATION, REASON);

        Assert.assertNotNull(resp);
        Assert.assertEquals(REPORTER, resp.getReporter());
        Assert.assertEquals(DESCRIPTION, resp.getDescription());
        Assert.assertEquals(REPORTED, resp.getReported());
        Assert.assertEquals(RELATION, resp.getRelation());
        Assert.assertEquals(REASON, resp.getReason());
    }

    @Test(expected = TripNotFoundException.class)
    public void TestDontHaveTripCreateReport() throws UserNotFoundException, TripNotFoundException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(REPORTED));
        when(tripService.findById(anyLong())).thenReturn(Optional.empty());

        reportService.createReport(0, 1, DESCRIPTION, RELATION, REASON);

        Assert.fail();
    }

    @Test(expected = ReportNotFoundException.class)
    public void TestRejectReportDoesntExist() throws ReportNotFoundException {
        when(reportDao.findById(anyLong())).thenReturn(Optional.empty());

        reportService.rejectReport(1, REASONADMIN);

        Assert.fail();
    }

    @Test(expected = ReportNotFoundException.class)
    public void TestAcceptReportDoesntExist() throws TripNotFoundException, UserNotFoundException, ReportNotFoundException, PassengerNotFoundException {
        when(reportDao.findById(anyLong())).thenReturn(Optional.empty());

        reportService.acceptReport(1, REASONADMIN);

        Assert.fail();
    }

    @Test(expected = IllegalStateException.class)
    public void TestNotIncludeInTripGetTripReportCollection() throws PassengerNotFoundException, UserNotLoggedInException, TripNotFoundException {
        when(tripService.findById(anyLong())).thenReturn(Optional.of(trip));
        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        when(tripService.userIsDriver(anyLong(), any())).thenReturn(false);
        when(tripService.userIsPassenger(anyLong(), any())).thenReturn(false);

        reportService.getTripReportCollection(trip.getTripId());

        Assert.fail();
    }

}
