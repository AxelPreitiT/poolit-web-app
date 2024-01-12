package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.controller.utils.ControllerUtils;
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
public @interface PageSize {
    String message() default "{dto.validation.min}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    long value() default 1;

    String fieldName() default ControllerUtils.PAGE_SIZE_QUERY_PARAM;
}
