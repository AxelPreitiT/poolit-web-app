package ar.edu.itba.paw.webapp.dto.validation.annotations;

import ar.edu.itba.paw.webapp.dto.validation.constraints.ExclusiveValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


//Checks that listed fields are the only that may be present in an object (in other words, other fields must be null)
//Validator uses only fields in the annotated class, not in superclass
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ExclusiveValidator.class)
@Documented
@Repeatable(value = Exclusive.List.class)
public @interface Exclusive {
    String message() default "{dto.validation.exclusive}";

    String[] group1() default {};
    String[] group2() default {};

    Class<?> [] groups() default {};

    Class<? extends Payload> [] payload() default {};

    @Target({TYPE, FIELD, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List{
        Exclusive[] value();
    }
}