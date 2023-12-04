package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.webapp.dto.output.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

//Con el Provider no manda esto tampoco!
@Provider
@Component
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessDeniedExceptionMapper.class);

    private static final String MESSAGE_CODE = "exceptions.accessDenied";
    private final MessageSource messageSource;

    @Autowired
    public AccessDeniedExceptionMapper(final MessageSource messageSource){
        this.messageSource = messageSource;
    }

    //Si no ponemos una entity, devuelve 404, entonces devolvemos un mensaje de error
    @Override
    public Response toResponse(AccessDeniedException exception) {
        LOGGER.error("{}: Exception raised",exception.getClass().getName());
        return Response.status(Response.Status.FORBIDDEN)
                .entity(ExceptionDto.fromMessage(messageSource.getMessage(MESSAGE_CODE,null, LocaleContextHolder.getLocale())))
                .build();

    }
}
