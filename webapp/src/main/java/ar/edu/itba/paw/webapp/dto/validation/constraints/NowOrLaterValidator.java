package ar.edu.itba.paw.webapp.dto.validation.constraints;


import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.NowOrLater;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class NowOrLaterValidator implements ConstraintValidator<NowOrLater, CreateTripDto> {
    @Override
    public boolean isValid(CreateTripDto dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getDate() == null || dto.getTime() == null || !dto.getDate().atTime(dto.getTime()).isBefore(LocalDateTime.now());
    }
}
