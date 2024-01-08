package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.interfaces.exceptions.CustomException;
import ar.edu.itba.paw.webapp.dto.output.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Locale;

@Provider
@Component
public class CustomExceptionMapper implements ExceptionMapper<CustomException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionMapper.class);

    private final MessageSource messageSource;

    @Autowired
    public CustomExceptionMapper(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Response toResponse(CustomException exception){
        LOGGER.error("{}: Exception raised with message {}",exception.getClass().getName(),messageSource.getMessage(exception.getMessageCode(),null, Locale.getDefault()));
        return Response.status(exception.getHttpStatusCode())
                .type(MediaType.APPLICATION_JSON)
                .entity(ExceptionDto.fromMessage(messageSource.getMessage(exception.getMessageCode(),null,LocaleContextHolder.getLocale())))
                .build();
    }
}
