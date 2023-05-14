package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.TripAlreadyStartedException;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.CreateTripForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import ar.edu.itba.paw.webapp.form.SearchTripForm;
import ar.edu.itba.paw.webapp.form.SelectionForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class TripController extends LoggedUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    private final TripService tripService;
    private final ReviewService reviewService;
    private final CityService cityService;
    private final UserService userService;
    private final CarService carService;

    private final static long DEFAULT_PROVINCE_ID = 1;
    private final static int PAGE_SIZE = 10;
    private final static String BASE_RELATED_PATH = "/";
    private final static String LANDING_PAGE_PATH = BASE_RELATED_PATH;
    private final static String TRIP_PATH = BASE_RELATED_PATH + "trips/";
    private final static String SEARCH_TRIP_PATH = BASE_RELATED_PATH + "search";
    private final static String CREATE_TRIP_PATH = TRIP_PATH + "create";
    private static final String RESERVED_TRIPS_PATH = TRIP_PATH + "reserved";
    private static final String CREATED_TRIPS_PATH = TRIP_PATH + "created";

    private static final String TIME_QUERY_PARAM_NAME = "time";
    private static final String TIME_QUERY_PARAM_DEFAULT = "future";

    @Autowired
    public TripController(final TripService tripService, ReviewService reviewService, final CityService cityService, final UserService userService, final CarService carService){
        super(userService);
        this.tripService = tripService;
        this.reviewService = reviewService;
        this.cityService = cityService;
        this.userService = userService;
        this.carService = carService;
    }

    @RequestMapping(value = "/trips/{id:\\d+$}",method = RequestMethod.GET)
    public ModelAndView getTripDetails(@PathVariable("id") final long tripId,
                                       @ModelAttribute("selectForm") final SelectionForm form
                                       ){
        LOGGER.debug("GET Request to /trips/{}", tripId);
        final Optional<User> userOp = userService.getCurrentUser();
        if(!userOp.isPresent()){
            return tripDetailsForReservation(tripId,form);
        }
        final User user = userOp.get();
        if(tripService.userIsDriver(tripId,user)){
            return tripDetailsForDriver(tripId);
        }else if (tripService.userIsPassenger(tripId,user)){
            return tripDetailsForPassenger(tripId,user);
        }
        return tripDetailsForReservation(tripId,form);
    }

    private ModelAndView tripDetailsForDriver(final long tripId){
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        final List<Passenger> passengers = tripService.getPassengers(trip);
        final ModelAndView mav = new ModelAndView("/trip-info/driver");
        mav.addObject("trip",trip);
        mav.addObject("passengers",passengers);
        return mav;
    }

    private ModelAndView tripDetailsForPassenger(final long tripId, final User user){
        final Passenger passenger = tripService.getPassenger(tripId,user).orElseThrow(() -> new PassengerNotFoundException(user.getUserId(), tripId));
        final Trip trip = tripService.findById(tripId,passenger.getStartDateTime(),passenger.getEndDateTime()).orElseThrow(() -> new TripNotFoundException(tripId));
        final boolean done = reviewService.canReview(passenger);
        final boolean reviewed = reviewService.haveReview(trip ,passenger);
        final ModelAndView mav = new ModelAndView("/trip-info/passenger");
        mav.addObject("trip",trip);
        mav.addObject("done",done);
        mav.addObject("reviewed",reviewed);
        mav.addObject("passenger",passenger);
        mav.addObject("reviewForm", new ReviewForm());
        return mav;
    }

    private ModelAndView tripDetailsForReservation(final long tripId, final SelectionForm form){
        final Trip trip = tripService.findById(tripId,form.getStartDate(),form.getStartTime(),form.getEndDate()).orElseThrow(() -> new TripNotFoundException(tripId));
        ModelAndView mv = new ModelAndView("/select-trip/main");
        mv.addObject("trip",trip);
        return mv;
    }

    @RequestMapping(value = "/trips/{id:\\d+$}/join",method = RequestMethod.POST)
    public ModelAndView addPassengerToTrip(@PathVariable("id") final long tripId,
                                           @Valid @ModelAttribute("selectForm") final SelectionForm form,
                                           final BindingResult errors)throws TripAlreadyStartedException {
        LOGGER.debug("POST Request to /trips/{}/join", tripId);
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in SelectionForm: {}", errors.getAllErrors());
            return getTripDetails(tripId,form);
        }
        final User passenger = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final Trip trip = tripService.findById(tripId,form.getStartDate(),form.getStartTime(),form.getEndDate()).orElseThrow(() -> new TripNotFoundException(tripId));
        tripService.addPassenger(trip,passenger,form.getStartDate(),form.getStartTime(),form.getEndDate());
        final ModelAndView mav = tripDetailsForPassenger(tripId,passenger);
        mav.addObject("successInscription",true);
        return mav;
    }


    //TODO: preguntar como validar a page
    @RequestMapping(value = SEARCH_TRIP_PATH, method = RequestMethod.GET)
    public ModelAndView getSearchedTrips(
            @Valid @ModelAttribute("searchTripForm") final SearchTripForm form,
            final BindingResult errors,
            @RequestParam(value = "sortType", required = false, defaultValue = "price") final String sortType,
            @RequestParam(value = "descending",required = false,defaultValue = "false") final boolean descending,
            @RequestParam(value = "page",required = false,defaultValue = "1")  final int page){

        LOGGER.debug("GET Request to {}", SEARCH_TRIP_PATH);
        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final ModelAndView mav = new ModelAndView("/search/main");
        mav.addObject("cities", cities);
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in SearchTripForm: {}", errors.getAllErrors());
            mav.addObject("tripsContent", new PagedContent<>(new ArrayList<>(),0,0,0));
            return mav;
        }
        final PagedContent<Trip> tripsContent = tripService.getTripsByDateTimeAndOriginAndDestinationAndPrice(form.getOriginCityId(),form.getDestinationCityId(), form.getDate(),form.getTime(), form.getLastDate(), form.getTime(), Optional.ofNullable(form.getMinPrice()), Optional.ofNullable(form.getMaxPrice()),sortType,descending,page-1,PAGE_SIZE);
        mav.addObject("tripsContent", tripsContent);
        return mav;
    }

    @RequestMapping(value = LANDING_PAGE_PATH, method = RequestMethod.GET)
    public ModelAndView landingPage(@ModelAttribute("searchTripForm") final SearchTripForm form){
        LOGGER.debug("GET Request to {}", LANDING_PAGE_PATH);
        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final Optional<User> user = userService.getCurrentUser();

        final ModelAndView mav = new ModelAndView("/landing/main");
        if(user.isPresent()){
            final List<Trip> trips = tripService.getRecommendedTripsForUser(user.get(),0,PAGE_SIZE).getElements();
            mav.addObject("trips", trips);
        }
        mav.addObject("cities", cities);

        return mav;
    }

    @RequestMapping(value = CREATE_TRIP_PATH, method = RequestMethod.GET)
    public ModelAndView createTripForm(@ModelAttribute("createTripForm") final CreateTripForm form){
        LOGGER.debug("GET Request to {}", CREATE_TRIP_PATH);
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
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
        LOGGER.debug("POST Request to {}", CREATE_TRIP_PATH);
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in CreateTripForm: {}", errors.getAllErrors());
            return createTripForm(form);
        }
        final City originCity = cityService.findCityById(form.getOriginCityId()).orElseThrow(() -> new CityNotFoundException(form.getOriginCityId()));
        final City destinationCity = cityService.findCityById(form.getDestinationCityId()).orElseThrow(() -> new CityNotFoundException(form.getDestinationCityId()));
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final Car car = carService.findById(form.getCarId()).orElseThrow(() -> new CarNotFoundException(form.getCarId()));
        final Trip trip = tripService.createTrip(originCity, form.getOriginAddress(), destinationCity, form.getDestinationAddress(), car, form.getDate(), form.getTime(),form.getPrice(), form.getMaxSeats(),user,form.getLastDate(), form.getTime());
        final ModelAndView mav = new ModelAndView("/create-trip/success");
        mav.addObject("trip", trip);

        return mav;
    }

    @RequestMapping(value = RESERVED_TRIPS_PATH, method = RequestMethod.GET)
    public ModelAndView getReservedTrips(@RequestParam(value = "page",required = true,defaultValue = "1") final int page,
                                         @RequestParam(value = TIME_QUERY_PARAM_NAME, required = false, defaultValue = TIME_QUERY_PARAM_DEFAULT) final String time) {

        LOGGER.debug("GET Request to {}", RESERVED_TRIPS_PATH);
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final PagedContent<Trip> trips = Objects.equals(time, "past") ? tripService.getTripsWhereUserIsPassengerPast(user, page-1, PAGE_SIZE) : tripService.getTripsWhereUserIsPassengerFuture(user, page-1, PAGE_SIZE);

        final ModelAndView mav = new ModelAndView("/reserved-trips/main");
        mav.addObject("trips", trips);
        mav.addObject("url", RESERVED_TRIPS_PATH);
        return mav;
    }

    @RequestMapping(value = CREATED_TRIPS_PATH, method = RequestMethod.GET)
    public ModelAndView getCreatedTrips(@RequestParam(value = "page",required = true,defaultValue = "1") final int page,
                                        @RequestParam(value = TIME_QUERY_PARAM_NAME, required = false, defaultValue = TIME_QUERY_PARAM_DEFAULT) final String time) {

        LOGGER.debug("GET Request to {}", CREATED_TRIPS_PATH);
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);

        final PagedContent<Trip> trips = Objects.equals(time, "past") ? tripService.getTripsCreatedByUserPast(user, page-1, PAGE_SIZE) : tripService.getTripsCreatedByUserFuture(user, page-1, PAGE_SIZE);

        final ModelAndView mav = new ModelAndView("/created-trips/main");
        mav.addObject("trips", trips);
        mav.addObject("tripDeleted", false);
        mav.addObject("url", CREATED_TRIPS_PATH);
        return mav;
    }

    //TODO: preguntar si la regla de que acceda solo si es el creador esta bien en el nivel de Spring security (o deberia ser solo de los servicios)
    //este si
    @RequestMapping(value = "/trips/{id:\\d+$}/delete", method = RequestMethod.POST)
    public ModelAndView deleteTrip(@PathVariable("id") final int tripId) {
        LOGGER.debug("POST Request to /trips/{}/delete", tripId);
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        tripService.deleteTrip(trip);
        final ModelAndView mav = getCreatedTrips(1, TIME_QUERY_PARAM_DEFAULT);
        mav.addObject("tripDeleted", true);
        return mav;
    }
    //TODO: preguntar si deberiamos hacer una regla de seguridad que vea si esta inscripto para esto tambien
    @RequestMapping(value ="/trips/{id:\\d+$}/cancel", method = RequestMethod.POST)
    public ModelAndView cancelTrip(@PathVariable("id") final int tripId){
        LOGGER.debug("POST Request to /trips/{}/cancel", tripId);
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        tripService.removePassenger(trip,user);
        final ModelAndView mav = getReservedTrips(1, TIME_QUERY_PARAM_DEFAULT);
        mav.addObject("tripCancelled", true);
        return mav;
    }

    @RequestMapping(value ="/trips/{id:\\d+$}/review", method = RequestMethod.POST)
    public ModelAndView reviewTrip(@PathVariable("id") final int tripId,
                                   @ModelAttribute("reviewForm") final ReviewForm form){
        LOGGER.debug("POST Request to /trips/{}/review", tripId);
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final Passenger passenger = tripService.getPassenger(tripId,user).orElseThrow(() -> new PassengerNotFoundException(user.getUserId(), tripId));
        reviewService.createReview(tripId, passenger, form.getRating(), form.getReview());
        final ModelAndView mav = tripDetailsForPassenger(tripId, user);
        mav.addObject("tripReviewed", true);
        return mav;
    }
}
