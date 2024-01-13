package ar.edu.itba.paw.interfaces.exceptions;

public class EmailAlreadyExistsException extends CustomException{
    static final long serialVersionUID = 6211553535658649789L;

    private static final String MESSAGE_CODE = "exceptions.email_already_exists";

//    https://www.rfc-editor.org/rfc/rfc7231#page-60
    private static final int ERROR_CODE = 409;

    public EmailAlreadyExistsException(){
        super(MESSAGE_CODE,ERROR_CODE);
    }
}
