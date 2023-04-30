package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUser;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.CreateTripForm;
import ar.edu.itba.paw.webapp.form.SearchTripForm;
import ar.edu.itba.paw.webapp.form.SelectionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TripController {
    private final TripService tripService;
    private final CityService cityService;
    private final UserService userService;
    private final CarService carService;
    private final ImageService imageService;

    private final static long DEFAULT_PROVINCE_ID = 1;
    private final static String BASE_RELATED_PATH = "/";
    private final static String LANDING_PAGE_PATH = BASE_RELATED_PATH;
    private final static String TRIP_PATH = BASE_RELATED_PATH + "trips/";
    private final static String SEARCH_TRIP_PATH = BASE_RELATED_PATH + "search";
    private final static String CREATE_TRIP_PATH = TRIP_PATH + "create";


    @Autowired
    public TripController(TripService tripService, CityService cityService, UserService userService, CarService carService, ImageService imageService){
        this.tripService = tripService;
        this.cityService = cityService;
        this.userService = userService;
        this.carService = carService;
        this.imageService = imageService;
    }

    @RequestMapping(value = "/trips/{id:\\d+$}",method = RequestMethod.GET)
    public ModelAndView getTripDetails(@PathVariable("id") final long tripId,
                                       @ModelAttribute("selectForm") final SelectionForm form
                                       ){
        System.out.println("Start date"+form.getStartDate());
        System.out.println("End date" + form.getEndDate());
        Optional<Trip> trip = tripService.findById(tripId);
        if(!trip.isPresent()){//Usar Optional?
            throw new TripNotFoundException();
        }
        ModelAndView mv = new ModelAndView("/select-trip/main");
        mv.addObject("trip",trip.get());
        return mv;
    }

    @RequestMapping(value = "/trips/{id:\\d+$}",method = RequestMethod.POST)
    public ModelAndView addPassengerToTrip(@PathVariable("id") final long tripId,
                                           @Valid @ModelAttribute("selectForm") final SelectionForm form,
                                           final BindingResult errors){
        System.out.println("POST Start date"+form.getStartDate());
        System.out.println("POST Start time" + form.getStartTime());
        System.out.println("POST End date" + form.getEndDate());
        if(errors.hasErrors()){
            return getTripDetails(tripId,form);
        }
        System.out.println("No hay errores");
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //final String userId = userService.findByEmail(email).orElseThrow(UsernameNotFoundException::new).getUserId();
        final User passenger = userService.findByEmail(authUser.getUsername()).orElseThrow(UserNotFoundException::new);
        //User passenger = userService.createUserIfNotExists(form.getEmail(), form.getPhone(), form.getPhone());
        //sacar el form.getPhone(), esta solo para que no falle
//        boolean ans = tripService.addPassenger(tripId,passenger);
//        Optional<Trip> trip = tripService.findById(tripId);
//        if(ans && trip.isPresent()){
//        User passenger = userService.createUserIfNotExists(form.getEmail(),form.getPhone());
        System.out.println("Encuentra al usuario");
        //TODO: agregar excepciones nuestras y nuestro manejo
        Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        System.out.println("Encontre el viaje");
        boolean ans = false;
        try{
            ans = tripService.addPassenger(trip,passenger,form.getStartDate(),form.getStartTime(),form.getEndDate());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Intenta agregar al usuario");
        if(ans){
            ModelAndView successMV = new ModelAndView("/select-trip/success");
            successMV.addObject("trip",trip);
            successMV.addObject("passenger",passenger);
            return successMV;
        }
        // TODO: Throw error 500 internal server error
        return new ModelAndView("/static/not-found-404");
    }

    @RequestMapping(value = SEARCH_TRIP_PATH, method = RequestMethod.GET)
    public ModelAndView getSearchedTrips(@Valid @ModelAttribute("searchTripForm") final SearchTripForm form, final BindingResult errors){
        List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final ModelAndView mav = new ModelAndView("/search/main");
        mav.addObject("cities", cities);
        mav.addObject("searchUrl", SEARCH_TRIP_PATH);
        if(errors.hasErrors()){
            mav.addObject("trips", new ArrayList<>());
            return mav;
        }
        final List<Trip> trips = tripService.getTripsByDateTimeAndOriginAndDestination(form.getOriginCityId(),form.getDestinationCityId(), form.getDate(),form.getTime(), form.getDate(), form.getTime(),0,10).getElements();
        mav.addObject("trips", trips);
        return mav;
    }

    @RequestMapping(value = LANDING_PAGE_PATH, method = RequestMethod.GET)
    public ModelAndView landingPage(@ModelAttribute("searchTripForm") final SearchTripForm form){
        List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        List<Trip> trips = tripService.getIncomingTrips(0,10).getElements();
//        System.out.println(trips.get(0));
        final ModelAndView mav = new ModelAndView("/landing/main");
        mav.addObject("trips", trips);
        mav.addObject("cities", cities);
        mav.addObject("searchUrl", SEARCH_TRIP_PATH);
        mav.addObject("isLoggedIn", false);

        return mav;
    }

    @RequestMapping(value = CREATE_TRIP_PATH, method = RequestMethod.GET)
    public ModelAndView createTripForm(@ModelAttribute("createTripForm") final CreateTripForm form){
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userService.findByEmail(authUser.getUsername()).orElseThrow(UserNotFoundException::new);
        List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        //TODO: throw custom Exception
        List<Car> userCars = carService.findByUser(user);
        final ModelAndView mav = new ModelAndView("/create-trip/main");
        mav.addObject("cities", cities);
        mav.addObject("createTripUrl", CREATE_TRIP_PATH);
        mav.addObject("cars", userCars);
        return mav;
    }

    @RequestMapping(value = CREATE_TRIP_PATH, method = RequestMethod.POST)
    public ModelAndView createTrip(
            @Valid @ModelAttribute("createTripForm") final CreateTripForm form,
            final BindingResult errors
    ){
        if(errors.hasErrors()){
            return createTripForm(form);
        }
        City originCity = cityService.findCityById(form.getOriginCityId()).orElseThrow(CityNotFoundException::new);
        City destinationCity = cityService.findCityById(form.getDestinationCityId()).orElseThrow(CityNotFoundException::new);
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //final String userId = userService.findByEmail(email).orElseThrow(UsernameNotFoundException::new).getUserId();
        final User user = userService.findByEmail(authUser.getUsername()).orElseThrow(UserNotFoundException::new);
//        Car car = carService.createCarIfNotExists("AE062TP", "Honda Fit azul", user);
        Car car = carService.findById(form.getCarId()).orElseThrow(CarNotFoundException::new);
        //TODO: get maxSeats from car
        Trip trip = tripService.createTrip(originCity, form.getOriginAddress(), destinationCity, form.getDestinationAddress(), car, form.getDate(), form.getTime(),form.getPrice(), 1,user,form.getLastDate(), form.getTime());
        final ModelAndView mav = new ModelAndView("/create-trip/success");
        mav.addObject("trip", trip);

        return mav;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView showUploadForm() {
        ModelAndView mav = new ModelAndView("/image-tester/uploadForm");
        return mav;
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        byte[] data = file.getBytes();
        Image image=imageService.createImage(data);
        ModelAndView modelAndView = new ModelAndView("/image-tester/imageDetails");
        modelAndView.addObject("image", image);
        return modelAndView;
    }

    @RequestMapping(value = "/image/{imageId}", method = RequestMethod.GET, produces = "image/*")
    public @ResponseBody
    byte[] getImage(@PathVariable("imageId") final int imageId) {
        return imageService.findById(imageId).orElseThrow(ImageNotFoundException::new).getData();
    }

//    @RequestMapping(value = "*", method = RequestMethod.GET)
//    public ModelAndView pageNotFound() {
//        return new ModelAndView("/static/not-found-404");
//    }
}
