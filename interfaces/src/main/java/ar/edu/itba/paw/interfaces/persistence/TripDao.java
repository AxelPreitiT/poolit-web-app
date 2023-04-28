package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripDao {

    Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final boolean isRecurrent, final double price, final int max_passengers, final User driver);
    boolean addPassenger(final Trip trip, final User passenger, final LocalDateTime startDateTime, final LocalDateTime endDateTime);
    List<User> getPassengers(final TripInstance tripInstance);
    List<User> getPassengers(final Trip trip, final LocalDateTime dateTime);
    List<User> getPassengers(final Trip trip, final LocalDateTime startDateTime, final LocalDateTime endDateTime);
    PagedContent<TripInstance> getTripInstances(final Trip trip,int page, int pageSize);
    PagedContent<TripInstance> getTripInstances(final Trip trip, int page, int pageSize, LocalDateTime start, LocalDateTime end);
    PagedContent<Trip> getTripsCreatedByUser(final User user,int page, int pageSize);
    PagedContent<Trip> getTripsWhereUserIsPassenger(final User user, int page, int pageSize);
    Optional<Trip> findById(long id);
    Optional<Trip> findById(long tripId, LocalDateTime start, LocalDateTime end);
    boolean deleteTrip(final Trip trip);
    PagedContent<Trip> getTripsWithFilters(
            long origin_city_id, long destination_city_id,
            LocalDateTime startDateTime, Optional<DayOfWeek> dayOfWeek, Optional<LocalDateTime> endDateTime,
            Optional<Double> minPrice, Optional<Double> maxPrice,
            int page, int pageSize);
    PagedContent<Trip> getIncomingTrips(int page, int pageSize);

}
