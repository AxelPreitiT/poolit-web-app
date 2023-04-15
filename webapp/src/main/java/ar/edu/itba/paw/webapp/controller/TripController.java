package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.SelectionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class TripController {
    private final TripService tripService;
    private final CityService cityService;
    private final UserService userService;
    private final CarService carService;

    private final static long DEFAULT_PROVINCE_ID = 1;

    @Autowired
    public TripController(TripService tripService, CityService cityService, UserService userService, CarService carService){
        this.tripService = tripService;
        this.cityService = cityService;
        this.userService = userService;
        this.carService = carService;
    }

    @RequestMapping(value = "/trips/{id:\\d+$}",method = RequestMethod.GET)
    public ModelAndView getTripDetails(@PathVariable("id") final long tripId,
                                       @ModelAttribute("selectForm") final SelectionForm form
                                       ){
//        User driver = userService.createUser("jmentasti@itba.edu.ar","1129150686");
//        Trip trip = tripService.createTrip(cityService.findById(1),"Av Callao 1348",cityService.findById(3),"Av Cabildo 1200","AE062TP","12/2/22","12:00",2,driver);
        Optional<Trip> trip = tripService.findById(tripId);
        if(!trip.isPresent()){//Usar Optional?
            return new ModelAndView("/select-trip/not-found");
        }
        ModelAndView mv = new ModelAndView("/select-trip/trip-detail");
        mv.addObject("trip",trip.get());
        return mv;
    }

    @RequestMapping(value = "/trips/{id:\\d+$}",method = RequestMethod.POST)
    public ModelAndView addPassengerToTrip(@PathVariable("id") final long tripId,
                                           @Valid @ModelAttribute("selectForm") final SelectionForm form,
                                           final BindingResult errors){
        if(errors.hasErrors()){
            return getTripDetails(tripId,form);
        }
        User passenger = userService.createUserIfNotExists(form.getEmail(),form.getPhone());
        boolean ans = tripService.addPassenger(tripId,passenger);
        ModelAndView mv = new ModelAndView("/select-trip/response");
        mv.addObject("response",ans);
        Optional<Trip> trip = tripService.findById(tripId);
        if(ans && trip.isPresent()){
            mv.addObject("trip",trip.get());
        }
        return mv;
    }
    @RequestMapping(value = {"/","/trips"}, method = RequestMethod.GET)
    public ModelAndView getTrips(){
        long originCityId = 1, destinationCityId = 2;
        List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);

        //Fix: avoid using a hardcoded id, database can use others if serial is used after
        Optional<City> originCity = cityService.findCityById(cities.get(0).getId());
        Optional<City> destinationCity = cityService.findCityById(cities.get(0).getId());
        if(!originCity.isPresent() || !destinationCity.isPresent()){
            // TODO: 404 page
            return new ModelAndView("/discovery/main");
        }

//        Trip trip = tripService.createTrip(originCity.get(),"Av Callao 1350",destinationCity.get(),"Av Cabildo 1200","corsita rojo", "AE063TP","12/2/2022","12:20",2,userService.createUserIfNotExists("jose@menta.com","1139150600"));
//        List<Trip> trips = new ArrayList<>();
//        trips.add(trip);
//        trips.add(trip);
//        trips.add(trip);
//        trips.add(trip);
//        trips.add(trip);
        //TODO: agregar logica para elegir lugares y momento
//        List<Trip> trips = tripService.getFirstNTrips(10);
        List<Trip> trips = tripService.getTripsByDateTimeAndOriginAndDestination(originCity.get().getId(),destinationCity.get().getId(),"2023-04-15","12:22");
        final ModelAndView mav = new ModelAndView("/discovery/main");
        mav.addObject("trips", trips);
        mav.addObject("cities", cities);

        return mav;
    }
    @RequestMapping(value = "/trips/create", method = RequestMethod.GET)
    public ModelAndView createTripForm(){
        List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final ModelAndView mav = new ModelAndView("/create-trip/main");
        mav.addObject("cities", cities);

        return mav;
    }

    @RequestMapping(value = "/trips/create", method = RequestMethod.POST)
    public ModelAndView createTrip(
            @RequestParam(value = "originCity", required = true) final int originCityId,
            @RequestParam(value = "originAddress", required = true) final String originAddress,
            @RequestParam(value = "destinationCity", required = true) final int destinationCityId,
            @RequestParam(value = "destinationAddress", required = true) final String destinationAddress,
            @RequestParam(value = "date", required = true) final String date,
            @RequestParam(value = "time", required = true) final String time,
            @RequestParam(value = "infoCar", required = true) final String infoCar,
            @RequestParam(value = "plate", required = true) final String plate,
            @RequestParam(value = "seats", required = true) final int seats,
            @RequestParam(value = "email", required = true) final String email,
            @RequestParam(value = "phone", required = true) final String phone
    ){
        System.out.println(date);
        System.out.println(time);
        Optional<City> originCity = cityService.findCityById(originCityId);
        Optional<City> destinationCity = cityService.findCityById(destinationCityId);
        if(!originCity.isPresent() || !destinationCity.isPresent()){
            //TODO: 404 page
            return new ModelAndView("/create-trip/response");
        }
        User user = userService.createUserIfNotExists(email,phone);
        Car car = carService.createCarIfNotExists(plate, infoCar, user.getUserId());
        //TODO: add price
        Trip trip = tripService.createTrip(originCity.get(), originAddress, destinationCity.get(), destinationAddress, car, date, time,0.0, seats,user);
        final ModelAndView mav = new ModelAndView("/create-trip/response");
        mav.addObject("trip", trip);

        return mav;
    }

}
