package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;
import ar.edu.itba.paw.webapp.form.annotations.LastDateIsAfterDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastDateIsAfterDateValidator implements ConstraintValidator<LastDateIsAfterDate, CreateTripDto> {
    @Override
    public boolean isValid(CreateTripDto dto, ConstraintValidatorContext constraintValidatorContext) {
        return !dto.isMultitrip() || dto.getDate() == null || dto.getLastDate() == null || dto.getLastDate().isAfter(dto.getDate());
    }
}
