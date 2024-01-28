package ar.edu.itba.paw.webapp.form.annotations;

//import ar.edu.itba.paw.webapp.form.RecurrentTripForm;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDateTime;

/*
@Constraint(validatedBy = NowOrLaterValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NowOrLater {
    String message() default "{NowOrLater.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

class NowOrLaterValidator implements ConstraintValidator<NowOrLater, RecurrentTripForm> {
    @Override
    public boolean isValid(RecurrentTripForm form, ConstraintValidatorContext constraintValidatorContext) {
        return form.getDate() == null || form.getTime() == null || !form.getDate().atTime(form.getTime()).isBefore(LocalDateTime.now());
    }
}

 */
