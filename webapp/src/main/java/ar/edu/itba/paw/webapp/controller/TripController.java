package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.form.CreateTripForm;
import ar.edu.itba.paw.webapp.form.DiscoveryForm;
import ar.edu.itba.paw.webapp.form.SelectionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
            return new ModelAndView("/static/not-found-404");
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

    @RequestMapping(value = {"/trips"}, method = RequestMethod.POST)
    public ModelAndView getSelectedTrips(@Valid @ModelAttribute("registerForm") final DiscoveryForm form, final BindingResult errors){
        if(errors.hasErrors()){
            return getTrips(form);
        }
        List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);

        final List<Trip> trips = tripService.getTripsByDateTimeAndOriginAndDestination(form.getOriginCityId(),form.getDestinationCityId(), form.getDate(),form.getTime());
        final ModelAndView mav = new ModelAndView("/discovery/main");
        mav.addObject("trips", trips);
        mav.addObject("cities", cities);

        return mav;
    }
    @RequestMapping(value = {"/","/trips"}, method = RequestMethod.GET)
    public ModelAndView getTrips(@ModelAttribute("registerForm") final DiscoveryForm form){
        List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        List<Trip> trips = tripService.getFirstNTrips(10);
        final ModelAndView mav = new ModelAndView("/discovery/main");
        mav.addObject("trips", trips);
        mav.addObject("cities", cities);

        return mav;
    }
    @RequestMapping(value = "/trips/create", method = RequestMethod.GET)
    public ModelAndView createTripForm(@ModelAttribute("createTripForm") final CreateTripForm form){
        List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final ModelAndView mav = new ModelAndView("/create-trip/main");
        mav.addObject("cities", cities);

        return mav;
    }

    @RequestMapping(value = "/trips/create", method = RequestMethod.POST)
    public ModelAndView createTrip(
            @Valid @ModelAttribute("createTripForm") final CreateTripForm form,
            final BindingResult errors
    ){
        if(errors.hasErrors()){
            return createTripForm(form);
        }
        Optional<City> originCity = cityService.findCityById(form.getOriginCityId());
        Optional<City> destinationCity = cityService.findCityById(form.getDestinationCityId());
        if(!originCity.isPresent() || !destinationCity.isPresent()){
            //TODO: 404 page
            return new ModelAndView("/create-trip/response");
        }
        User user = userService.createUserIfNotExists(form.getEmail(),form.getPhone());
        Car car = carService.createCarIfNotExists(form.getCarPlate(), form.getCarInfo(), user);
        //TODO: get price for trip
        Trip trip = tripService.createTrip(originCity.get(), form.getOriginAddress(), destinationCity.get(), form.getDestinationAddress(), car, form.getOriginDate(), form.getOriginTime(),0.0, form.getMaxSeats(),user);
        final ModelAndView mav = new ModelAndView("/create-trip/response");
        mav.addObject("trip", trip);

        return mav;
    }

    @RequestMapping(value = "*", method = RequestMethod.GET)
    public ModelAndView pageNotFound() {
        return new ModelAndView("/static/not-found-404");
    }

}
