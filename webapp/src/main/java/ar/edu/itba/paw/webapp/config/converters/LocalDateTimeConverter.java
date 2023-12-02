package ar.edu.itba.paw.webapp.config.converters;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements ParamConverter<LocalDateTime> {
    @Override
    public LocalDateTime fromString(String s) {
        if(s == null){
            return null;
        }
        try {
            return LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }catch (Exception e){
            throw new ConversionException("conversions.dateTimeFormat");
        }

    }

    @Override
    public String toString(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
