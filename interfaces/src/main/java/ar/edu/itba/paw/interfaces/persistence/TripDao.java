package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripDao {

    Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final boolean isRecurrent, final double price, final int max_passengers, final User driver);
    boolean addPassenger(final Trip trip, final Passenger passenger);
    boolean removePassenger(final Trip trip, final User user);
    List<Passenger> getPassengers(final TripInstance tripInstance);
    List<Passenger> getPassengers(final Trip trip, final LocalDateTime dateTime);
    List<Passenger> getPassengers(final Trip trip, final LocalDateTime startDateTime, final LocalDateTime endDateTime);
    Optional<Passenger> getPassenger(final Trip trip, final User user);
    Optional<Passenger> getPassenger(final long tripId, final User user);
    PagedContent<TripInstance> getTripInstances(final Trip trip,int page, int pageSize);
    PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end);
    PagedContent<Trip> getTripsCreatedByUser(final User user,Optional<LocalDateTime> minDateTime, Optional<LocalDateTime> maxDateTime,int page, int pageSize);
    PagedContent<Trip> getTripsWhereUserIsPassenger(final User user,Optional<LocalDateTime> minDateTime, Optional<LocalDateTime> maxDateTime, int page, int pageSize);
    Optional<Trip> findById(long id);
    Optional<Trip> findById(long tripId, LocalDateTime start, LocalDateTime end);
    boolean deleteTrip(final Trip trip);
    PagedContent<Trip> getTripsWithFilters(
            long origin_city_id, long destination_city_id,
            LocalDateTime startDateTime, Optional<DayOfWeek> dayOfWeek, Optional<LocalDateTime> endDateTime, int minutes,
            Optional<BigDecimal> minPrice, Optional<BigDecimal> maxPrice, Trip.SortType sortType, boolean descending,
            int page, int pageSize);
    PagedContent<Trip> getTripsByOriginAndStart(long origin_city_id, LocalDateTime startDateTime, int page, int pageSize);

}
