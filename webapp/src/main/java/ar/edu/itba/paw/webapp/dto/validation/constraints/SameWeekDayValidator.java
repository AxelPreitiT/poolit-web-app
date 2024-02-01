package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.SameWeekDay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAccessor;

public class SameWeekDayValidator implements ConstraintValidator<SameWeekDay, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SameWeekDayValidator.class);
    private String field1;
    private String field2;

    @Override
    public void initialize(SameWeekDay constraintAnnotation) {
        this.field1 = constraintAnnotation.value1();
        this.field2 = constraintAnnotation.value2();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try {
            TemporalAccessor value1 = (TemporalAccessor) FieldUtils.getFieldValue(object,field1);
            TemporalAccessor value2 = (TemporalAccessor) FieldUtils.getFieldValue(object,field2);
            return value1 == null || value2 == null || DayOfWeek.from(value1).equals(DayOfWeek.from(value2));
        }catch (Exception e){
            LOGGER.error("Error while getting field values in SameWeekDayValidator",e);
        }
        return true;
//        dto.getLastDate().atTime(LocalTime.now()).getDayOfWeek()
//        return !dto.isMultitrip() || dto.getDate() == null || dto.getLastDate() == null || dto.getDate().getDayOfWeek().equals(dto.getLastDate().getDayOfWeek());
    }
}
