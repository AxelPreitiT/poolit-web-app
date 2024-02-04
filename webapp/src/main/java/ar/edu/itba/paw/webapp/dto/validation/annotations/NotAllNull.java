package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.dto.validation.constraints.NotAllNullValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotAllNullValidator.class)
@Documented
public @interface NotAllNull {
    String message() default "{dto.validation.notAllNull}";

    String[] fields() default {};

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
