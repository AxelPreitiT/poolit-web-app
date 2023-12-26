package ar.edu.itba.paw.interfaces.exceptions;

public class TripAlreadyStartedException extends CustomException{
    static final long serialVersionUID = 285113123155649789L;

    private static final String MESSAGE_CODE = "exceptions.trip_already_started";

    public TripAlreadyStartedException(){
        super(MESSAGE_CODE);
    }
}
