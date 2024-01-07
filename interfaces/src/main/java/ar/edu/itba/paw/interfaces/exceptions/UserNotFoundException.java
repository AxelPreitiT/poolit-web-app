package ar.edu.itba.paw.interfaces.exceptions;


public class UserNotFoundException extends CustomException{
    private static final long serialVersionUID = 3213155749078647789L;

    private static final String MESSAGE_CODE = "exceptions.user_not_found";
    public UserNotFoundException(){
        super(MESSAGE_CODE);
    }

    public UserNotFoundException(final int httpStatusCode){
        super(MESSAGE_CODE,httpStatusCode);
    }


}
