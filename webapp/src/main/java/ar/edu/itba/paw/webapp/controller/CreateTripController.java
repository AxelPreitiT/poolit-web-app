package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CreateTripController {

    private final TripService tripService;
    private final CityService cityService;

    private final UserService userService;

    private final CarService carService;

    @Autowired
    public CreateTripController(TripService tripService, CityService cityService, UserService userService, CarService carService){
        this.tripService = tripService;
        this.cityService = cityService;
        this.userService = userService;
        this.carService = carService;
    }

//    @RequestMapping(value = "/create-trip", method = RequestMethod.GET)
//    public ModelAndView createTripForm(){
//        ArrayList<City> cities = cityService.getCities();
//        final ModelAndView mav = new ModelAndView("/create-trip/main");
//        mav.addObject("cities", cities);
//
//        return mav;
//    }
//
//    @RequestMapping(value = "/create-trip", method = RequestMethod.POST)
//    public ModelAndView createTrip(
//            @RequestParam(value = "originCity", required = true) final int originCityId,
//            @RequestParam(value = "originAddress", required = true) final String originAddress,
//            @RequestParam(value = "destinationCity", required = true) final int destinationCityId,
//            @RequestParam(value = "destinationAddress", required = true) final String destinationAddress,
//            @RequestParam(value = "date", required = true) final String date,
//            @RequestParam(value = "time", required = true) final String time,
//            @RequestParam(value = "carInfo", required = true) final String carInfo,
//            @RequestParam(value = "seats", required = true) final int seats,
//            @RequestParam(value = "email", required = true) final String email,
//            @RequestParam(value = "phone", required = true) final String phone
//    ){
//        User user = userService.createUser(email,phone);
//        Trip trip = tripService.createTrip(cityService.findById(originCityId), originAddress, cityService.findById(destinationCityId), destinationAddress,carInfo, date, time, seats,user);
//        final ModelAndView mav = new ModelAndView("/create-trip/response");
//        mav.addObject("trip", trip);
//
//        return mav;
//    }

}
