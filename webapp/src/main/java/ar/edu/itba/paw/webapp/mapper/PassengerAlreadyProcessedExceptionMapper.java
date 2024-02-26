package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.interfaces.exceptions.PassengerAlreadyProcessedException;
import ar.edu.itba.paw.webapp.mapper.utils.AbstractExceptionMapper;
import org.springframework.http.HttpStatus;

import javax.ws.rs.ext.Provider;

@Provider
public class PassengerAlreadyProcessedExceptionMapper extends AbstractExceptionMapper<PassengerAlreadyProcessedException> {
    private static final String MESSAGE_CODE = "exceptions.passenger_already_processed";
    private static final int HTTP_STATUS_CODE = HttpStatus.CONFLICT.value();

    public PassengerAlreadyProcessedExceptionMapper() {
        super(MESSAGE_CODE, HTTP_STATUS_CODE);
    }
}
