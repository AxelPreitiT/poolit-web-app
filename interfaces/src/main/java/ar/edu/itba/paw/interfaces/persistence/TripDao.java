package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TripDao {

    Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final LocalDateTime originDateTime, final double price, final int max_passengers, final User driver);
    boolean addPassenger(final Trip trip, final User passenger);
    List<User> getPassengers(final long tripId);
    Optional<Trip> findById(long id);
    List<Trip> getTripsByDateTimeAndOriginAndDestination(long origin_city_id, long destination_city_id, LocalDateTime dateTime);
    public List<Trip> getFirstNTrips(long n);

}
