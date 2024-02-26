package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.User;

import java.time.LocalDateTime;

public interface EmailService {

    void sendMailNewPassenger(Trip trip, Passenger passenger);

    void sendMailNewTrip(Trip trip);

    void sendMailTripCancelledToDriver(Trip trip,Passenger passenger);

    void sendMailTripConfirmation(Trip trip, Passenger passenger);

    void sendMailTripDeletedToPassenger(Trip trip, Passenger passenger);

    void sendMailTripDeletedToDriver(Trip trip);

    void sendMailTripTruncatedToPassenger(Trip trip, Passenger passenger, LocalDateTime nextOccurrence)  ;

    void sendVerificationEmail(User user, String token);

    //Se manda al driver indicando que alguien quiere ser pasajero
    void sendMailNewPassengerRequest(Trip trip, Passenger passenger);
    //Se manda al pasajero indicando que se recibio su solicitud
    void sendMailTripRequest(Trip trip, Passenger passenger);
    //Se manda al pasajero indicando que fue aceptado en el viaje
    void sendMailTripConfirmed(Trip trip, Passenger passenger);
    //Se manda al pasajero indicando que fue rechazado en el viaje
    void sendMailTripRejected(Trip trip, Passenger passenger);

    void sendMailRejectReport(Report report);
    void sendMailAcceptReport(Report report);
    void sendMailBanReport(Report report);
    void sendMailNewReport(Report report, User admin);

}
