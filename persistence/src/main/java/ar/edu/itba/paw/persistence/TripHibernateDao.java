package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.TripDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class TripHibernateDao implements TripDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TripHibernateDao.class);
    @PersistenceContext
    private EntityManager em;
    @Override
    public Trip create(City originCity, String originAddress, City destinationCity, String destinationAddress, Car car, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean isRecurrent, double price, int max_passengers, User driver) {
        Trip trip = new Trip(originCity,originAddress,destinationCity,destinationAddress,startDateTime,endDateTime,max_passengers,driver,car,price);
        LOGGER.debug("Adding new trip from '{}' to '{}', with startDate '{}' and endDate '{}', and created by driver with id {} to the database",originCity,destinationCity,startDateTime,endDateTime,driver.getUserId());
        em.persist(trip);
        LOGGER.info("Trip added to the database with id {}",trip.getTripId());
        LOGGER.debug("New {}", trip);
        return trip;
    }

    @Override
    public boolean addPassenger(Trip trip, Passenger passenger) {
        return false;
    }

    @Override
    public boolean removePassenger(Trip trip, User user) {
        return false;
    }

    @Override
    public List<Passenger> getPassengers(TripInstance tripInstance) {
        return new ArrayList<>();
    }

    @Override
    public List<Passenger> getPassengers(Trip trip, LocalDateTime dateTime) {
        return new ArrayList<>();
    }

    @Override
    public List<Passenger> getPassengers(Trip trip, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new ArrayList<>();
    }

    @Override
    public Optional<Passenger> getPassenger(Trip trip, User user) {
        return Optional.empty();
    }

    @Override
    public Optional<Passenger> getPassenger(long tripId, User user) {
        return Optional.empty();
    }

    @Override
    public PagedContent<TripInstance> getTripInstances(Trip trip, int page, int pageSize) {
        return new PagedContent<>(new ArrayList<>(), 0, 0, 0);
    }

    @Override
    public PagedContent<TripInstance> getTripInstances(Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end) {
        return new PagedContent<>(new ArrayList<>(), 0, 0, 0);
    }

    @Override
    public PagedContent<Trip> getTripsCreatedByUser(User user, Optional<LocalDateTime> minDateTime, Optional<LocalDateTime> maxDateTime, int page, int pageSize) {
        return new PagedContent<>(new ArrayList<>(), 0, 0, 0);
    }

    @Override
    public PagedContent<Trip> getTripsWhereUserIsPassenger(User user, Optional<LocalDateTime> minDateTime, Optional<LocalDateTime> maxDateTime, int page, int pageSize) {
        return new PagedContent<>(new ArrayList<>(), 0, 0, 0);
    }

    @Override
    public Optional<Trip> findById(long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Trip> findById(long tripId, LocalDateTime start, LocalDateTime end) {
        return Optional.empty();
    }

    @Override
    public boolean deleteTrip(Trip trip) {
        return false;
    }

    @Override
    public PagedContent<Trip> getTripsWithFilters(long origin_city_id, long destination_city_id, LocalDateTime startDateTime, Optional<DayOfWeek> dayOfWeek, Optional<LocalDateTime> endDateTime, int minutes, Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice, Trip.SortType sortType, boolean descending, int page, int pageSize) {
        return new PagedContent<>(new ArrayList<>(), 0, 0, 0);
    }

    @Override
    public PagedContent<Trip> getTripsByOriginAndStart(long origin_city_id, LocalDateTime startDateTime, int page, int pageSize) {
        return new PagedContent<>(new ArrayList<>(), 0, 0, 0);
    }
}
