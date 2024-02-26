package ar.edu.itba.paw.webapp.mapper.utils;

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
import java.util.Locale;

@Component
public class AbstractExceptionMapper<T extends Throwable> implements ExceptionMapper<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractExceptionMapper.class);

    @Autowired
    private MessageSource messageSource;

    private final String messageCode;

    private final int httpStatusCode;

    public AbstractExceptionMapper(String messageCode, int httpStatusCode) {
        this.messageCode = messageCode;
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public Response toResponse(T exception) {
        LOGGER.error("{}: Exception raised with message {}",exception.getClass().getName(),messageSource.getMessage(messageCode,null, Locale.getDefault()));
        return Response.status(httpStatusCode)
                .type(MediaType.APPLICATION_JSON)
                .entity(ExceptionDto.fromMessage(messageSource.getMessage(messageCode,null, LocaleContextHolder.getLocale())))
                .build();
    }
}
