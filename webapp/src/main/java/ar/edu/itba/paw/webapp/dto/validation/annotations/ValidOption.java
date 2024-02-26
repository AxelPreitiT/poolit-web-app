package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.dto.validation.constraints.ValidOptionValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidOptionValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOption {
    String message() default "{dto.validation.validReviewOption}";
    String listMethod() default "getRatings";
    String listableField();
    String chosenValue() default "rating";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

