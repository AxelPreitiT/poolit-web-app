package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.config.converters.LocalDateTimeConverter;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

//https://blog.sebastian-daschner.com/entries/jaxrs-convert-params
//https://eclipse-ee4j.github.io/jersey.github.io/apidocs/2.22/jersey/javax/ws/rs/ext/ParamConverter.html
@Provider
public class ConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
        if(type.equals(LocalDateTime.class)){
            return (ParamConverter<T>) new LocalDateTimeConverter();
        }
        return null;
    }
}
