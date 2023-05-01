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

@Constraint(validatedBy = DateAndTimeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAndTime{
    String message() default "{DateAndTime.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

class DateAndTimeValidator implements ConstraintValidator<DateAndTime, SearchTripForm>{
    @Override
    public boolean isValid(SearchTripForm form, ConstraintValidatorContext constraintValidatorContext) {
        if((form.getDate()==null || form.getDate().length()==0) && (form.getTime()==null || form.getTime().length()==0)){
            return true;
        }
        if (form.getDate().length() == 0 || form.getTime().length() == 0) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        LocalDate firstDate = LocalDate.parse(form.getDate(), formatter);
        return !firstDate.isBefore(today);
    }
}