package ar.edu.itba.paw.interfaces.exceptions;

public class PassengerNotFoundException extends CustomException {

    private static final long serialVersionUID = 3631763748631763748L;

    private static final String MESSAGE_CODE = "exceptions.passenger_not_found";

    public PassengerNotFoundException(){
        super(MESSAGE_CODE);
    }

}
