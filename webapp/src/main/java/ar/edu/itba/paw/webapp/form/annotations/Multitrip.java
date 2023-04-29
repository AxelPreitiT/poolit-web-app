package ar.edu.itba.paw.webapp.form.annotations;

import ar.edu.itba.paw.webapp.form.SearchTripForm;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Constraint(validatedBy = MultitripValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Multitrip {
    String message() default "{Multitrip.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

class MultitripValidator implements ConstraintValidator<Multitrip, SearchTripForm> {
    @Override
    public boolean isValid(SearchTripForm form, ConstraintValidatorContext constraintValidatorContext) {
        if(form.isMultitrip()) {
            if (form.getLastDate().length() == 0) {
                return false;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate firstDate = LocalDate.parse(form.getDate(), formatter);
            LocalDate lastDate = LocalDate.parse(form.getLastDate(), formatter);
            return firstDate.isBefore(lastDate) && firstDate.getDayOfWeek().equals(lastDate.getDayOfWeek());
        }
        return true;
    }
}
