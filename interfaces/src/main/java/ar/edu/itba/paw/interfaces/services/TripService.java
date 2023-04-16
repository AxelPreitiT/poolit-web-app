package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

import java.util.List;
import java.util.Optional;

public interface TripService {

    Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress, final Car car, final String date, final String time,final double price, final int maxSeats, User driver);

    Optional<Trip> findById(long id);
    boolean addPassenger(Trip trip, User passenger);
    public boolean addPassenger(long tripId, User passenger);
    List<User> getPassengers(final long tripId);
    List<Trip> getFirstNTrips(long n);
    List<Trip> getTripsByDateTimeAndOriginAndDestination(long origin_city_id, long destination_city_id,final String date, final String time);
}
