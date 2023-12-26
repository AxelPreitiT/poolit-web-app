package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.EndDateIsAfterStartDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class EndDateIsAfterStartDateValidator implements ConstraintValidator<EndDateIsAfterStartDate, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EndDateIsAfterStartDateValidator.class);

    private String startDate;

    private String endDate;

    @Override
    public void initialize(EndDateIsAfterStartDate endDateIsAfterStartDate){
        startDate = endDateIsAfterStartDate.start();
        endDate = endDateIsAfterStartDate.end();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        try {
            LocalDate start = (LocalDate) FieldUtils.getFieldValue(object,startDate);
            LocalDate end = (LocalDate) FieldUtils.getFieldValue(object,endDate);
            return start == null || end == null || end.isAfter(start);
        }catch (Exception e){
            LOGGER.error("Error while getting field values in EndDateIsAfterStartDateValidator",e);
        }
        return false;
    }
}
