package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.webapp.dto.output.ExceptionDto;
import ar.edu.itba.paw.webapp.exceptions.ResourceNotFoundException;
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
public class NotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotFoundExceptionMapper.class);

    private final MessageSource messageSource;

    @Autowired
    public NotFoundExceptionMapper(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Response toResponse(ResourceNotFoundException exception) {
        LOGGER.error("{}: Exception raised with message {}",exception.getClass().getName(),messageSource.getMessage(exception.getMessageCode(),null, Locale.getDefault()));
        return Response.status(ResourceNotFoundException.getHttpStatusCode())
                .type(MediaType.APPLICATION_JSON)
                .entity(ExceptionDto.fromMessage(messageSource.getMessage(exception.getMessageCode(),null, LocaleContextHolder.getLocale())))
                .build();
    }
}
