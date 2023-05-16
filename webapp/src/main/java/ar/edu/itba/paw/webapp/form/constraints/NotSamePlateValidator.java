package ar.edu.itba.paw.webapp.form.constraints;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.annotations.NotSamePlate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class NotSamePlateValidator implements ConstraintValidator<NotSamePlate,String> {
    private final CarService carService;
    private final UserService userService;

    @Autowired
    public NotSamePlateValidator(final CarService carService, final UserService userService){
        super();
        this.carService = carService;
        this.userService = userService;
    }
    @Override
    public boolean isValid(String plate, ConstraintValidatorContext constraintValidatorContext){
        final User user = userService.getCurrentUser().orElseThrow(IllegalStateException::new);
        final Optional<Car> car = carService.findByUserAndPlate(user,plate);
        return !car.isPresent();
    }
}
