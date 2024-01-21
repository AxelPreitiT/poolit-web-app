package ar.edu.itba.paw.webapp.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

//Se usa cuando se pide que el usuario esté autenticado en las reglas de WebAuthConfig
//https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html#servlet-authentication-authenticationentrypoint
@Component
public class UnauthorizedRequestHandler implements AuthenticationEntryPoint {

    private static final String MESSAGE_CODE = "handlers.unauthorized_request";

    private final MessageSource messageSource;

    @Autowired
    public UnauthorizedRequestHandler(final MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON);
        response.getWriter().write(String.format("{\n \"message:\" : \"%s\"\n}",messageSource.getMessage(MESSAGE_CODE,null, request.getLocale())));
        //https://datatracker.ietf.org/doc/html/rfc9110#name-401-unauthorized
        //https://datatracker.ietf.org/doc/html/rfc9110#field.www-authenticate
        //https://www.iana.org/assignments/http-authschemes/http-authschemes.xhtml
        response.setHeader("WWW-Authenticate","Basic, Bearer");
    }
}
