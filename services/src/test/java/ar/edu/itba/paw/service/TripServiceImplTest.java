package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.services.CarServiceImpl;
import ar.edu.itba.paw.services.CityServiceImpl;
import ar.edu.itba.paw.services.TripServiceImpl;
import ar.edu.itba.paw.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceImplTest {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    private static final User user = new User(1, "USER", "SURNAME", "user@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "USER", 1L);
    private static final City originCity = new City(1, "Agronomía", 1);
    private static final long originCityId = 1;
    private static final City destinationCity = new City(1, "Agronomía", 1);
    private static final long destinationCityId = 1;
    private static final String originAddress = "ORIGIN ADDRESS";
    private static final String destinationAddress = "DESTINATION ADDRESS";
    private static final Car car = new Car(1, "ABC123", "INFO", user, 1L);
    private static final long carId = 1;
    private static final LocalDate startDate = LocalDate.parse("02/05/2023", DATE_FORMAT);
    private static final LocalTime startTime = LocalTime.parse("10:00", TIME_FORMAT);
    private static final BigDecimal price = BigDecimal.valueOf(10.0);
    private static final int maxSeats = 4;
    private static final User driver = new User(2, "DRIVER", "SURNAME", "driver@gmail.com", "PHONE", "PASSWORD", new City(1, "Agronomía", 1), new Locale("es"), "DRIVER", 1L);
    ;
    private static final LocalDate endDate = LocalDate.parse("02/05/2023", DATE_FORMAT);
    private static final LocalTime endTime = LocalTime.parse("10:00", TIME_FORMAT);
    private static final long tripId = 1L;
    private static final LocalDateTime dateTime = LocalDateTime.now();
    private static final Trip trip = new Trip(tripId, originCity, originAddress, destinationCity, destinationAddress, dateTime, dateTime.plusDays(7), maxSeats, driver, car, 0, price.doubleValue());
    private static final Trip not_started_trip = new Trip(tripId, originCity, originAddress, destinationCity, destinationAddress, dateTime.plusMinutes(20), dateTime.plusDays(7), maxSeats, driver, car, 0, price.doubleValue());

    private static final Trip tripErrorDate = new Trip(tripId, originCity, originAddress, destinationCity, destinationAddress, dateTime, dateTime.minusDays(1), maxSeats, driver, car, 0, price.doubleValue());

    private static final Passenger passenger = new Passenger(user, trip, dateTime, dateTime.plusDays(7));


    @Mock
    private TripDao tripDao;
    @Mock
    private EmailService emailService;
    @Mock
    private CityServiceImpl cityService;
    @Mock
    private CarServiceImpl carService;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private TripServiceImpl tripService;



    @Test
    public void testCreateTrip() throws Exception {
        // precondiciones
        when(cityService.findCityById(originCityId)).thenReturn(Optional.of(originCity));
        when(cityService.findCityById(destinationCityId)).thenReturn(Optional.of(destinationCity));
        when(carService.findById(anyLong())).thenReturn(Optional.of(car));
        when(userService.getCurrentUser()).thenReturn(Optional.of(driver));
        when(tripDao.create(eq(originCity), eq(originAddress), eq(destinationCity), eq(destinationAddress), eq(car), any(), any(), eq(false), eq(price.doubleValue()), eq(maxSeats), eq(driver)))
                .thenReturn(trip);
        doNothing().when(emailService).sendMailNewTrip(any());

        // ejercitar la clase
        Trip newTrip = tripService.createTrip(originCityId, originAddress, destinationCityId, destinationAddress, carId, startDate, startTime, price, maxSeats, endDate, endTime);

        // assertions
        Assert.assertNotNull(newTrip);
        Assert.assertEquals(tripId, newTrip.getTripId());
        Assert.assertEquals(originCityId,newTrip.getOriginCity().getId());
        Assert.assertEquals(destinationCityId,newTrip.getDestinationCity().getId());
        Assert.assertEquals(carId,newTrip.getCar().getCarId());
        Assert.assertEquals(driver.getUserId(),newTrip.getDriver().getUserId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTripLessThanWeek() throws Exception {
        // precondiciones
        when(cityService.findCityById(originCityId)).thenReturn(Optional.of(originCity));
        when(cityService.findCityById(destinationCityId)).thenReturn(Optional.of(destinationCity));
        when(carService.findById(anyLong())).thenReturn(Optional.of(car));
        when(userService.getCurrentUser()).thenReturn(Optional.of(driver));

        // ejercitar la clase
        tripService.createTrip(originCityId, originAddress, destinationCityId, destinationAddress, carId, startDate, startTime, price, maxSeats, startDate.plusDays(2), endTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTripInvalidDates() throws Exception {
        // precondiciones
        when(cityService.findCityById(originCityId)).thenReturn(Optional.of(originCity));
        when(cityService.findCityById(destinationCityId)).thenReturn(Optional.of(destinationCity));
        when(carService.findById(anyLong())).thenReturn(Optional.of(car));
        when(userService.getCurrentUser()).thenReturn(Optional.of(driver));

        // ejercitar la clase
        tripService.createTrip(originCityId, originAddress, destinationCityId, destinationAddress, carId, startDate, startTime, price, maxSeats, startDate.minusDays(2), endTime);
    }

    @Test(expected = TripNotFoundException.class)
    public void testDeleteForNonExistentTrip() throws TripNotFoundException {
        // precondiciones
        when(tripDao.findById(anyLong())).thenReturn(Optional.empty());

        // ejercitar la clase
        tripService.deleteTrip(tripId);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteTripAfterEnded() throws IllegalArgumentException, TripNotFoundException {
        // precondiciones
        when(tripDao.findById(anyLong())).thenReturn(Optional.of(tripErrorDate));

        // ejercitar la clase
        tripService.deleteTrip(tripId);

        // assertions
        Assert.fail();
    }

    @Test
    public void testDeleteTripNotStarted() throws Exception {
        when(tripDao.findById(anyLong())).thenReturn(Optional.of(not_started_trip));
        when(tripDao.getPassengers(any(Trip.class),any(),any())).thenReturn(Collections.singletonList(passenger));

        tripService.deleteTrip(tripId);

        verify(emailService,times(1)).sendMailTripDeletedToPassenger(any(),any());
        verify(tripDao,times(1)).rejectPassenger(any());
        verify(emailService,times(1)).sendMailTripDeletedToDriver(any());
        verify(tripDao,times(1)).markTripAsDeleted(any(),any());
    }


    @Test
    public void testDeleteTripStarted() throws Exception {
        when(tripDao.findById(anyLong())).thenReturn(Optional.of(trip));
        when(tripDao.getPassengers(any(Trip.class),any(),any())).thenReturn(Collections.singletonList(passenger));

        tripService.deleteTrip(tripId);

        verify(emailService,times(1)).sendMailTripTruncatedToPassenger(any(),any(),any());
        verify(tripDao,times(1)).truncatePassengerEndDateTime(any(),any());
        verify(emailService,times(1)).sendMailTripDeletedToDriver(any());
        verify(tripDao,times(1)).markTripAsDeleted(any(),any());
    }


    @Test(expected = TripAlreadyStartedException.class)
    public void testAddCurrentUserForStartedTrip() throws UserNotFoundException, TripAlreadyStartedException, TripNotFoundException, NotAvailableSeatsException {
        when(tripDao.findById(anyLong())).thenReturn(Optional.of(trip));
        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        when(tripDao.getPassengers(any(),any(),any())).thenReturn(Collections.emptyList());

        tripService.addCurrentUserAsPassenger(tripId, startDate, endDate);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCurrentUserAlreadyPassenger() throws UserNotFoundException, TripAlreadyStartedException, TripNotFoundException, NotAvailableSeatsException {
        when(tripDao.findById(anyLong())).thenReturn(Optional.of(trip));
        when(userService.getCurrentUser()).thenReturn(Optional.of(user));
        when(tripDao.getPassengers(any(),any(),any())).thenReturn(Collections.singletonList(passenger));

        tripService.addCurrentUserAsPassenger(tripId, startDate, endDate);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemovePassengerEnded() throws UserNotFoundException, PassengerNotFoundException, TripNotFoundException {
        when(tripDao.findById(anyLong())).thenReturn(Optional.of(trip));
        when(userService.findById(anyLong())).thenReturn(Optional.of(user));
        when(tripDao.getPassenger(any(Trip.class),any(User.class))).thenReturn(Optional.of(new Passenger(user,trip,dateTime,dateTime)));

        tripService.removePassenger(tripId,user.getUserId());
    }

    @Test
    public void testIfUserCanGetPassengersDriver() throws TripNotFoundException {
        when(tripDao.findById(anyLong())).thenReturn(Optional.of(trip));

        boolean ans = tripService.checkIfUserCanGetPassengers(1,driver,dateTime,dateTime, Passenger.PassengerState.ACCEPTED);

        Assert.assertTrue(ans);
    }

    @Test
    public void testIfUserCanGetPassengersNotAccepted() throws TripNotFoundException {
        when(tripDao.findById(anyLong())).thenReturn(Optional.of(trip));
        when(tripDao.getPassenger(any(Trip.class),any(User.class))).thenReturn(Optional.of(passenger));
        boolean ans = tripService.checkIfUserCanGetPassengers(1,user,dateTime,dateTime, Passenger.PassengerState.PENDING);

        Assert.assertFalse(ans);
    }

    @Test
    public void testIfUserCanGetPassengersNotInRange() throws TripNotFoundException {
        when(tripDao.findById(anyLong())).thenReturn(Optional.of(trip));
        when(tripDao.getPassenger(any(Trip.class),any(User.class))).thenReturn(Optional.of(passenger));
        boolean ans = tripService.checkIfUserCanGetPassengers(1,user,dateTime,dateTime.plusDays(14), Passenger.PassengerState.ACCEPTED);

        Assert.assertFalse(ans);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAcceptPassengerAlreadyStarted() throws UserNotFoundException, PassengerAlreadyProcessedException, PassengerNotFoundException, NotAvailableSeatsException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(user));
        when(tripDao.getPassenger(anyLong(),any())).thenReturn(Optional.of(passenger));

        tripService.acceptOrRejectPassenger(tripId,user.getUserId(), Passenger.PassengerState.ACCEPTED);
    }

    @Test(expected = NotAvailableSeatsException.class)
    public void testAcceptPassengerWithoutSeats() throws UserNotFoundException, PassengerAlreadyProcessedException, PassengerNotFoundException, NotAvailableSeatsException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(user));
        when(tripDao.getPassenger(anyLong(),any())).thenReturn(Optional.of(new Passenger(user, trip, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(8))));
        when(tripDao.getTripSeatCount(anyLong(),any(),any())).thenReturn(trip.getMaxSeats()+1);

        tripService.acceptOrRejectPassenger(tripId,user.getUserId(), Passenger.PassengerState.ACCEPTED);

    }

    @Test(expected = PassengerAlreadyProcessedException.class)
    public void testAcceptPassengerAlreadyProcessed() throws UserNotFoundException, PassengerAlreadyProcessedException, PassengerNotFoundException, NotAvailableSeatsException {
        when(userService.findById(anyLong())).thenReturn(Optional.of(user));
        Passenger aux = new Passenger(user, trip, dateTime, dateTime.plusDays(7));
        aux.setPassengerState(Passenger.PassengerState.ACCEPTED);
        when(tripDao.getPassenger(anyLong(),any())).thenReturn(Optional.of(aux));

        tripService.acceptOrRejectPassenger(tripId,user.getUserId(), Passenger.PassengerState.ACCEPTED);

    }

}
