package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SelectTripController {
    private final TripService ts;
    private final CityService cs;

    @Autowired
    public SelectTripController(TripService ts, CityService cs){
        this.ts = ts;
        this.cs = cs;
    }

    @RequestMapping(value = "/trips/",method = RequestMethod.GET)
    public ModelAndView getAllTrips(){
        return new ModelAndView("/select-trip/discovery");
    }

    @RequestMapping(value = "/trips/{id:\\d+$}")
    public ModelAndView getTripDetails(@PathVariable("id") final long tripId){
        ModelAndView mv = new ModelAndView("/select-trip/trip-detail");
        mv.addObject("tripId",tripId);
        return mv;
    }
}
