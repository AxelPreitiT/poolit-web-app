package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.interfaces.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.webapp.mapper.utils.AbstractExceptionMapper;
import org.springframework.http.HttpStatus;

import javax.ws.rs.ext.Provider;

@Provider
public class UserNotLoggedInExceptionMapper extends AbstractExceptionMapper<UserNotLoggedInException> {
    private static final String MESSAGE_CODE = "exceptions.user_not_logged_in";
    private static final int HTTP_STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public UserNotLoggedInExceptionMapper() {
        super(MESSAGE_CODE, HTTP_STATUS_CODE);
    }
}
