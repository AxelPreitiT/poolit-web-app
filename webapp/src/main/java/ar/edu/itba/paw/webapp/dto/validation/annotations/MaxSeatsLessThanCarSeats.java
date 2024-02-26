package ar.edu.itba.paw.webapp.dto.validation.annotations;


import ar.edu.itba.paw.webapp.dto.validation.constraints.MaxSeatsLessThanCarSeatsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = MaxSeatsLessThanCarSeatsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxSeatsLessThanCarSeats {
    String message() default "{dto.validation.maxSeatsLessThanCarSeats}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
