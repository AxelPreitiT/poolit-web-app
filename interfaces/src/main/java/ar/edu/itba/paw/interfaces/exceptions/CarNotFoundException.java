package ar.edu.itba.paw.interfaces.exceptions;



public class CarNotFoundException extends CustomException{
    private static final long serialVersionUID = 521311231237345289L;

    private static final String MESSAGE_CODE = "exceptions.car_not_found";
    public CarNotFoundException(){
        super(MESSAGE_CODE);
    }

    public CarNotFoundException(final int httpStatusCode){
        super(MESSAGE_CODE,httpStatusCode);
    }
}
