package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.Trip;

public interface TripDao {

    Trip create(String originCity, String originAddress, String destinationCity, String destinationAddress, String date, String time, int seats, String email, String phone);
}
