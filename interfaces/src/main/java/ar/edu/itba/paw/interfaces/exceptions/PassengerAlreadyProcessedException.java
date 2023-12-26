package ar.edu.itba.paw.interfaces.exceptions;

public class PassengerAlreadyProcessedException extends CustomException{
    private static final long serialVersionUID = 3631123732231763748L;

    private static final String MESSAGE_CODE = "exceptions.passenger_already_processed";

    public PassengerAlreadyProcessedException(){
        super(MESSAGE_CODE);
    }
}
