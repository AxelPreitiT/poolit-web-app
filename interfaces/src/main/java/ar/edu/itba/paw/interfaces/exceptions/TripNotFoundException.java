package ar.edu.itba.paw.interfaces.exceptions;



public class TripNotFoundException extends CustomException{
    private static final long serialVersionUID = 121312390745766939L;

    private static final String MESSAGE_CODE = "exceptions.trip_not_found";
    public TripNotFoundException(){
        super(MESSAGE_CODE);
    }

    public TripNotFoundException(final int httpStatusCode){
        super(MESSAGE_CODE,httpStatusCode);
    }
}
