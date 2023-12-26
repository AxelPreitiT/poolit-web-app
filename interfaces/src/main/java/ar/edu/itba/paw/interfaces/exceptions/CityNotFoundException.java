package ar.edu.itba.paw.interfaces.exceptions;

public class CityNotFoundException extends CustomException{
    private static final long serialVersionUID = 221315739072439789L;

    private static final String MESSAGE_CODE = "exceptions.city_not_found";
    public CityNotFoundException(){
        super(MESSAGE_CODE);
    }

    public CityNotFoundException(final int httpStatusCode){
        super(MESSAGE_CODE,httpStatusCode);
    }
}
