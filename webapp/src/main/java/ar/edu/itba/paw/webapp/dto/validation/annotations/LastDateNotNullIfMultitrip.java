package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.dto.validation.constraints.LastDateNotNullIfMultitripValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LastDateNotNullIfMultitripValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LastDateNotNullIfMultitrip {
    String message() default "{dto.validation.lastDateNotNullIfMultitrip}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}