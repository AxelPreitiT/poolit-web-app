package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.config.converters.Converter;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//https://blog.sebastian-daschner.com/entries/jaxrs-convert-params
//https://eclipse-ee4j.github.io/jersey.github.io/apidocs/2.22/jersey/javax/ws/rs/ext/ParamConverter.html
@Provider
public class ConverterProvider implements ParamConverterProvider {
    @Override
    public <T> ParamConverter<T> getConverter(Class<T> aClass, Type type, Annotation[] annotations) {
        if(type.equals(LocalDateTime.class)){
            return (ParamConverter<T>) new Converter<LocalDateTime>(v -> LocalDateTime.parse(v, DateTimeFormatter.ISO_LOCAL_DATE_TIME),v->v.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),"conversions.dateTimeFormat");
        }
        if(type.equals(FeatureCar.class)){
            return (ParamConverter<T>) new Converter<FeatureCar>(FeatureCar::valueOf,FeatureCar::toString,"conversions.featureCar");
        }
        if(type.equals(Passenger.PassengerState.class)){
            return (ParamConverter<T>) new Converter<Passenger.PassengerState>(Passenger.PassengerState::valueOf,Passenger.PassengerState::toString,"conversions.passengerState");
        }
        if(type.equals(Trip.SortType.class)){
            return (ParamConverter<T>) new Converter<Trip.SortType>(Trip.SortType::valueOf,Trip.SortType::toString,"conversions.tripSortType");
        }
        return null;
    }
}
