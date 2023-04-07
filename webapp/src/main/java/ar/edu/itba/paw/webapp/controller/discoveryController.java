package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ViewResolver;
import ar.edu.itba.paw.interfaces.services.CityService;

import java.util.ArrayList;

@Controller
public class discoveryController {

    private final CityService cs;

    @Autowired
    public discoveryController(CityService cs){
        this.cs = cs;
    }
    @RequestMapping(value = "/discovery", method = RequestMethod.GET)
    public ModelAndView createTripForm(){
        ArrayList<City> cities = cs.getCities();
        final ModelAndView mav = new ModelAndView("/discovery/main");
        mav.addObject("cities", cities);

        return mav;
    }
}

