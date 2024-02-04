package ar.edu.itba.paw.webapp.config.converters;

import javax.ws.rs.ext.ParamConverter;
import java.util.function.Function;

public class Converter<T> implements ParamConverter<T> {

    private final String messageCode;
    private final Function<String,T> converterFromString;

    private final Function<T,String> converterToString;

    public Converter(final Function<String,T> converterFromString, final Function<T,String> converterToString, final String messageCode){
        this.converterFromString = converterFromString;
        this.converterToString = converterToString;
        this.messageCode = messageCode;
    }
    @Override
    public T fromString(String value) {
        if(value==null){
            return null;
        }
        try {
            return converterFromString.apply(value);
        }catch (Exception e){
            throw new ConversionException(messageCode);
        }
    }

    @Override
    public String toString(T value) {
        return converterToString.apply(value);
    }
}
