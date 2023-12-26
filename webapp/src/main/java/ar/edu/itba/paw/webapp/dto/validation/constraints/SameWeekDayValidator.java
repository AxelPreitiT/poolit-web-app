package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.SameWeekDay;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SameWeekDayValidator implements ConstraintValidator<SameWeekDay, CreateTripDto> {
    @Override
    public boolean isValid(CreateTripDto dto, ConstraintValidatorContext constraintValidatorContext) {
        return !dto.isMultitrip() || dto.getDate() == null || dto.getLastDate() == null || dto.getDate().getDayOfWeek().equals(dto.getLastDate().getDayOfWeek());
    }
}
