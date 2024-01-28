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

/*
@Constraint(validatedBy = LastDateNotNullIfMultitripValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LastDateNotNullIfMultitrip {
    String message() default "{LastDateNotNullIfMultitrip.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

class LastDateNotNullIfMultitripValidator implements ConstraintValidator<LastDateNotNullIfMultitrip, RecurrentTripForm> {

    @Override
    public boolean isValid(RecurrentTripForm form, ConstraintValidatorContext constraintValidatorContext) {
        return !form.isMultitrip() || form.getLastDate() != null;
    }
}

 */