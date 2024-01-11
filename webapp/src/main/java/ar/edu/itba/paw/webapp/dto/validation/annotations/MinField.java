package ar.edu.itba.paw.webapp.dto.validation.annotations;


import ar.edu.itba.paw.webapp.dto.validation.constraints.MinFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = MinFieldValidator.class)
//Just to add a fieldName property to use in error message
public @interface MinField {
    String message() default "{dto.validation.min}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    long value();

    String fieldName() default "";
}
