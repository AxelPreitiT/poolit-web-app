package ar.edu.itba.paw.models.converters;

import javax.persistence.AttributeConverter;
import java.time.DayOfWeek;

public class DayOfWeekConverter implements AttributeConverter<DayOfWeek,Integer> {

    @Override
    public Integer convertToDatabaseColumn(DayOfWeek attribute) {
        return attribute.getValue();
    }

    @Override
    public DayOfWeek convertToEntityAttribute(Integer dbData) {
        return DayOfWeek.of(dbData);
    }
}
