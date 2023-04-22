package ar.edu.itba.paw.webapp.form.annotations;

import ar.edu.itba.paw.webapp.form.SearchForm;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateAndTimeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAndTime{
    String message() default "{DateAndTime.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

class DateAndTimeValidator implements ConstraintValidator<DateAndTime, SearchForm>{
    @Override
    public boolean isValid(SearchForm form, ConstraintValidatorContext constraintValidatorContext) {
//        return !((form.getDate().length()==0 && form.getTime().length()!=0) || (form.getDate().length()!=0 && form.getTime().length()==0));
        return false;
    }
}