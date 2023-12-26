package ar.edu.itba.paw.webapp.dto.validation.annotations;


import ar.edu.itba.paw.webapp.dto.validation.constraints.EndDateIsAfterStartDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Constraint(validatedBy = EndDateIsAfterStartDateValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EndDateIsAfterStartDate {
    String message() default "{dto.validation.lastDateIsAfterDate}";

    String start();

    String end();

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
