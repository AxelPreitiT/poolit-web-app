package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

public interface TripDao {

    Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final String plate, final Date date, final Time time, final double price, final int max_passengers, User driver);

    Optional<Trip> findById(long id);

    boolean addPassenger(final Trip trip, final User passenger);


}
