package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CreateTripController {

    @RequestMapping(value = "/create-trip", method = RequestMethod.GET)
    public ModelAndView createTripForm(){
        return new ModelAndView("/create-trip/main");
    }

}
