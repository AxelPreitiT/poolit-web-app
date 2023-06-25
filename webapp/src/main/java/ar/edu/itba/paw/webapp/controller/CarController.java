package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.CarReviewService;
import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.reviews.CarReview;
import ar.edu.itba.paw.interfaces.exceptions.CarNotFoundException;
import ar.edu.itba.paw.webapp.form.CreateCarForm;
import ar.edu.itba.paw.webapp.form.UpdateCarForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Controller
public class CarController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarController.class);
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;
    private final CarService carService;
    private final CarReviewService carReviewService;

    @Autowired
    public CarController(CarService carService, CarReviewService carReviewService) {
            this.carService = carService;
            this.carReviewService = carReviewService;
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
                                final BindingResult errors) throws IOException, UserNotFoundException {
        LOGGER.debug("POST Request to /cars/create");
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in CreateCarForm: {}", errors.getAllErrors());
            return createCarForm(form);
        }
        try {
            carService.createCar(form.getPlate(),form.getCarInfo() , form.getImageFile().getBytes(), form.getSeats(), form.getCarBrand(), form.getFeatures());
        } catch (IOException e) {
            LOGGER.error("Error while reading image file", e);
            throw e;
        }
        return new ModelAndView("redirect:/users/profile?carAdded=true");
    }

    @RequestMapping(value = "/cars/{id:\\d+$}", method = RequestMethod.GET)
    public ModelAndView publicCar(
            @PathVariable("id") final long carId,
            @RequestParam(value = "page", required = false, defaultValue = "1") final int page,
            @ModelAttribute("updateCarForm") final UpdateCarForm form
    ) throws CarNotFoundException{
        LOGGER.debug("GET Request to /cars/{}", carId);
        final Car car = carService.findById(carId).orElseThrow(() -> new CarNotFoundException());
        final Double carReviewRating = carReviewService.getCarsRating(carId);
        final PagedContent<CarReview> carReviews = carReviewService.getCarReviews(carId, page-1, DEFAULT_PAGE_SIZE);

        final ModelAndView mav;
        if( carService.currentUserIsCarOwner(car)){
            form.setCarInfo(car.getInfoCar());
            form.setSeats(car.getSeats());
            form.setFeatures(car.getFeatures());
            mav = new ModelAndView("/create-car/car-detail-owner");
            mav.addObject("allFeatures", FeatureCar.values());
        }else {
            mav = new ModelAndView("/create-car/car-detail");
        }

        mav.addObject("car",car);
        mav.addObject("rating", carReviewRating);
        mav.addObject("carReviewsPaged", carReviews);

        return mav;
    }

    @RequestMapping(value = "/cars/{id:\\d+$}", method = RequestMethod.POST)
    public ModelAndView carUpdate(@PathVariable("id") final long carId,@Valid @ModelAttribute("updateCarForm") final UpdateCarForm form,
                                  final BindingResult errors) throws IOException, CarNotFoundException{
        LOGGER.debug("Update Request to /cars/{}", carId);
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in updateCarForm: {}", errors.getAllErrors());
            return publicCar(carId, FIRST_PAGE, form);
        }
        try {
            carService.ModifyCar(carId,form.getCarInfo(),form.getSeats(),form.getFeatures(), form.getImageFile().getBytes());
        } catch (CarNotFoundException e){
            throw e;
        }
        return new ModelAndView("redirect:/cars/" + carId);
    }

}
