package ar.edu.itba.paw.webapp.form.annotations;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Constraint(validatedBy = TodayOrLaterValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TodayOrLater {
    String message() default "{TodayOrLater.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

class TodayOrLaterValidator implements ConstraintValidator<TodayOrLater, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate == null || !localDate.isBefore(LocalDate.now());
    }
}
