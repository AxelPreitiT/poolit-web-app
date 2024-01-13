package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.dto.validation.constraints.ImageSizeValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = ImageSizeValidator.class)
@Documented
public @interface ImageSize {

    String message() default "{dto.validation.imageSize}";

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};
}