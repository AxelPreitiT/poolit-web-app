package ar.edu.itba.paw.webapp.mapper;

import ar.edu.itba.paw.webapp.config.converters.ConversionException;
import ar.edu.itba.paw.webapp.dto.output.ExceptionDto;
import org.glassfish.jersey.server.ParamException;
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
public class ParamExceptionMapper implements ExceptionMapper<ParamException> {

    private final MessageSource messageSource;

    @Autowired
    public ParamExceptionMapper(final MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @Override
    public Response toResponse(ParamException exception) {
        Response.ResponseBuilder ans = Response.status(Response.Status.BAD_REQUEST);
        if(exception.getCause() instanceof ConversionException){
            ans.type(MediaType.APPLICATION_JSON)
                    .entity(ExceptionDto.fromMessage(messageSource.getMessage(((ConversionException)exception.getCause()).getMessageCode(),null, LocaleContextHolder.getLocale())));
        }
        return ans.build();
    }
}
