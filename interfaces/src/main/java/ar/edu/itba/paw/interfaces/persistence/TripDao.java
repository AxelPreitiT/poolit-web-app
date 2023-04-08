package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface TripDao {

    Trip create(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress,final String carInfo, final String date, final String time, final int seats, User driver);

    Trip findById(long id);
    public boolean addPassenger(final Trip trip, final User passenger);
}
