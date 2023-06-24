package ar.edu.itba.paw.webapp.form.constraints;

import ar.edu.itba.paw.webapp.form.annotations.FieldMatches;
import org.springframework.security.util.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchesValidator implements ConstraintValidator<FieldMatches, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatches fieldMatch) {
        firstFieldName = fieldMatch.first();
        secondFieldName = fieldMatch.second();
    }

    @Override
    public boolean isValid(Object form, ConstraintValidatorContext context) {
        Object firstValue;
        Object secondValue;
        try{
            firstValue = FieldUtils.getFieldValue(form, firstFieldName);
            secondValue = FieldUtils.getFieldValue(form, secondFieldName);
        } catch (Exception e){
            throw new RuntimeException();
        }

        if (firstValue == null && secondValue == null) {
            return true;
        }

        if (firstValue != null) {
            return firstValue.equals(secondValue);
        }

        return false;
    }
}

