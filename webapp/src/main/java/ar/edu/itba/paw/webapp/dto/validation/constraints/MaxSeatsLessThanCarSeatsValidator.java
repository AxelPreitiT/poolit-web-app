package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.webapp.dto.input.CreateTripDto;
import ar.edu.itba.paw.webapp.dto.validation.annotations.MaxSeatsLessThanCarSeats;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class MaxSeatsLessThanCarSeatsValidator implements ConstraintValidator<MaxSeatsLessThanCarSeats, CreateTripDto> {

    private final CarService carService;

    @Autowired
    public MaxSeatsLessThanCarSeatsValidator( final CarService carService){
        this.carService = carService;
    }
    @Override
    public boolean isValid(CreateTripDto dto, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Car> car = carService.findById(dto.getCarId());
        if(!car.isPresent()){
            return false;
        }
        return car.get().getSeats() >= dto.getMaxSeats();
    }
}
