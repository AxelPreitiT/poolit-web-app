package ar.edu.itba.paw.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

@Component
public class ForbiddenRequestHandler implements AccessDeniedHandler {

    private static final String MESSAGE_CODE = "exceptions.accessDenied";
    private final MessageSource messageSource;

    @Autowired
    public ForbiddenRequestHandler(final MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON);
        response.getWriter().write(String.format("{\n \"message:\" : \"%s\"\n}",messageSource.getMessage(MESSAGE_CODE,null,request.getLocale())));
    }
}
