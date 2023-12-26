package ar.edu.itba.paw.interfaces.exceptions;

public class NotAvailableSeatsException extends CustomException{
    static final long serialVersionUID = 61232335631235789L;

    private static final String MESSAGE_CODE = "exceptions.not_available_seats";

    public NotAvailableSeatsException(){
        super(MESSAGE_CODE);
    }


}
