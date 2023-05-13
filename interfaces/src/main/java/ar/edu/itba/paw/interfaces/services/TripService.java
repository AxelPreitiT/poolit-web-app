package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface TripService {
    //
    Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDate startDate, final LocalTime startTime,final BigDecimal price, final int maxSeats, User driver, final LocalDate endDate, final LocalTime endTime);
    //
    Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDate date, final LocalTime time,final BigDecimal price, final int maxSeats, User driver);
    //
    boolean addPassenger(Trip trip, User passenger, LocalDateTime dateTime);
    //
    boolean addPassenger(Trip trip, User passenger, LocalDateTime startDateTime, LocalDateTime endDateTime);
    //
    boolean addPassenger(long tripId, User passenger, LocalDateTime startDateTime, LocalDateTime endDateTime);
    //
    boolean addPassenger(long tripId, User passenger, LocalDateTime dateTime);
    boolean removePassenger(final Trip trip, final User passenger);
    //
    Optional<Trip> findById(long id);
    //
    Optional<Trip> findById(long id, LocalDateTime start, LocalDateTime end);
    //
    Optional<Trip> findById(long id, LocalDateTime dateTime);

    Optional<Trip> findById(long id, String startDate, String startTime, String endDate);
    boolean userIsDriver(final long tripId, final User user);
    boolean userIsPassenger(final long tripId, final User user);

    Optional<Passenger> getPassenger(final long tripId, final User user);
    Optional<Passenger> getPassenger(final Trip trip, final User user);
    //
    List<Passenger> getPassengers(Trip trip, LocalDateTime dateTime);
    //
    List<Passenger> getPassengers(TripInstance tripInstance);

    //
    List<Passenger> getPassengers(Trip trip);
    //
    PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize);
    //
    PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end);
    //
    PagedContent<Trip> getTripsCreatedByUserFuture(final User user, int page, int pageSize);
    PagedContent<Trip> getTripsCreatedByUserPast(final User user, int page, int pageSize);
    PagedContent<Trip> getTripsWhereUserIsPassengerFuture(final User user, int page, int pageSize);
    PagedContent<Trip> getTripsWhereUserIsPassengerPast(final User user, int page, int pageSize);
    //
    //
    PagedContent<Trip> getIncomingTrips(int page, int pageSize);
    //
    PagedContent<Trip> getIncomingTripsByOrigin(long origin_city_id, int page, int pageSize);
    //

    boolean deleteTrip(final Trip trip);
    boolean addPassenger(Trip trip,User passenger, String startDate,String startTime, String endDate);
    boolean deleteTrip(int tripId);
    //
    PagedContent<Trip> getTripsByDateTimeAndOriginAndDestination(
            long origin_city_id, long destination_city_id, final String startDate,
            final String startTime, final String endDate, final String endTime,
            final int page, final int pageSize);
    //
    PagedContent<Trip> getRecommendedTripsForUser(User user, int page, int pageSize);
    //
    PagedContent<Trip> getTripsByDateTimeAndOriginAndDestinationAndPrice(
            long origin_city_id, long destination_city_id, final LocalDate startDate,
            final LocalTime startTime, final LocalDate endDate, final LocalTime endTime,
            final Optional<BigDecimal> minPrice, final Optional<BigDecimal> maxPrice, final String sortType, final boolean descending,
            final int page, final int pageSize);

}
