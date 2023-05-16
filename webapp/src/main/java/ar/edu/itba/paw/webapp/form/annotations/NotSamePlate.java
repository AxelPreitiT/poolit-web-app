package ar.edu.itba.paw.webapp.form.annotations;

import ar.edu.itba.paw.webapp.form.constraints.MultiPartFileValidator;
import ar.edu.itba.paw.webapp.form.constraints.NotSamePlateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = NotSamePlateValidator.class)
@Documented
public @interface NotSamePlate {
    String message() default "NotSamePlate.error";

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};
}