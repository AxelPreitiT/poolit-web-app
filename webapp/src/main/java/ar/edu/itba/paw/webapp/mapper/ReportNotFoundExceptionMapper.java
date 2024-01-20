package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.interfaces.exceptions.ReportNotFoundException;
import ar.edu.itba.paw.webapp.mapper.utils.AbstractExceptionMapper;
import org.springframework.http.HttpStatus;

import javax.ws.rs.ext.Provider;

@Provider
public class ReportNotFoundExceptionMapper extends AbstractExceptionMapper<ReportNotFoundException> {
    private static final String MESSAGE_CODE = "exceptions.report_not_found";
    private static final int HTTP_STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public ReportNotFoundExceptionMapper() {
        super(MESSAGE_CODE, HTTP_STATUS_CODE);
    }
}
