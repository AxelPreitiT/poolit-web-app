package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionControllerAdvice extends LoggedUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice.class);


    @Autowired
    public ExceptionControllerAdvice(final UserService userService){
        super(userService);
    }
    @ExceptionHandler(TripNotFoundException.class)
    public ModelAndView tripNotFound(){
        LOGGER.error("Raised TripNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage","exceptions.tripNotFound");
        mav.addObject("errorDescription","exceptions.tripNotFound.description");
        return mav;
    }
    @ExceptionHandler(CityNotFoundException.class)
    public ModelAndView cityNotFound(){
        LOGGER.error("Raised CityNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage","exceptions.cityNotFound");
        mav.addObject("errorDescription","exceptions.cityNotFound.description");
        return mav;
    }
    @ExceptionHandler(ImageNotFoundException.class)
    public ModelAndView imageNotFound(){
        LOGGER.error("Raised ImageNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage", "exceptions.imageNotFound");
        mav.addObject("errorDescription","exceptions.imageNotFound.description");
        return mav;
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView userNotFound(){
        LOGGER.error("Raised UserNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage","exceptions.userNotFound");
        mav.addObject("errorDescription","exceptions.userNotFound.description");
        return mav;
    }
    @ExceptionHandler(UserNotLoggedInException.class)
    public ModelAndView userNotLoggedIn() {
        LOGGER.error("Raised UserNotLoggedInException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage", "exceptions.userNotLoggedIn");
        mav.addObject("errorDescription", "exceptions.userNotLoggedIn.description");
        return mav;
    }
    @ExceptionHandler(PassengerNotFoundException.class)
    public ModelAndView passengerNotFound(){
        LOGGER.error("Raised PassengerNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage","exceptions.passengerNotFound");
        mav.addObject("errorDescription","exceptions.passengerNotFound.description");
        return mav;
    }
    @ExceptionHandler(CarNotFoundException.class)
    public ModelAndView carNotFound(){
        LOGGER.error("Raised CarNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage","exceptions.carNotFound");
        mav.addObject("errorDescription","exceptions.carNotFound.description");
        return mav;
    }
    @ExceptionHandler(TripAlreadyStartedException.class)
    public ModelAndView tripAlreadyStarted(){
        LOGGER.error("Raised TripAlreadyStartedException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage","exceptions.tripAlreadyStarted");
        mav.addObject("errorDescription","exceptions.tripAlreadyStarted.message");
        return mav;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView illegalArgument(){
        LOGGER.error("Raised IllegalArgumentException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage", "exceptions.illegalArgument");
        mav.addObject("errorDescription", "exceptions.illegalArgument.description");
        return mav;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView illegalState(){
        LOGGER.error("Raised IllegalStateException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage","exceptions.illegalState");
        mav.addObject("errorDescription","exceptions.illegalState.description");
        return mav;
    }

}
