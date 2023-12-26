package ar.edu.itba.paw.interfaces.exceptions;

public class CustomException extends Exception{

    private static final int DEFAULT_STATUS_CODE = 400; //BAD REQUEST
    private final String messageCode;

    private final int httpStatusCode;

    public CustomException(final String messageCode){
        this.messageCode = messageCode;
        this.httpStatusCode = DEFAULT_STATUS_CODE;
    }

    public CustomException(final String messageCode, final int httpStatusCode){
        this.messageCode = messageCode;
        this.httpStatusCode = httpStatusCode;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }
}
