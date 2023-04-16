package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
public interface EmailService {

    void sendMailNewPassenger(Trip trip,  User passenger) throws Exception;

    void sendMailNewTrip(Trip trip) throws Exception;

    void sendMailTripConfirmation(Trip trip,  User passenger) throws Exception;
}
