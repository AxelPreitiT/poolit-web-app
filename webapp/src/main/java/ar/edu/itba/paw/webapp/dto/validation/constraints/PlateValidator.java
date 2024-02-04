package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.Plate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PlateValidator implements ConstraintValidator<Plate,String>{
    //TODO: check if there are plates with spaces in database, because i removed the optional spaces from the pattern

    private static final String PATTERN_STRING = "([a-zA-Z]{2}\\d{3}[a-zA-Z]{2})|([a-zA-Z]{3}\\d{3})";

    private static final Pattern PATTERN = Pattern.compile(PATTERN_STRING);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return PATTERN.matcher(value).matches();
    }
}
