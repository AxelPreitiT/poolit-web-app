package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.User;

import java.io.IOException;

public interface EmailService {

    void sendMailNewPassenger(Trip trip, Passenger passenger) throws Exception;

    void sendMailNewTrip(Trip trip) throws Exception;

    void sendMailTripConfirmation(Trip trip, Passenger passenger) throws Exception;

    void sendMailTripDeletedToPassenger(Trip trip, Passenger passenger) throws Exception;

    void sendMailTripDeletedToDriver(Trip trip) throws Exception;

}
