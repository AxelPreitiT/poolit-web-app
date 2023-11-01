package ar.edu.itba.paw.webapp.config;

import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

//https://docs.oracle.com/javaee/7/api/javax/ws/rs/container/ContainerRequestFilter.html#filter-javax.ws.rs.container.ContainerRequestContext-

@Provider
public class AcceptLanguageFilter implements ContainerRequestFilter {
//    TODO: no se ejecuta, ver por qué
    @Override
    public void filter(ContainerRequestContext requestContext) {
        List<Locale> acceptedLanguages = requestContext.getAcceptableLanguages();
        if(!acceptedLanguages.isEmpty()){
            LocaleContextHolder.setLocale(acceptedLanguages.get(0));
        }
    }
}
