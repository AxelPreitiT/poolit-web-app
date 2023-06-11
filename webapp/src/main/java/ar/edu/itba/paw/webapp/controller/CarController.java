package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.exceptions.CarNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.webapp.form.CreateCarForm;
import ar.edu.itba.paw.webapp.form.UpdateCarForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class CarController extends LoggedUserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    private final UserService userService;
    private final ImageService imageService;
    private final CarService carService;

    @Autowired
    public CarController(UserService userService, ImageService imageService,CarService carService) {
            super(userService);
            this.carService = carService;
            this.userService = userService;
            this.imageService = imageService;
    }

    @RequestMapping(value = "/cars/create", method = RequestMethod.GET)
    public ModelAndView createCarForm(@ModelAttribute("createCarForm") final CreateCarForm form) {
        LOGGER.debug("GET Request to /cars/create");
        final ModelAndView mav = new ModelAndView("create-car/main");
        mav.addObject("brands", CarBrand.values());
        mav.addObject("allFeatures", FeatureCar.values());
        return mav;
    }

    @RequestMapping(value = "/cars/create", method = RequestMethod.POST)
    public ModelAndView postCar(@Valid @ModelAttribute("createCarForm") final CreateCarForm form,
                                final BindingResult errors) throws IOException {
        LOGGER.debug("POST Request to /cars/create");
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in CreateCarForm: {}", errors.getAllErrors());
            return createCarForm(form);
        }
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        try {
            byte[] data = form.getImageFile().getBytes();
            final Image image=imageService.createImage(data);
            carService.createCar(form.getPlate(),form.getCarInfo(),user , image.getImageId(), form.getSeats(), form.getCarBrand(), form.getFeatures());
        } catch (IOException e) {
            LOGGER.error("Error while reading image file", e);
            throw e;
        }
        return new ModelAndView("redirect:/users/profile?carAdded=true");
    }

    @RequestMapping(value = "/cars/{id:\\d+$}", method = RequestMethod.GET)
    public ModelAndView publicCar(@PathVariable("id") final long carId, @ModelAttribute("updateCarForm") final UpdateCarForm form){
        LOGGER.debug("GET Request to /cars/{}", carId);

        final Optional<User> userOp = userService.getCurrentUser();
        final User user = userOp.get();


        final Car car = carService.findById(carId).orElseThrow(() -> new CarNotFoundException(carId));
        form.setCarInfo(car.getInfoCar());
        form.setSeats(car.getSeats());
        form.setFeatures(car.getFeatures());

        final ModelAndView mav;
        if( car.getUser().getUserId()==user.getUserId()){
            mav = new ModelAndView("/create-car/car-detail-owner");
            mav.addObject("allFeatures", FeatureCar.values());
        }else {
            mav = new ModelAndView("/create-car/car-detail");
        }

        mav.addObject("car",car);
        mav.addObject("rating", 3.0);

        return mav;
    }

    @RequestMapping(value = "/cars/{id:\\d+$}", method = RequestMethod.POST)
    public ModelAndView carUpdate(@PathVariable("id") final long carId,@Valid @ModelAttribute("updateCarForm") final UpdateCarForm form,
                                  final BindingResult errors) throws IOException{
        LOGGER.debug("Update Request to /cars/{}", carId);
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in updateCarForm: {}", errors.getAllErrors());
            return publicCar(carId,form);
        }
        carService.ModifyCar(carId,form.getCarInfo(),form.getSeats(),form.getFeatures(), form.getImageFile().getBytes());
        return publicCar(carId,form);
    }

}
