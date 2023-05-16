package ar.edu.itba.paw.webapp.form.annotations;

import ar.edu.itba.paw.webapp.form.RecurrentTripForm;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SameWeekDayValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SameWeekDay {
    String message() default "{SameWeekDay.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

class SameWeekDayValidator implements ConstraintValidator<SameWeekDay, RecurrentTripForm> {

    @Override
    public boolean isValid(RecurrentTripForm form, ConstraintValidatorContext constraintValidatorContext) {
        return !form.isMultitrip() || form.getLastDate() == null || form.getDate().getDayOfWeek().equals(form.getLastDate().getDayOfWeek());
    }
}