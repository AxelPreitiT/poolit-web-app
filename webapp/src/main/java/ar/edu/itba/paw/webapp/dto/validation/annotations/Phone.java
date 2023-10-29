package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.dto.validation.constraints.EmailValidator;
import ar.edu.itba.paw.webapp.dto.validation.constraints.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
@Documented
public @interface Phone {
    String message() default "{dto.validation.phone}";

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
