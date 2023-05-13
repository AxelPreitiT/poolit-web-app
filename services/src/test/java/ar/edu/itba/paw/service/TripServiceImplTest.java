package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.services.EmailServiceImpl;
import ar.edu.itba.paw.services.TripServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceImplTest {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private static final User user = new User(1, "USER", "SURNAME", "user@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final City originCity = new City(1, "Agronomía", 1);
    private static final City destinationCity = new City(1, "Agronomía", 1);
    private static final String originAddress = "ORIGIN ADDRESS";
    private static final String destinationAddress = "DESTINATION ADDRESS";
    private static final Car car = new Car(1, "ABC123", "INFO", user, 1L);
    private static final LocalDate startDate = LocalDate.parse("02/05/2023", DATE_FORMAT);
    private static final LocalTime startTime = LocalTime.parse("10:00", TIME_FORMAT);
    private static final BigDecimal price = BigDecimal.valueOf(10.0);
    private static final int maxSeats = 4;
    private static final User driver = new User(2, "DRIVER", "SURNAME", "driver@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "DRIVER", 1L);
    ;
    private static final LocalDate endDate = LocalDate.parse("02/05/2023", DATE_FORMAT);
    private static final LocalTime endTime = LocalTime.parse("10:00", TIME_FORMAT);
    private static final long tripId = 1L;


    @Mock
    private TripDao tripDao;
    @InjectMocks
    private TripServiceImpl tripService;

    @Mock
    private EmailService emailService;

    @Test
    public void TestCreateTrip(){
        // precondiciones
        when(tripDao.create(eq(originCity), eq(originAddress), eq(destinationCity), eq(destinationAddress), eq(car), any(), any(), eq(false), eq(price.doubleValue()), eq(maxSeats), eq(driver)))
                .thenReturn(new Trip(1, originCity, originAddress, destinationCity, destinationAddress, LocalDateTime.now(), LocalDateTime.now(), maxSeats, driver, car, 0, price.doubleValue()));

        // ejercitar la clase
        Trip newTrip = tripService.createTrip(originCity, originAddress, destinationCity, destinationAddress, car, startDate, startTime, price, maxSeats, driver, endDate, endTime);

        // assertions
        Assert.assertNotNull(newTrip);
        Assert.assertEquals(tripId, newTrip.getTripId());
    }


}
