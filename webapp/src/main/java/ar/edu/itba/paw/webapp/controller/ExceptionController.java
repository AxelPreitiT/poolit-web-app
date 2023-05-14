package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.TripAlreadyStartedException;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@ControllerAdvice
public class ExceptionController extends LoggedUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    private final MessageSource messageSource;

    @Autowired
    public ExceptionController(final UserService userService, final MessageSource messageSource){
        super(userService);
        this.messageSource = messageSource;
    }
    @ExceptionHandler(TripNotFoundException.class)
    public ModelAndView tripNotFound(){
        LOGGER.warn("Raised TripNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.tripNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.tripNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(CityNotFoundException.class)
    public ModelAndView cityNotFound(){
        LOGGER.warn("Raised CityNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.cityNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.cityNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(ImageNotFoundException.class)
    public ModelAndView imageNotFound(){
        LOGGER.warn("Raised ImageNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.imageNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.imageNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView userNotFound(){
        LOGGER.warn("Raised UserNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.userNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.userNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(UserNotLoggedInException.class)
    public ModelAndView userNotLoggedIn() {
        LOGGER.warn("Raised UserNotLoggedInException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage", messageSource.getMessage("exceptions.userNotLoggedIn", null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription", messageSource.getMessage("exceptions.userNotLoggedIn.description", null, LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(PassengerNotFoundException.class)
    public ModelAndView passengerNotFound(){
        LOGGER.warn("Raised PassengerNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.passengerNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.passengerNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(CarNotFoundException.class)
    public ModelAndView carNotFound(){
        LOGGER.warn("Raised CarNotFoundException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.carNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.carNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(TripAlreadyStartedException.class)
    public ModelAndView tripAlreadyStarted(){
        LOGGER.warn("Raised TripAlreadyStartedException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.tripAlreadyStarted",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.tripAlreadyStarted.message",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView illegalArgument(){
        LOGGER.warn("Raised IllegalArgumentException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.illegalArgument",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.illegalArgument.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView illegalState(){
        LOGGER.warn("Raised IllegalStateException in controller");
        final ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.illegalState",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.illegalState.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }

}
