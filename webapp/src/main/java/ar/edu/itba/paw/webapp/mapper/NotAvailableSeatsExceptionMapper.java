package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.interfaces.exceptions.NotAvailableSeatsException;
import ar.edu.itba.paw.webapp.mapper.utils.AbstractExceptionMapper;
import org.springframework.http.HttpStatus;

import javax.ws.rs.ext.Provider;

@Provider
public class NotAvailableSeatsExceptionMapper extends AbstractExceptionMapper<NotAvailableSeatsException> {
    private static final String MESSAGE_CODE = "exceptions.not_available_seats";
    private static final int HTTP_STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public NotAvailableSeatsExceptionMapper() {
        super(MESSAGE_CODE, HTTP_STATUS_CODE);
    }
}
