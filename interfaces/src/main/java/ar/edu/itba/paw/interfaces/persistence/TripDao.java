package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface TripDao {

    Trip create(City originCity, String originAddress, City destinationCity, String destinationAddress, String date, String time, int seats, User driver);

    Trip findById(long id);
    public boolean addPassenger(final Trip trip, final User passenger);
}
