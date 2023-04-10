package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface TripService {

    Trip createTrip(final City originCity, final String originAddress, final City destinationCity, final String destinationAddress,final String infoCar, final String plate, final String date, final String time, final int seats, User driver);

    Trip findById(long id);
    public boolean addPassenger(Trip trip, User passenger);
    public boolean addPassenger(long tripId, User passenger);
}
