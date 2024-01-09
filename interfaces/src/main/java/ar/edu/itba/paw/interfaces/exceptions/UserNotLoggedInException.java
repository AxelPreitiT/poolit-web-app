package ar.edu.itba.paw.interfaces.exceptions;

public class UserNotLoggedInException extends CustomException {
    private static final long serialVersionUID = 121312390745766939L;

    private static final String MESSAGE_CODE = "exceptions.user_not_logged_in";

    public UserNotLoggedInException(){
        super(MESSAGE_CODE);
    }

    public UserNotLoggedInException(final int httpStatusCode){
        super(MESSAGE_CODE,httpStatusCode);
    }

}
