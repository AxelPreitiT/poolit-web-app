package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.dto.validation.constraints.PassengerStateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PassengerStateValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface PassengerState {
    String message() default "{dto.validation.passengerState}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

