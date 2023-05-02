package ar.edu.itba.paw.webapp.form.annotations;

import ar.edu.itba.paw.webapp.form.SearchTripForm;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Constraint(validatedBy = MultitripSearchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MultitripSearch {
    String message() default "{MultitripSearch.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

class MultitripSearchValidator implements ConstraintValidator<MultitripSearch, SearchTripForm> {
    @Override
    public boolean isValid(SearchTripForm form, ConstraintValidatorContext constraintValidatorContext) {
        if(form.isMultitrip()) {
            if((form.getDate()==null || form.getDate().length()==0) &&
                    (form.getTime()==null || form.getTime().length()==0) &&
                    (form.getLastDate() == null || form.getLastDate().length() == 0)
            ){
                return false;
            }

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDate today = LocalDate.now();
            try {
                LocalTime time = LocalTime.parse(form.getTime(), timeFormatter);
                LocalDate firstDate = LocalDate.parse(form.getDate(), dateFormatter);
                LocalDate lastDate = LocalDate.parse(form.getLastDate(), dateFormatter);
                return firstDate.isBefore(lastDate) && firstDate.getDayOfWeek().equals(lastDate.getDayOfWeek());
            } catch (DateTimeParseException e) {
                return false;
            }
        }
        return true;
    }
}
