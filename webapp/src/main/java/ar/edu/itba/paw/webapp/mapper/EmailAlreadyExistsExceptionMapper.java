package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;
import ar.edu.itba.paw.webapp.mapper.utils.AbstractExceptionMapper;
import org.springframework.http.HttpStatus;

import javax.ws.rs.ext.Provider;

@Provider
public class EmailAlreadyExistsExceptionMapper extends AbstractExceptionMapper<EmailAlreadyExistsException> {
    private static final String MESSAGE_CODE = "exceptions.email_already_exists";
    private static final int HTTP_STATUS_CODE = HttpStatus.CONFLICT.value();

    public EmailAlreadyExistsExceptionMapper() {
        super(MESSAGE_CODE,HTTP_STATUS_CODE);
    }
}
