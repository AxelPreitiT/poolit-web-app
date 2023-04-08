package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.itba.paw.interfaces.services.CityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class discoveryController {

    private final CityService cityService;

    private final UserService userService;

    private final TripService tripService;
    @Autowired
    public discoveryController(CityService cityService, UserService userService, TripService tripService){
        this.cityService = cityService;
        this.userService = userService;
        this.tripService = tripService;
    }
//    @RequestMapping(value = "/discovery", method = RequestMethod.GET)
//    public ModelAndView createTripForm(){
//        ArrayList<City> cities = cityService.getCities();
//
//        Trip trip = tripService.createTrip(cityService.findById(1),"Av Callao 1350",cityService.findById(2),"Av Cabildo 1200","AE063TP","12/2/2022","12:20",2,new User("jose@menta.com","1139150600"));
//        List<Trip> trips = new ArrayList<>();
//        trips.add(trip);
//        trips.add(trip);
//        trips.add(trip);
//        trips.add(trip);
//        trips.add(trip);
////        TODO: remplazar cuando tengamos el back de trip
////        Map<String, String> trip = new HashMap<String, String>();
////        trip.put("begin", "palermooooooooooo");
////        trip.put("end", "Nuñez");
////        trip.put("date", "02/05");
////        trip.put("time", "17:34");
////        trip.put("places", "4");
////        trip.put("places_ocuppied", "2");
////        trip.put("price", "$3000");
//
//
//        final ModelAndView mav = new ModelAndView("/discovery/main");
//        mav.addObject("trips", trips);
//        mav.addObject("cities", cities);
//
//        return mav;
//    }
}

