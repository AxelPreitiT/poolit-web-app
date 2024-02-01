package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.dto.validation.constraints.LessOrEqualThanValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

//If any value is null, it is considered valid
@Constraint(validatedBy = LessOrEqualThanValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(value = LessOrEqualThan.List.class)
public @interface LessOrEqualThan {
    String message() default "{dto.validation.lessOrEqualThan}";

    String value1();
    String value2();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    @Target(TYPE)
    @Retention(RUNTIME)
    @Documented
    @interface List{
        LessOrEqualThan[] value();
    }
}
