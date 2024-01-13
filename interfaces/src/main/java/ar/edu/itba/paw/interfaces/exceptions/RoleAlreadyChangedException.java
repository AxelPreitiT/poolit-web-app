package ar.edu.itba.paw.interfaces.exceptions;

public class RoleAlreadyChangedException extends CustomException{
    private static final long serialVersionUID = 345679072439789L;

    private static final String MESSAGE_CODE = "exceptions.role_already_changed";
    private static final int ERROR_CODE = 409;
    public RoleAlreadyChangedException(){
        super(MESSAGE_CODE,ERROR_CODE);
    }

    public RoleAlreadyChangedException(final int httpStatusCode){
        super(MESSAGE_CODE,httpStatusCode);
    }
}
