package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.exceptions.CityNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.TripNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@ControllerAdvice
public class ExceptionController {

    private final MessageSource messageSource;

    @Autowired
    public ExceptionController(MessageSource messageSource){
        this.messageSource = messageSource;
    }
    @ExceptionHandler(TripNotFoundException.class)
    public ModelAndView tripNotFound(){
        ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.tripNotFound",null, Locale.getDefault()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.tripNotFound.description",null,Locale.getDefault()));
        return mav;
    }
    @ExceptionHandler(CityNotFoundException.class)
    public ModelAndView cityNotFound(){
        ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.cityNotFound",null, Locale.getDefault()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.cityNotFound.description",null,Locale.getDefault()));
        return mav;
    }
    @ExceptionHandler(ImageNotFoundException.class)
    public ModelAndView imageNotFound(){
        ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.imageNotFound",null, Locale.getDefault()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.imageNotFound.description",null,Locale.getDefault()));
        return mav;
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView userNotFound(){
        ModelAndView mav = new ModelAndView("/exceptions/default");
        mav.addObject("errorMessage",messageSource.getMessage("exceptions.userNotFound",null, Locale.getDefault()));
        mav.addObject("errorDescription",messageSource.getMessage("exceptions.userNotFound.description",null,Locale.getDefault()));
        return mav;
    }
}
