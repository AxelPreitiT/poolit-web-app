package ar.edu.itba.paw.webapp.form.annotations;

import ar.edu.itba.paw.webapp.form.CreateTripForm;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Constraint(validatedBy = DateAndTimeCreateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAndTimeCreate {
    String message() default "{DateAndTimeTrip.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

class DateAndTimeCreateValidator implements ConstraintValidator<DateAndTimeCreate, CreateTripForm>{
    @Override
    public boolean isValid(CreateTripForm form, ConstraintValidatorContext constraintValidatorContext) {
        if((form.getDate()==null || form.getDate().length()==0) && (form.getTime()==null || form.getTime().length()==0)){
            return false;
        }
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate today = LocalDate.now();
        try {
            LocalTime time = LocalTime.parse(form.getTime(), timeFormatter);
            LocalDate firstDate = LocalDate.parse(form.getDate(), dateFormatter);
            return !firstDate.isBefore(today);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}