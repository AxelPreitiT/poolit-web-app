package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.interfaces.exceptions.TripAlreadyStartedException;
import ar.edu.itba.paw.webapp.mapper.utils.AbstractExceptionMapper;
import org.springframework.http.HttpStatus;

import javax.ws.rs.ext.Provider;

@Provider
public class TripAlreadyStartedExceptionMapper extends AbstractExceptionMapper<TripAlreadyStartedException> {
    private static final String MESSAGE_CODE = "exceptions.trip_already_started";
    private static final int HTTP_STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public TripAlreadyStartedExceptionMapper() {
        super(MESSAGE_CODE, HTTP_STATUS_CODE);
    }
}
