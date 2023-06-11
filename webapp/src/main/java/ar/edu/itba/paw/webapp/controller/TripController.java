package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.NotAvailableSeatsException;
import ar.edu.itba.paw.interfaces.exceptions.TripAlreadyStartedException;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.CreateTripForm;
import ar.edu.itba.paw.webapp.form.ReviewForm;
import ar.edu.itba.paw.webapp.form.SearchTripForm;
import ar.edu.itba.paw.webapp.form.SelectionForm;
import ar.edu.itba.paw.webapp.utils.DefaultBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final static int DEFAULT_PAGE = 1;
    private final static String DEFAULT_PASSENGERS_SATE = "";
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
                                       @ModelAttribute("selectForm") final SelectionForm form,
                                       @RequestParam(name = "page", required = false, defaultValue = "1") final int passengersPage,
                                       @RequestParam(name = "status",required = false, defaultValue = "") final String passengersState,
                                       @ModelAttribute("acceptPass") final DefaultBoolean passengerAccepted,
                                       @ModelAttribute("deletePass") final DefaultBoolean passengerRejected,
                                       @ModelAttribute("notAvailableSeats") final DefaultBoolean notAvailableSeats){
        LOGGER.debug("GET Request to /trips/{}", tripId);
        final Optional<User> userOp = userService.getCurrentUser();
        if(!userOp.isPresent()){
            return tripDetailsForReservation(tripId,form);
        }
        final User user = userOp.get();
        if(tripService.userIsDriver(tripId,user)){
            return tripDetailsForDriver(tripId,passengerAccepted.getValue(),passengerRejected.getValue(),notAvailableSeats.getValue(),passengersState,passengersPage);
        }else if (tripService.userIsPassenger(tripId,user)){
            return tripDetailsForPassenger(tripId,user);
        }
        return tripDetailsForReservation(tripId,form);
    }

    private ModelAndView tripDetailsForDriver(final long tripId,final boolean passengerAccepted,final boolean passengerRejected,final boolean notAvailableSeats,final String passengersState, final int passengersPage){
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        final PagedContent<Passenger> passengers = tripService.getPassengersPaged(trip,passengersState,passengersPage-1,PAGE_SIZE);
        final double totalPrice = tripService.getTotalTripEarnings(passengers.getElements()); //TODO: arreglar
        final ModelAndView mav = new ModelAndView("/trip-info/driver");
        mav.addObject("trip",trip);
        mav.addObject("passengersContent",passengers);
        mav.addObject("totalIncome",String.format(LocaleContextHolder.getLocale(),"%.2f",totalPrice));
        mav.addObject("acceptPass",passengerAccepted);
        mav.addObject("deletePass",passengerRejected);
        mav.addObject("notAvailableSeats",notAvailableSeats);
        return mav;
    }

    private ModelAndView tripDetailsForPassenger(final long tripId, final User user){
        return tripDetailsForPassenger(tripId,user, new ReviewForm());
    }

    private ModelAndView tripDetailsForPassenger(final long tripId, final User user, final ReviewForm reviewForm){
        final Passenger passenger = tripService.getPassenger(tripId,user).orElseThrow(() -> new PassengerNotFoundException(user.getUserId(), tripId));
        final Trip trip = tripService.findById(tripId,passenger.getStartDateTime(),passenger.getEndDateTime()).orElseThrow(() -> new TripNotFoundException(tripId));
        final boolean done = reviewService.canReview(passenger);
        final boolean reviewed = reviewService.haveReview(trip ,passenger);
        final ModelAndView mav = new ModelAndView("/trip-info/passenger");
        mav.addObject("trip",trip);
        mav.addObject("done",done);
        mav.addObject("reviewed",reviewed);
        mav.addObject("passenger",passenger);
        mav.addObject("reviewForm", reviewForm);
        mav.addObject("failedReview", reviewForm.isFailedReview());
        return mav;
    }

    private ModelAndView tripDetailsForReservation(final long tripId, final SelectionForm form){
        final Trip trip = tripService.findById(tripId,form.getStartDate(),form.getStartTime(),form.getEndDate()).orElseThrow(() -> new TripNotFoundException(tripId));
        final ModelAndView mv = new ModelAndView("/select-trip/main");
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
            return getTripDetails(tripId,form,DEFAULT_PAGE,DEFAULT_PASSENGERS_SATE,new DefaultBoolean(), new DefaultBoolean(),new DefaultBoolean());
        }
        final User passenger = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final Trip trip = tripService.findById(tripId,form.getStartDate(),form.getStartTime(),form.getEndDate()).orElseThrow(() -> new TripNotFoundException(tripId));
        tripService.addPassenger(trip,passenger,form.getStartDate(),form.getStartTime(),form.getEndDate());
        final ModelAndView mav = tripDetailsForPassenger(tripId,passenger);
        mav.addObject("successInscription",true);
        return mav;
    }

    @RequestMapping(value = SEARCH_TRIP_PATH, method = RequestMethod.GET)
    public ModelAndView getSearchedTrips(
            @Valid @ModelAttribute("searchTripForm") final SearchTripForm form,
            final BindingResult errors,
            @RequestParam(value = "sortType", required = false, defaultValue = "price") final String sortType,
            @RequestParam(value = "descending",required = false,defaultValue = "false") final boolean descending,
            @RequestParam(value = "page",required = false,defaultValue = "1")  final int page){

        LOGGER.debug("GET Request to {}", SEARCH_TRIP_PATH);
        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final Optional<User> user = userService.getCurrentUser();
        final ModelAndView mav = new ModelAndView("/search/main");
        mav.addObject("cities", cities);
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in SearchTripForm: {}", errors.getAllErrors());
            mav.addObject("tripsContent", new PagedContent<>(new ArrayList<>(),0,0,0));
            return mav;
        }
        final PagedContent<Trip> tripsContent;
        if(user.isPresent()){
            tripsContent = tripService.getTripsByDateTimeAndOriginAndDestinationAndPrice(form.getOriginCityId(),form.getDestinationCityId(), form.getDate(),form.getTime(), form.getLastDate(), form.getTime(), Optional.ofNullable(form.getMinPrice()), Optional.ofNullable(form.getMaxPrice()),sortType,descending,user.get(),page-1,PAGE_SIZE);
        }else{
            tripsContent = tripService.getTripsByDateTimeAndOriginAndDestinationAndPrice(form.getOriginCityId(),form.getDestinationCityId(), form.getDate(),form.getTime(), form.getLastDate(), form.getTime(), Optional.ofNullable(form.getMinPrice()), Optional.ofNullable(form.getMaxPrice()),sortType,descending,null,page-1,PAGE_SIZE);
        }
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

    @RequestMapping(value = "/trips/{id:\\d+$}/delete", method = RequestMethod.POST)
    public ModelAndView deleteTrip(@PathVariable("id") final int tripId) {
        LOGGER.debug("POST Request to /trips/{}/delete", tripId);
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        tripService.deleteTrip(trip);
        final ModelAndView mav = getCreatedTrips(1, TIME_QUERY_PARAM_DEFAULT);
        mav.addObject("tripDeleted", true);
        return mav;
    }
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
                                   @Valid @ModelAttribute("reviewForm") final ReviewForm form,
                                   final BindingResult errors){
        LOGGER.debug("POST Request to /trips/{}/review", tripId);
        if(errors.hasErrors()) {
            LOGGER.warn("Errors found in ReviewForm: {}", errors.getAllErrors());
            form.setFailedReview(true);
            return tripDetailsForPassenger(tripId, userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new), form);
        }
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final Passenger passenger = tripService.getPassenger(tripId,user).orElseThrow(() -> new PassengerNotFoundException(user.getUserId(), tripId));
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        reviewService.createReview(trip, passenger, form.getRating(), form.getReview());
        final ModelAndView mav = tripDetailsForPassenger(tripId, user, form);
        mav.addObject("tripReviewed", true);
        return mav;
    }

    @RequestMapping(value ="/trips/{id:\\d+$}/deletePas/{user_id:\\d+$}", method = RequestMethod.POST)
    public ModelAndView rejectPassanger(@PathVariable("id") final int tripId,
                                   @PathVariable("user_id") final int userId,
                                        RedirectAttributes redirectAttributes){
        LOGGER.debug("POST DELETE passanger {}", userId);
        tripService.rejectPassenger(tripId,userId);
        redirectAttributes.addFlashAttribute("deletePass", new DefaultBoolean(true));
        return new ModelAndView(String.format("redirect:/trips/%d", tripId));

        //final ModelAndView mav = tripDetailsForDriver(tripId,DEFAULT_PASSENGERS_SATE,DEFAULT_PAGE);
        //mav.addObject("deletePass", true);
        //return mav;
    }

    @RequestMapping(value ="/trips/{id:\\d+$}/AceptPas/{user_id:\\d+$}", method = RequestMethod.POST)
    public ModelAndView acceptPassanger(@PathVariable("id") final int tripId,
                                   @PathVariable("user_id") final int userId,
                                        RedirectAttributes redirectAttributes){
        LOGGER.debug("POST DELETE passanger {}", userId);
        try{
            tripService.acceptPassenger(tripId,userId);
        }catch (NotAvailableSeatsException e){
            redirectAttributes.addFlashAttribute("notAvailableSeats", new DefaultBoolean(true));
            return new ModelAndView(String.format("redirect:/trips/%d", tripId));
        }
        redirectAttributes.addFlashAttribute("acceptPass", new DefaultBoolean(true));
        return new ModelAndView(String.format("redirect:/trips/%d", tripId));
        //final ModelAndView mav = tripDetailsForDriver(tripId,DEFAULT_PASSENGERS_SATE,DEFAULT_PAGE);
        //mav.addObject("acceptPass", true);
        //return mav;
    }
}
