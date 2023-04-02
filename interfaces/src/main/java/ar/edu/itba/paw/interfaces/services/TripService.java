package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Trip;

public interface TripService {

    Trip createTrip(String originCity, String originAddress, String destinationCity, String destinationAddress, String date, String time, int seats, String email, String phone);
}
