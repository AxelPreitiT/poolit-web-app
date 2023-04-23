package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.trips.TripInstance;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripDao {

    Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDateTime startDateTime, final LocalDateTime endDateTime, final boolean isRecurrent, final double price, final int max_passengers, final User driver);
    boolean addPassenger(final Trip trip, final User passenger, final LocalDateTime startDateTime, final LocalDateTime endDateTime);
    List<User> getPassengers(final TripInstance tripInstance);
    List<User> getPassengers(final Trip trip, final LocalDateTime dateTime);
    List<TripInstance> getTripInstances(final Trip trip);
    List<Trip> getTripsCreatedByUser(final User user);
    List<Trip> getTripsWhereUserIsPassenger(final User user);
    Optional<Trip> findById(long id);
    List<Trip> getTripsByDateTimeAndOriginAndDestination(long origin_city_id, long destination_city_id, Optional<LocalDateTime> dateTime);
    List<Trip> getFirstNTrips(long n);

}
