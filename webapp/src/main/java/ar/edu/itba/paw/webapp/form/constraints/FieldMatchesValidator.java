package ar.edu.itba.paw.webapp.form.constraints;

import ar.edu.itba.paw.webapp.form.annotations.FieldMatches;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchesValidator implements ConstraintValidator<FieldMatches, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldMatchesValidator.class);

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatches fieldMatch) {
        firstFieldName = fieldMatch.first();
        secondFieldName = fieldMatch.second();
    }

    @Override
    public boolean isValid(Object form, ConstraintValidatorContext context) {
        try{
            Object firstValue = FieldUtils.getFieldValue(form, firstFieldName);
            Object secondValue = FieldUtils.getFieldValue(form, secondFieldName);
            return (firstValue == null && secondValue == null) || (firstValue != null && firstValue.equals(secondValue));
        } catch (Exception e){
            LOGGER.error("Error while getting field values for FieldMatchesValidator", e);
            return false;
        }
    }
}

