package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;

public interface TripService {

    Trip createTrip(City originCity, String originAddress, City destinationCity, String destinationAddress, String date, String time, int seats, User driver);

    Trip findById(long id);
    public boolean addPassenger(Trip trip, User passenger);
    public boolean addPassenger(long tripId, User passenger);
}
