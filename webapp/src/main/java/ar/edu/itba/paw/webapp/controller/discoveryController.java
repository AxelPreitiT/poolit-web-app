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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

        //TODO: remplazar cuando tengamos el back de trip
        Map<String, String> trip = new HashMap<String, String>();
        trip.put("begin", "palermooooooooooo");
        trip.put("end", "Nuñez");
        trip.put("date", "02/05");
        trip.put("time", "17:34");
        trip.put("places", "4");
        trip.put("places_ocuppied", "2");
        trip.put("price", "$3000");


        final ModelAndView mav = new ModelAndView("/discovery/main");
        mav.addObject("trip", trip);
        mav.addObject("cities", cities);

        return mav;
    }
}

