package ar.edu.itba.paw.webapp.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {
    private static final int HTTP_STATUS_CODE = HttpStatus.NOT_FOUND.value();

    private final String entity;

    public ResourceNotFoundException(){
        this.entity = "";
    }

    public ResourceNotFoundException(final String entity){
        this.entity = entity;
    }

    public String getMessageCode(){
//        return "controllerExceptions." + entity +".not_found";
        return "controllerExceptions.not_found";
    }

    public static int getHttpStatusCode(){
        return HTTP_STATUS_CODE;
    }
}
