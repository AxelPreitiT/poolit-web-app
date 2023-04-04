package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;

@Controller
public class CreateTripController {

    private final TripService tripService;
    private final CityService cityService;

    private final UserService userService;

    @Autowired
    public CreateTripController(TripService tripService, CityService cityService, UserService userService){
        this.tripService = tripService;
        this.cityService = cityService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create-trip", method = RequestMethod.GET)
    public ModelAndView createTripForm(){
        ArrayList<City> cities = cityService.getCities();
        final ModelAndView mav = new ModelAndView("/create-trip/main");
        mav.addObject("cities", cities);

        return mav;
    }

    @RequestMapping(value = "/create-trip", method = RequestMethod.POST)
    public ModelAndView createTrip(
            @RequestParam(value = "originCity", required = true) final int originCityId,
            @RequestParam(value = "originAddress", required = true) final String originAddress,
            @RequestParam(value = "destinationCity", required = true) final int destinationCityId,
            @RequestParam(value = "destinationAddress", required = true) final String destinationAddress,
            @RequestParam(value = "date", required = true) final String date,
            @RequestParam(value = "time", required = true) final String time,
            @RequestParam(value = "seats", required = true) final int seats,
            @RequestParam(value = "email", required = true) final String email,
            @RequestParam(value = "phone", required = true) final String phone
    ){
        User user = userService.createUser(email,phone);
        Trip trip = tripService.createTrip(cityService.findById(originCityId), originAddress, cityService.findById(destinationCityId), destinationAddress, date, time, seats,user);
        final ModelAndView mav = new ModelAndView("/create-trip/response");
        mav.addObject("trip", trip);

        return mav;
    }

}
