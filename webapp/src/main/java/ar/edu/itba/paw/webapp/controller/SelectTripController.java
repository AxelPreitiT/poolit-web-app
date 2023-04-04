package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SelectTripController {
    private final TripService tripService;
    private final CityService cityService;

    private final UserService userService;
    @Autowired
    public SelectTripController(TripService tripService, CityService cityService, UserService userService){
        this.tripService = tripService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @RequestMapping(value = "/trips/",method = RequestMethod.GET)
    public ModelAndView getAllTrips(){
        return new ModelAndView("/select-trip/discovery");
    }

    @RequestMapping(value = "/trips/{id:\\d+$}")
    public ModelAndView getTripDetails(@PathVariable("id") final long tripId){
        ModelAndView mv = new ModelAndView("/select-trip/trip-detail");
        User driver = userService.createUser("jmentasti@itba.edu.ar","1129150686");
        Trip trip = tripService.createTrip(cityService.findById(1),"Av Callao 1348",cityService.findById(3),"Av Cabildo 1200","12/2/22","12:00",2,driver);
        mv.addObject("trip",trip);
        mv.addObject("tripId",tripId);
        return mv;
    }
}
