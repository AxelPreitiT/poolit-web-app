package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
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
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TripDaoImplTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TripHibernateDao tripDao;

    private static final long PROVINCE_ID = 1;
    private static final long CITY_ID = 1;
    private static final String CITY_NAME = "Recoleta";
    private static final City CITY = new City(CITY_ID,CITY_NAME,PROVINCE_ID);
    private static final long KNOWN_IMAGE_ID = 1;
    private static final long USER_ID_1 = 1;
    private static final long USER_ID_2 = 2;
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
    private static final long CAR_ID_1 = 1;
    private static final long CAR_ID_2 = 2;
    private static final String CAR_PLATE = "BB000BB";
    private static final String CAR_INFO = "Fit Azul";
    private static final Car CAR_1 = new Car(CAR_ID_1,"AA000AA",CAR_INFO,USER_1, KNOWN_IMAGE_ID);
    private static final Car CAR_2 = new Car(CAR_ID_2,"BB000BB",CAR_INFO,USER_1, KNOWN_IMAGE_ID);

    private static final String ORIGIN_ADDRESS = "Av Callao 1348";
    private static final String DESTINATION_ADDRESS = "ITBA";
    private static final LocalDateTime START = LocalDateTime.of(2023,7,3,23,30,0);
    private static final LocalDateTime END = LocalDateTime.of(2023,7,17,23,30,0);
    private static final double PRICE = 1200.0;
    private static final int MAX_SEATS = 3;
    private static final int PAGE_SIZE = 10;
    private static final long KNOWN_SINGLE_TRIP_ID = 1;
    private static final long KNOWN_RECURRENT_TRIP_ID = 2;

    private static final Trip TRIP_1 = new Trip(KNOWN_SINGLE_TRIP_ID,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,START,MAX_SEATS,USER_1,CAR_1,0,PRICE);

    private static final Trip TRIP_2 = new Trip(KNOWN_RECURRENT_TRIP_ID,CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,START,END,MAX_SEATS,USER_1,CAR_1,0,PRICE);

    private static final Passenger PASSENGER_1 = new Passenger(USER_1,TRIP_2,START,START);

    private static final Passenger PASSENGER_2 = new Passenger(USER_2,TRIP_2,START,END);

    private Trip getTrip(Trip trip){
//        TypedQuery<Trip> auxTripQuery = em.createQuery("from Trip where tripId = :tripId",Trip.class);
//        auxTripQuery.setParameter("tripId",trip.getTripId());
//        return auxTripQuery.getResultList().stream().findFirst().get();
        return em.merge(trip);
    }
    private User getUser(User user){
//        TypedQuery<User> auxUserQuery = em.createQuery("from User where userId = :userId",User.class);
//        auxUserQuery.setParameter("userId",user.getUserId());
//        return auxUserQuery.getResultList().stream().findFirst().get();
        return em.merge(user);
    }

    private Passenger getPassenger(Passenger passenger){
//        TypedQuery<Passenger> auxUserQuery = em.createQuery("from Passenger where user.userId = :userId AND trip.tripId = :tripId",Passenger.class);
//        auxUserQuery.setParameter("userId",user.getUserId());
//        auxUserQuery.setParameter("tripId",trip.getTripId());
//        return auxUserQuery.getResultList().stream().findFirst().get();
        return em.merge(passenger);
    }

    @Rollback
    @Test
    public void testCreateTripSimple(){
        //Execute
        Trip aux = tripDao.create(CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,CAR_2,START,START,false,PRICE,MAX_SEATS,USER_2);

        //Assert
        Assert.assertEquals(CITY_ID,aux.getOriginCity().getId());
        Assert.assertEquals(START,aux.getStartDateTime());
        Assert.assertEquals(START,aux.getEndDateTime());
        Assert.assertEquals(CITY_NAME,aux.getOriginCity().getName());
        Assert.assertEquals(PROVINCE_ID,aux.getOriginCity().getProvinceId());
        Assert.assertEquals(ORIGIN_ADDRESS,aux.getOriginAddress());
        Assert.assertEquals(DESTINATION_ADDRESS,aux.getDestinationAddress());
        Assert.assertEquals(CITY_ID,aux.getDestinationCity().getId());
        Assert.assertEquals(CITY_NAME,aux.getDestinationCity().getName());
        Assert.assertEquals(PROVINCE_ID,aux.getDestinationCity().getProvinceId());
        Assert.assertEquals(CAR_ID_2,aux.getCar().getCarId());
        Assert.assertEquals(CAR_PLATE,aux.getCar().getPlate());
        Assert.assertEquals(USER_ID_2,aux.getDriver().getUserId());
        Assert.assertEquals(MAX_SEATS,aux.getMaxSeats());
        Assert.assertFalse(aux.isRecurrent());
        assertEquals(0, Double.compare(PRICE, aux.getPrice()));
    }

    @Rollback
    @Test
    public void testCreateTripRecurrent(){
        //Execute
        Trip aux = tripDao.create(CITY,ORIGIN_ADDRESS,CITY,DESTINATION_ADDRESS,CAR_2,START,END,true,PRICE,MAX_SEATS,USER_2);

        //Assert
        Assert.assertEquals(CITY_ID,aux.getOriginCity().getId());
        Assert.assertEquals(START,aux.getStartDateTime());
        Assert.assertEquals(END,aux.getEndDateTime());
        Assert.assertEquals(CITY_NAME,aux.getOriginCity().getName());
        Assert.assertEquals(PROVINCE_ID,aux.getOriginCity().getProvinceId());
        Assert.assertEquals(ORIGIN_ADDRESS,aux.getOriginAddress());
        Assert.assertEquals(DESTINATION_ADDRESS,aux.getDestinationAddress());
        Assert.assertEquals(CITY_ID,aux.getDestinationCity().getId());
        Assert.assertEquals(CITY_NAME,aux.getDestinationCity().getName());
        Assert.assertEquals(PROVINCE_ID,aux.getDestinationCity().getProvinceId());
        Assert.assertEquals(CAR_ID_2,aux.getCar().getCarId());
        Assert.assertEquals(CAR_PLATE,aux.getCar().getPlate());
        Assert.assertEquals(USER_ID_2,aux.getDriver().getUserId());
        Assert.assertEquals(MAX_SEATS,aux.getMaxSeats());
        Assert.assertTrue(aux.isRecurrent());
        assertEquals(0, Double.compare(PRICE, aux.getPrice()));
    }

    @Test
    public void testFindById(){
        //Execute
        Optional<Trip> trip = tripDao.findById(TRIP_1.getTripId());
        //Assert
        Assert.assertTrue(trip.isPresent());
        Assert.assertEquals(TRIP_1.getTripId(),trip.get().getTripId());
        Assert.assertEquals(0,trip.get().getOccupiedSeats());
        Assert.assertEquals(TRIP_1.getStartDateTime(),trip.get().getStartDateTime());
        Assert.assertEquals(TRIP_1.getEndDateTime(),trip.get().getEndDateTime());
        Assert.assertEquals(TRIP_1.getStartDateTime(),trip.get().getQueryStartDateTime());
        Assert.assertEquals(TRIP_1.getEndDateTime(),trip.get().getQueryEndDateTime());
    }

    @Test
    public void testFindByIdInRange(){
        //Execute
        Optional<Trip> ans = tripDao.findById(TRIP_2.getTripId(),START.plusDays(7),END);
        //Assert
        Assert.assertTrue(ans.isPresent());
        Assert.assertEquals(1,ans.get().getOccupiedSeats());
        Assert.assertEquals(START,ans.get().getStartDateTime());
        Assert.assertEquals(END,ans.get().getEndDateTime());
        Assert.assertEquals(START.plusDays(7),ans.get().getQueryStartDateTime());
        Assert.assertEquals(END,ans.get().getQueryEndDateTime());
    }

    @Rollback
    @Test
    public void testAddPassenger(){
        //SetUp
        Trip auxTrip = getTrip(TRIP_1);
        User auxUser = getUser(USER_2);

        //Execute and Assert
        Assert.assertTrue(tripDao.addPassenger(auxTrip,auxUser,START,START));
        TypedQuery<Passenger> query = em.createQuery("from Passenger where trip.tripId = :tripId AND user.userId = :userId",Passenger.class);
        query.setParameter("tripId",TRIP_1.getTripId());
        query.setParameter("userId",USER_2.getUserId());
        Optional<Passenger> ans = query.getResultList().stream().findFirst();
        Assert.assertTrue(ans.isPresent());
        Assert.assertEquals(Passenger.PassengerState.PENDING,ans.get().getPassengerState());
        Assert.assertEquals(START,ans.get().getStartDateTime());
        Assert.assertEquals(START,ans.get().getEndDateTime());
    }

    @Rollback
    @Test
    public void testRemovePassenger(){
        //SetUp
        Trip auxTrip = getTrip(TRIP_2);
        Passenger auxPassenger = getPassenger(PASSENGER_1);
        //Execute and Assert
        Assert.assertTrue(tripDao.removePassenger(auxTrip,auxPassenger));
        TypedQuery<Passenger> query = em.createQuery("from Passenger where trip.tripId = :tripId AND user.userId = :userId",Passenger.class);
        query.setParameter("tripId",auxPassenger.getTrip().getTripId());
        query.setParameter("userId",auxPassenger.getUser().getUserId());
        Assert.assertFalse(query.getResultList().stream().findFirst().isPresent());
    }

    @Test
    @Rollback
    public void testAcceptPassenger(){
        //Setup
        Passenger auxPassenger = getPassenger(PASSENGER_1);
        //Execute and Assert
        Assert.assertTrue(tripDao.acceptPassenger(auxPassenger));
        TypedQuery<Passenger> query = em.createQuery("from Passenger where trip.tripId = :tripId AND user.userId = :userId",Passenger.class);
        query.setParameter("tripId",PASSENGER_1.getTrip().getTripId());
        query.setParameter("userId",PASSENGER_1.getUser().getUserId());
        Optional<Passenger> ans = query.getResultList().stream().findFirst();
        Assert.assertTrue(ans.isPresent());
        Assert.assertEquals(Passenger.PassengerState.ACCEPTED,ans.get().getPassengerState());
    }

    @Test
    @Rollback
    public void testRejectPassenger(){
        //Setup
        Passenger auxPassenger = getPassenger(PASSENGER_1);
        //Execute and Assert
        Assert.assertTrue(tripDao.rejectPassenger(auxPassenger));
        TypedQuery<Passenger> query = em.createQuery("from Passenger where trip.tripId = :tripId AND user.userId = :userId",Passenger.class);
        query.setParameter("tripId",PASSENGER_1.getTrip().getTripId());
        query.setParameter("userId",PASSENGER_1.getUser().getUserId());
        Optional<Passenger> ans = query.getResultList().stream().findFirst();
        Assert.assertTrue(ans.isPresent());
        Assert.assertEquals(Passenger.PassengerState.REJECTED,ans.get().getPassengerState());
    }
    @Test
    public void testGetPassengersForDate(){
        //Setup
        Trip auxTrip = getTrip(TRIP_2);
        //Execute
        List<Passenger> ans = tripDao.getPassengers(auxTrip,START);
        //Assert
        Assert.assertEquals(2,ans.size());
        Assert.assertTrue(ans.stream().anyMatch(p -> p.getUser().getUserId() == USER_ID_1));
        Assert.assertTrue(ans.stream().anyMatch(p -> p.getUser().getUserId() == USER_ID_2));
    }

    @Test
    public void testGetPassengersForRange(){
        //Setup
        Trip auxTrip = getTrip(TRIP_2);
        //Execute
        List<Passenger> ans = tripDao.getPassengers(auxTrip,START.plusDays(7),END);
        //Assert
        Assert.assertEquals(1,ans.size());
        Assert.assertTrue(ans.stream().anyMatch(p -> p.getUser().getUserId() == USER_ID_2));
    }

    @Test
    public void testGetAcceptedPagedPassengers(){
        //Setup
        Trip auxTrip = getTrip(TRIP_2);
        //Execute
        PagedContent<Passenger> ans = tripDao.getPassengers(auxTrip,START,START,Optional.of(Passenger.PassengerState.ACCEPTED),0,PAGE_SIZE);
        //Assert
        Assert.assertEquals(2,ans.getTotalCount());
    }

    @Test
    public void testGetPendingPagedPassengers(){
        //Setup
        Trip auxTrip = getTrip(TRIP_2);
        //Execute
        PagedContent<Passenger> ans = tripDao.getPassengers(auxTrip,START,START,Optional.of(Passenger.PassengerState.PENDING),0,PAGE_SIZE);
        //Assert
        Assert.assertEquals(0,ans.getTotalCount());
    }

    @Test
    public void testGetPassenger(){
        //Setup
        Trip auxTrip = getTrip(TRIP_2);
        User auxUser = getUser(USER_1);
        //Execute
        Optional<Passenger> ans = tripDao.getPassenger(auxTrip,auxUser);
        //Assert
        Assert.assertTrue(ans.isPresent());
        Assert.assertEquals(USER_1.getUserId(),ans.get().getUser().getUserId());
        Assert.assertEquals(TRIP_2.getTripId(),ans.get().getTrip().getTripId());
    }

    @Test
    public void testGetTripSeatCount(){
        //Assert and execute
        Assert.assertEquals(1,tripDao.getTripSeatCount(TRIP_2.getTripId(),START.plusDays(7),END));
    }

    @Test
    public void testGetTripsCreatedByUser(){
        //Setup
        User auxUser = getUser(USER_1);
        //Execute
        PagedContent<Trip> ans = tripDao.getTripsCreatedByUser(auxUser,Optional.empty(),Optional.empty(),0,PAGE_SIZE);
        //Asssert
        Assert.assertEquals(2,ans.getTotalCount());
        Assert.assertTrue(ans.getElements().stream().anyMatch(t -> t.getTripId() == KNOWN_SINGLE_TRIP_ID));
        Assert.assertTrue(ans.getElements().stream().anyMatch(t -> t.getTripId() == KNOWN_RECURRENT_TRIP_ID));
    }

    @Test
    public void testGetTripsWhereUserIsPassenger(){
        //Setup
        User auxUser = getUser(USER_1);
        //Execute
        PagedContent<Trip> ans = tripDao.getTripsWhereUserIsPassenger(auxUser,Optional.empty(),Optional.empty(),null,0,PAGE_SIZE);
        //Assert
        Assert.assertEquals(1,ans.getTotalCount());
        Assert.assertTrue(ans.getElements().stream().anyMatch(t -> t.getTripId() == KNOWN_RECURRENT_TRIP_ID));
    }

    @Test
    @Rollback
    public void testDeleteTrip(){
        //Setup
        Trip auxTrip = getTrip(TRIP_1);
        //Execute and Assert
        Assert.assertTrue(tripDao.deleteTrip(auxTrip));
        TypedQuery<Trip> auxTripQuery = em.createQuery("from Trip where tripId = :tripId",Trip.class);
        auxTripQuery.setParameter("tripId",auxTrip.getTripId());
        Assert.assertFalse(auxTripQuery.getResultList().stream().findFirst().isPresent());
    }

    @Test
    @Rollback
    public void testMarkTripAsDeleted(){
        //Setup
        Trip auxTrip = getTrip(TRIP_1);
        //Execute and Assert
        Assert.assertTrue(tripDao.markTripAsDeleted(auxTrip,END));
        TypedQuery<Trip> auxTripQuery = em.createQuery("from Trip where tripId = :tripId",Trip.class);
        auxTripQuery.setParameter("tripId",auxTrip.getTripId());
        Optional<Trip> ans = auxTripQuery.getResultList().stream().findFirst();
        Assert.assertTrue(ans.isPresent());
        Assert.assertEquals(END,ans.get().getLastOccurrence());
        Assert.assertTrue(ans.get().getDeleted());
    }

    @Test
    @Rollback
    public void testTruncatePassengerEndDateTime(){
        //Setup
        Passenger auxPassenger = getPassenger(PASSENGER_2);
        //Execute
        tripDao.truncatePassengerEndDateTime(auxPassenger,START);
        //Assert
        TypedQuery<Passenger> query = em.createQuery("from Passenger where trip.tripId = :tripId AND user.userId = :userId",Passenger.class);
        query.setParameter("tripId",PASSENGER_2.getTrip().getTripId());
        query.setParameter("userId",PASSENGER_2.getUser().getUserId());
        Optional<Passenger> ans = query.getResultList().stream().findFirst();
        Assert.assertTrue(ans.isPresent());
        Assert.assertEquals(START,ans.get().getStartDateTime());
        Assert.assertEquals(START,ans.get().getEndDateTime());
    }

    @Test
    public void testGetAcceptedPassengers(){
        //Setup
        Trip auxTrip = getTrip(TRIP_2);
        //Execute
        List<Passenger> ans = tripDao.getAcceptedPassengers(auxTrip,auxTrip.getStartDateTime(),auxTrip.getEndDateTime());
        //Assert
        Assert.assertEquals(2,ans.size());
        Assert.assertTrue(ans.stream().anyMatch(p -> p.getUser().getUserId() == USER_ID_1));
        Assert.assertTrue(ans.stream().anyMatch(p -> p.getUser().getUserId() == USER_ID_2));
    }
}

