package ar.edu.itba.paw.webapp.mapper;


import ar.edu.itba.paw.webapp.dto.output.ExceptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Component
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    private final MessageSource messageSource;

    private static final String MESSAGE_CODE = "exceptions.illegal_argument";

    @Autowired
    public IllegalArgumentExceptionMapper(final MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @Override
    public Response toResponse(IllegalArgumentException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .entity(ExceptionDto.fromMessage(messageSource.getMessage(MESSAGE_CODE,null, LocaleContextHolder.getLocale())))
                        .build();
    }
}
