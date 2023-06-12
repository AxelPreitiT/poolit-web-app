package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.User;

import java.io.IOException;
import java.time.LocalDateTime;

public interface EmailService {

    void sendMailNewPassenger(Trip trip, Passenger passenger) throws Exception;

    void sendMailNewTrip(Trip trip) throws Exception;

    void sendMailTripCancelledToDriver(Trip trip,Passenger passenger) throws Exception;

    void sendMailTripConfirmation(Trip trip, Passenger passenger) throws Exception;

    void sendMailTripDeletedToPassenger(Trip trip, Passenger passenger) throws Exception;

    void sendMailTripDeletedToDriver(Trip trip) throws Exception;

    void sendMailTripTruncatedToPassenger(Trip trip, Passenger passenger, LocalDateTime nextOccurrence)  throws Exception ;

    void sendVerificationEmail(User user, String token) throws Exception;

    //Se manda al driver indicando que alguien quiere ser pasajero
    void sendMailNewPassengerRequest(Trip trip, Passenger passenger) throws Exception;
    //Se manda al pasajero indicando que se recibio su solicitud
    void sendMailTripRequest(Trip trip, Passenger passenger) throws Exception;
    //Se manda al pasajero indicando que fue aceptado en el viaje
    void sendMailTripConfirmed(Trip trip, Passenger passenger) throws  Exception;
    //Se manda al pasajero indicando que fue rechazado en el viaje
    void sendMailTripRejected(Trip trip, Passenger passenger) throws Exception;
}
