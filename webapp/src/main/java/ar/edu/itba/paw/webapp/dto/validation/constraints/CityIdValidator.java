package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.webapp.dto.validation.annotations.CityId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CityIdValidator implements ConstraintValidator<CityId, Integer> {

    private final CityService cityService;
    @Autowired
    public CityIdValidator(CityService cityService){
        this.cityService = cityService;
    }
    @Override
    public boolean isValid( Integer cityId, ConstraintValidatorContext constraintValidatorContext) {
        return cityId==null ||  cityService.findCityById(cityId).isPresent();
    }
}
