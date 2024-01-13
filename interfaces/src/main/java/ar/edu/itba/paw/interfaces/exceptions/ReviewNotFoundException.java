package ar.edu.itba.paw.interfaces.exceptions;

public class ReviewNotFoundException extends CustomException{
    private static final long serialVersionUID = 363174537324524528L;

    private static final String MESSAGE_CODE = "exceptions.review_not_found";

    public ReviewNotFoundException(){
        super(MESSAGE_CODE);
    }

    public ReviewNotFoundException(final int httpStatusCode){
        super(MESSAGE_CODE,httpStatusCode);
    }
}
