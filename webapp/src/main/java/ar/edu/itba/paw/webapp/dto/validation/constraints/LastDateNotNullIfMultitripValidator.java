package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.LastDateNotNullIfMultitrip;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class LastDateNotNullIfMultitripValidator implements ConstraintValidator<LastDateNotNullIfMultitrip, CreateTripDto> {

    @Override
    public boolean isValid(CreateTripDto dto, ConstraintValidatorContext constraintValidatorContext) {
        return !dto.isMultitrip() || dto.getLastDate() != null;
    }
}