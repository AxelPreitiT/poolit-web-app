package ar.edu.itba.paw.webapp.form.annotations;

//import ar.edu.itba.paw.webapp.form.SearchTripForm;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
@Constraint(validatedBy = PriceValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Price {
    String message() default "{Price.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

class PriceValidator implements ConstraintValidator<Price, SearchTripForm> {

    @Override
    public boolean isValid(SearchTripForm form, ConstraintValidatorContext constraintValidatorContext) {
        return form.getMinPrice() == null || form.getMaxPrice() == null || form.getMinPrice().compareTo(form.getMaxPrice()) <= 0;
    }
}

 */
