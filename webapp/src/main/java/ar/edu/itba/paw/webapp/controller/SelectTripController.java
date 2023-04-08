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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

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
        return new ModelAndView("/");
    }

    @RequestMapping(value = "/trips/{id:\\d+$}",method = RequestMethod.GET)
    public ModelAndView getTripDetails(@PathVariable("id") final long tripId){
        User driver = userService.createUser("jmentasti@itba.edu.ar","1129150686");
        Trip trip = tripService.createTrip(cityService.findById(1),"Av Callao 1348",cityService.findById(3),"Av Cabildo 1200","AE062TP","12/2/22","12:00",2,driver);
//        Trip trip = tripService.findById(tripId);
        if(trip==null){//Usar Optional?
            return new ModelAndView("/select-trip/not-found");
        }
        ModelAndView mv = new ModelAndView("/select-trip/trip-detail");
        mv.addObject("trip",trip);
        return mv;
    }

    @RequestMapping(value = "/trips/{id:\\d+$}",method = RequestMethod.POST)
    public ModelAndView addPassengerToTrip(@PathVariable("id") final long tripId,
                                           @RequestParam(value = "email",required = true) final String email,
                                           @RequestParam(value = "phone",required = true) final String phone){
        User passenger = userService.createUser(email,phone);
        boolean ans = tripService.addPassenger(tripId,passenger);
        ModelAndView mv = new ModelAndView("/select-trip/response");
        mv.addObject("response",ans);
        if(ans){
            mv.addObject("trip",tripService.findById(tripId));
        }
        return mv;
    }
}
