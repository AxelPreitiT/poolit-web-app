package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.interfaces.exceptions.RoleAlreadyChangedException;
import ar.edu.itba.paw.webapp.mapper.utils.AbstractExceptionMapper;
import org.springframework.http.HttpStatus;

import javax.ws.rs.ext.Provider;

@Provider
public class RoleAlreadyChangedExceptionMapper extends AbstractExceptionMapper<RoleAlreadyChangedException> {
    private static final String MESSAGE_CODE = "exceptions.role_already_changed";
    private static final int HTTP_STATUS_CODE = HttpStatus.CONFLICT.value();

    public RoleAlreadyChangedExceptionMapper() {
        super(MESSAGE_CODE, HTTP_STATUS_CODE);
    }
}
