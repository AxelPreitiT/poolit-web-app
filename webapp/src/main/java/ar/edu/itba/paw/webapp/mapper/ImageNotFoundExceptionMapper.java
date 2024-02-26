package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.interfaces.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.mapper.utils.AbstractExceptionMapper;
import org.springframework.http.HttpStatus;

import javax.ws.rs.ext.Provider;

@Provider
public class ImageNotFoundExceptionMapper extends AbstractExceptionMapper<ImageNotFoundException> {
    private static final String MESSAGE_CODE = "exceptions.image_not_found";
    private static final int HTTP_STATUS_CODE = HttpStatus.BAD_REQUEST.value();

    public ImageNotFoundExceptionMapper() {
        super(MESSAGE_CODE,HTTP_STATUS_CODE);
    }
}
