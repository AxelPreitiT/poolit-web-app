package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.Locale;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<Locale,String> {

    private static final String PATTERN_STRING = "^(\\+\\d{1,3}\\s?)?\\d{2,4}\\s?\\d{4}\\s?\\d{4}$";

    private static final Pattern PATTERN = Pattern.compile(PATTERN_STRING);
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return PATTERN.matcher(value).matches();
    }
}
