package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@ControllerAdvice
public class ExceptionController extends LoggedUserController {

    private final MessageSource messageSource;

    @Autowired
    public ExceptionController(final UserService userService, final MessageSource messageSource){
        super(userService);
        this.messageSource = messageSource;
    }
    @ExceptionHandler(TripNotFoundException.class)
    public ModelAndView tripNotFound(){
        ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.tripNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.tripNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(CityNotFoundException.class)
    public ModelAndView cityNotFound(){
        ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.cityNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.cityNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(ImageNotFoundException.class)
    public ModelAndView imageNotFound(){
        ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.imageNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.imageNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView userNotFound(){
        ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.userNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.userNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
    @ExceptionHandler(CarNotFoundException.class)
    public ModelAndView carNotFound(){
        ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.carNotFound",null, LocaleContextHolder.getLocale()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.carNotFound.description",null,LocaleContextHolder.getLocale()));
        return mav;
    }
}
