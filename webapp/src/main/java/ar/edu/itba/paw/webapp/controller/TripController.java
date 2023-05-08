package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.CreateTripForm;
import ar.edu.itba.paw.webapp.form.SearchTripForm;
import ar.edu.itba.paw.webapp.form.SelectionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TripController extends LoggedUserController {
    private final TripService tripService;
    private final CityService cityService;
    private final UserService userService;
    private final CarService carService;
    private final ImageService imageService;

    private final static long DEFAULT_PROVINCE_ID = 1;
    private final static int PAGE_SIZE = 10;
    private final static String BASE_RELATED_PATH = "/";
    private final static String LANDING_PAGE_PATH = BASE_RELATED_PATH;
    private final static String TRIP_PATH = BASE_RELATED_PATH + "trips/";
    private final static String SEARCH_TRIP_PATH = BASE_RELATED_PATH + "search";
    private final static String CREATE_TRIP_PATH = TRIP_PATH + "create";
    private static final String RESERVED_TRIPS_PATH = TRIP_PATH + "reserved";
    private static final String RESERVED_TRIPS_HISTORIC_PATH = RESERVED_TRIPS_PATH + "/history";
    private static final String CREATED_TRIPS_PATH = TRIP_PATH + "created";
    private static final String CREATED_TRIPS_HISTORIC_PATH = CREATED_TRIPS_PATH + "/history";

    @Autowired
    public TripController(final TripService tripService, final CityService cityService, final UserService userService, final CarService carService, final ImageService imageService){
        super(userService);
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
//        if(userIsDriver){
//
//        }else if (userIsPassenger){
//
//        }else{
//
//        }
        //TODO: buscar al trip en el rango especificado
        Trip trip = tripService.findById(tripId,form.getStartDate(),form.getStartTime(),form.getEndDate()).orElseThrow(TripNotFoundException::new);
        ModelAndView mv = new ModelAndView("/select-trip/main");
        mv.addObject("trip",trip);
        return mv;
    }

    private ModelAndView tripDetailsForDriver(final int tripId){
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        final List<Passenger> passengers = tripService.getPassengers(trip);
        final ModelAndView mav = new ModelAndView();
        return mav;
    }

    private ModelAndView tripDetailsForPassenger(final int tripId, final User user){
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
//        final Passenger passenger = tripService.getPassenger()
        final ModelAndView mav = new ModelAndView();
        return mav;
    }

    private ModelAndView tripDetailsForReservation(final int tripId, final User user, final SelectionForm form){
        final Trip trip = tripService.findById(tripId,form.getStartDate(),form.getStartTime(),form.getEndDate()).orElseThrow(TripNotFoundException::new);
        final ModelAndView mav = new ModelAndView();
        return mav;
    }

    @RequestMapping(value = "/trips/{id:\\d+$}",method = RequestMethod.POST)
    public ModelAndView addPassengerToTrip(@PathVariable("id") final long tripId,
                                           @Valid @ModelAttribute("selectForm") final SelectionForm form,
                                           final BindingResult errors){
        if(errors.hasErrors()){
            return getTripDetails(tripId,form);
        }
        final User passenger = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        //TODO: buscar al trip en el rango especificado
        Trip trip = tripService.findById(tripId,form.getStartDate(),form.getStartTime(),form.getEndDate()).orElseThrow(TripNotFoundException::new);
        tripService.addPassenger(trip,passenger,form.getStartDate(),form.getStartTime(),form.getEndDate());
        ModelAndView successMV = new ModelAndView("/select-trip/success");
        successMV.addObject("trip",trip);
        successMV.addObject("passenger",passenger);
        return successMV;
    }
    //TODO: preguntar como validar a page
    @RequestMapping(value = SEARCH_TRIP_PATH, method = RequestMethod.GET)
    public ModelAndView getSearchedTrips(
            @Valid @ModelAttribute("searchTripForm") final SearchTripForm form,
            final BindingResult errors,
            @RequestParam(value = "page",required = false,defaultValue = "1")  final int page){

        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final ModelAndView mav = new ModelAndView("/search/main");
        mav.addObject("cities", cities);
        if(errors.hasErrors()){
            mav.addObject("tripsContent", new PagedContent<>(new ArrayList<>(),0,0,0));
            return mav;
        }
        final PagedContent<Trip> tripsContent = tripService.getTripsByDateTimeAndOriginAndDestinationAndPrice(form.getOriginCityId(),form.getDestinationCityId(), form.getDate(),form.getTime(), form.getLastDate(), form.getTime(),form.getMinPrice(),form.getMaxPrice(),page-1,PAGE_SIZE);
        mav.addObject("tripsContent", tripsContent);
        return mav;
    }

    @RequestMapping(value = LANDING_PAGE_PATH, method = RequestMethod.GET)
    public ModelAndView landingPage(@ModelAttribute("searchTripForm") final SearchTripForm form){
        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final List<Trip> trips = tripService.getIncomingTrips(0,PAGE_SIZE).getElements();

        final ModelAndView mav = new ModelAndView("/landing/main");
        mav.addObject("trips", trips);
        mav.addObject("cities", cities);

        return mav;
    }

    @RequestMapping(value = CREATE_TRIP_PATH, method = RequestMethod.GET)
    public ModelAndView createTripForm(@ModelAttribute("createTripForm") final CreateTripForm form){
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final List<Car> userCars = carService.findByUser(user);

        final ModelAndView mav = new ModelAndView("/create-trip/main");
        mav.addObject("cities", cities);
        mav.addObject("createCarUrl", "/cars/create");
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
        final City originCity = cityService.findCityById(form.getOriginCityId()).orElseThrow(CityNotFoundException::new);
        final City destinationCity = cityService.findCityById(form.getDestinationCityId()).orElseThrow(CityNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        final Car car = carService.findById(form.getCarId()).orElseThrow(CarNotFoundException::new);
        final Trip trip = tripService.createTrip(originCity, form.getOriginAddress(), destinationCity, form.getDestinationAddress(), car, form.getDate(), form.getTime(),form.getPrice(), form.getMaxSeats(),user,form.getLastDate(), form.getTime());
        final ModelAndView mav = new ModelAndView("/create-trip/success");
        mav.addObject("trip", trip);

        return mav;
    }

    @RequestMapping(value = RESERVED_TRIPS_PATH, method = RequestMethod.GET)
    public ModelAndView getNextReservedTrips(@RequestParam(value = "page",required = true,defaultValue = "1") final int page) {
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        PagedContent<Trip> trips = tripService.getTripsWhereUserIsPassengerFuture(user, page-1, PAGE_SIZE);

        final ModelAndView mav = new ModelAndView("/reserved-trips/next");
        mav.addObject("trips", trips);
        return mav;
    }

    @RequestMapping(value = RESERVED_TRIPS_HISTORIC_PATH, method = RequestMethod.GET)
    public ModelAndView getHistoricReservedTrips(@RequestParam(value = "page",required = true,defaultValue = "1") final int page) {
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        PagedContent<Trip> trips = tripService.getTripsWhereUserIsPassengerPast(user, page-1, PAGE_SIZE);

        final ModelAndView mav = new ModelAndView("/reserved-trips/history");
        mav.addObject("trips", trips);
        return mav;
    }

    @RequestMapping(value = CREATED_TRIPS_PATH, method = RequestMethod.GET)
    public ModelAndView getNextCreatedTrips(@RequestParam(value = "page",required = true,defaultValue = "1") final int page) {
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        PagedContent<Trip> trips = tripService.getTripsCreatedByUserFuture(user, page-1, PAGE_SIZE);

        final ModelAndView mav = new ModelAndView("/created-trips/next");
        mav.addObject("trips", trips);
        mav.addObject("tripDeleted", false);
        return mav;
    }

    @RequestMapping(value = CREATED_TRIPS_HISTORIC_PATH, method = RequestMethod.GET)
    public ModelAndView getHistoricCreatedTrips(@RequestParam(value = "page",required = true,defaultValue = "1") final int page) {
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        PagedContent<Trip> trips = tripService.getTripsCreatedByUserPast(user, page-1, PAGE_SIZE);

        final ModelAndView mav = new ModelAndView("/created-trips/history");
        mav.addObject("trips", trips);
        return mav;
    }

    //TODO: preguntar si la regla de que acceda solo si es el creador esta bien en el nivel de Spring security (o deberia ser solo de los servicios)
    @RequestMapping(value = "/trips/{id:\\d+$}/delete", method = RequestMethod.POST)
    public ModelAndView deleteTrip(@PathVariable("id") final int tripId) {
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        tripService.deleteTrip(trip);
        final ModelAndView mav = getNextCreatedTrips(1);
        mav.addObject("tripDeleted", true);
        return mav;
    }
    //TODO: preguntar si deberiamos hacer una regla de seguridad que vea si esta inscripto para esto tambien
    @RequestMapping(value ="/trips/{id:\\d+$}/cancel", method = RequestMethod.POST)
    public ModelAndView cancelTrip(@PathVariable("id") final int tripId){
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        tripService.removePassenger(trip,user);
        final ModelAndView mav = getNextReservedTrips(1);
        mav.addObject("tripCancelled", true);
        return mav;
    }
}
