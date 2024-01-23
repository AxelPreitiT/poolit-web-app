package ar.edu.itba.paw.webapp.form.annotations;

import ar.edu.itba.paw.interfaces.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
@Constraint(validatedBy = CityIdValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CityId{
    String message() default "{CityId.error}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
class CityIdValidator implements ConstraintValidator<CityId, Integer> {

    private final CityService cityService;
    @Autowired
    public CityIdValidator(CityService cityService){
        this.cityService = cityService;
    }
    @Override
    public boolean isValid( Integer cityId, ConstraintValidatorContext constraintValidatorContext) {
        return cityId==-1 ||  cityService.findCityById(cityId).isPresent();
    }
}

 */