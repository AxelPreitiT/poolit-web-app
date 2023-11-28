package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.webapp.dto.validation.annotations.CarFromUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CarFromUserValidator implements ConstraintValidator<CarFromUser, Long> {

    private final CarService carService;

    @Autowired
    public CarFromUserValidator( final CarService carService){
        this.carService = carService;
    }
    @Override
    public boolean isValid( Long carId, ConstraintValidatorContext constraintValidatorContext) {
        try{
            List<Car> cars = carService.findCurrentUserCars();
            return cars.stream().anyMatch(c -> c.getCarId()==carId);
        }catch (Exception e){
            return false;
        }
    }
}

