package ar.edu.itba.paw.webapp.spring;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.reviews.TripReviewCollection;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.form.*;
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

/*
@Controller
public class TripController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    private final TripService tripService;
    private final CityService cityService;
    private final UserService userService;
    private final CarService carService;
    private final TripReviewService tripReviewService;
    private final ReportService reportService;

    private final static long DEFAULT_PROVINCE_ID = 1;
    private final static int PAGE_SIZE = 10;
    private final static int DEFAULT_PAGE = 1;
    private final static String DEFAULT_PASSENGERS_SATE = "";
    private final static String BASE_RELATED_PATH = "/";
    private final static String LANDING_PAGE_PATH = BASE_RELATED_PATH;
    private final static String TRIP_PATH = BASE_RELATED_PATH + "trips/";
    private final static String TRIP_DETAILS_PATH = TRIP_PATH + "{id:\\d+}";
    private final static String SEARCH_TRIP_PATH = BASE_RELATED_PATH + "search";
    private final static String CREATE_TRIP_PATH = TRIP_PATH + "create";
    private static final String RESERVED_TRIPS_PATH = TRIP_PATH + "reserved";
    private static final String CREATED_TRIPS_PATH = TRIP_PATH + "created";

    private static final String TIME_QUERY_PARAM_NAME = "time";
    private static final String TIME_QUERY_PARAM_DEFAULT = "future";

    @Autowired
    public TripController(final TripService tripService, final CityService cityService, final UserService userService, final CarService carService, final TripReviewService tripReviewService,final ReportService reportService) {
        this.tripService = tripService;
        this.cityService = cityService;
        this.userService = userService;
        this.carService = carService;
        this.tripReviewService = tripReviewService;
        this.reportService = reportService;
    }

    @RequestMapping(value = TRIP_DETAILS_PATH,method = RequestMethod.GET)
    public ModelAndView getTripDetails(@PathVariable("id") final long tripId,
                                       @ModelAttribute("selectForm") final SelectionForm form,
                                       @RequestParam(name = "page", required = false, defaultValue = "1") final int passengersPage,
                                       @RequestParam(name = "status",required = false, defaultValue = "") final String passengersState,
                                       @ModelAttribute("acceptPass") final DefaultBoolean passengerAccepted,
                                       @ModelAttribute("deletePass") final DefaultBoolean passengerRejected,
                                       @ModelAttribute("notAvailableSeats") final DefaultBoolean notAvailableSeats,
                                       @RequestParam(value = "created", required = false, defaultValue = "false") final boolean created,
                                       @RequestParam(value = "joined", required = false, defaultValue = "false") final boolean joined,
                                       @RequestParam(value = "reviewed", required = false, defaultValue = "false") final boolean reviewed,
                                       @RequestParam(value = "reported", required = false, defaultValue = "false") final boolean reported,
                                       @ModelAttribute("passengerReviewForm") final PassengerReviewForm passengerReviewForm,
                                       @ModelAttribute("driverReviewForm") final DriverReviewForm driverReviewForm,
                                       @ModelAttribute("carReviewForm") final CarReviewForm carReviewForm,
                                       @ModelAttribute("reportForm") final ReportForm reportForm
                                       ) throws TripNotFoundException, UserNotFoundException, PassengerNotFoundException, UserNotLoggedInException, CarNotFoundException {
        LOGGER.debug("GET Request to /trips/{}", tripId);
        final Optional<User> userOp = userService.getCurrentUser();
        if(!userOp.isPresent()){
            return tripDetailsForReservation(tripId,form);
        }
        final User user = userOp.get();
        ModelAndView mav;
        if(tripService.userIsDriver(tripId,user)){
            mav = tripDetailsForDriver(tripId,created,passengerAccepted.getValue(),passengerRejected.getValue(),notAvailableSeats.getValue(),passengersState,passengersPage);
        }else if (tripService.userIsPassenger(tripId,user)){
            mav = tripDetailsForPassenger(tripId, user, joined);
        } else {
            return tripDetailsForReservation(tripId,form);
        }
        mav.addObject("tripReportCollection", reportService.getTripReportCollection(tripId));
        mav.addObject("reviewed", reviewed);
        mav.addObject("reported", reported);
        return mav;
    }

    private ModelAndView tripDetailsForDriver(final long tripId, final boolean created,final boolean passengerAccepted,final boolean passengerRejected,final boolean notAvailableSeats,final String passengersState, final int passengersPage) throws TripNotFoundException, PassengerNotFoundException, UserNotLoggedInException {
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        final PagedContent<Passenger> passengers = tripService.getPassengersPaged(trip,passengersState,passengersPage-1,PAGE_SIZE);
        final double totalPrice = tripService.getTotalTripEarnings(tripId);
        final TripReviewCollection tripReviewCollection =  tripReviewService.getReviewsForDriver(tripId);
        final ModelAndView mav = new ModelAndView("/trip-info/driver");
        mav.addObject("trip",trip);
        mav.addObject("passengersContent",passengers);
        mav.addObject("totalIncome",String.format(LocaleContextHolder.getLocale(),"%.2f",totalPrice));
        mav.addObject("acceptPass",passengerAccepted);
        mav.addObject("deletePass",passengerRejected);
        mav.addObject("notAvailableSeats",notAvailableSeats);
        mav.addObject("tripReviewCollection", tripReviewCollection);
        mav.addObject("created", created);
        return mav;
    }

    private ModelAndView tripDetailsForPassenger(final long tripId, final User user, final boolean joined) throws TripNotFoundException, UserNotFoundException, PassengerNotFoundException, CarNotFoundException, UserNotLoggedInException {
        final Passenger passenger = tripService.getPassenger(tripId,user).orElseThrow(PassengerNotFoundException::new);
        final Trip trip = tripService.findById(tripId,passenger.getStartDateTime(),passenger.getEndDateTime()).orElseThrow(TripNotFoundException::new);
        final List<Passenger> passengers = tripService.getAcceptedPassengers(trip, passenger.getStartDateTime(), passenger.getEndDateTime());
        final TripReviewCollection tripReviewCollection = tripReviewService.getReviewsForPassenger(tripId,user.getUserId());
        final ModelAndView mav = new ModelAndView("/trip-info/passenger");
        mav.addObject("trip",trip);
        mav.addObject("currentPassenger",passenger);
        mav.addObject("tripReviewCollection", tripReviewCollection);
        mav.addObject("passengers",passengers);
        mav.addObject("joined", joined);
        return mav;
    }

    private ModelAndView tripDetailsForReservation(final long tripId, final SelectionForm form) throws TripNotFoundException{
        final Trip trip = tripService.findById(tripId,form.getStartDate(),form.getStartTime(),form.getEndDate()).orElseThrow(TripNotFoundException::new);
        final ModelAndView mv = new ModelAndView("/select-trip/main");
        mv.addObject("trip",trip);
        return mv;
    }

    @RequestMapping(value = "/trips/{id:\\d+$}/join",method = RequestMethod.POST)
    public ModelAndView addPassengerToTrip(@PathVariable("id") final long tripId,
                                           @Valid @ModelAttribute("selectForm") final SelectionForm form,
                                           final BindingResult errors)throws TripAlreadyStartedException, TripNotFoundException, UserNotFoundException {
        LOGGER.debug("POST Request to /trips/{}/join", tripId);
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in SelectionForm: {}", errors.getAllErrors());
            return new ModelAndView("redirect:" + TRIP_DETAILS_PATH.replace("{id}",String.valueOf(tripId)));
        }
        tripService.addCurrentUser(tripId,form.getStartDate(),form.getStartTime(),form.getEndDate());
        return new ModelAndView("redirect:/trips/" + tripId + "?joined=true");
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
        final ModelAndView mav = new ModelAndView("/search/main");
        mav.addObject("cities", cities);
        mav.addObject("carFeatures", FeatureCar.values());
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in SearchTripForm: {}", errors.getAllErrors());
            mav.addObject("tripsContent", new PagedContent<>(new ArrayList<>(),0,0,0));
            return mav;
        }
        final PagedContent<Trip> tripsContent = tripService.getTripsByDateTimeAndOriginAndDestinationAndPrice(form.getOriginCityId(),form.getDestinationCityId(), form.getDate(),form.getTime(), form.getLastDate(), form.getTime(), form.getMinPrice(), form.getMaxPrice(),sortType,descending,form.getCarFeatures(),page-1,PAGE_SIZE);
        mav.addObject("tripsContent", tripsContent);
        return mav;
    }

    @RequestMapping(value = LANDING_PAGE_PATH, method = RequestMethod.GET)
    public ModelAndView landingPage(@ModelAttribute("searchTripForm") final SearchTripForm form){
        LOGGER.debug("GET Request to {}", LANDING_PAGE_PATH);
        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final ModelAndView mav = new ModelAndView("/landing/main");
        final List<Trip> trips = tripService.getRecommendedTripsForCurrentUser(DEFAULT_PAGE-1,PAGE_SIZE).getElements();
        mav.addObject("trips", trips);
        mav.addObject("cities", cities);
        mav.addObject("carFeatures", FeatureCar.values());
        return mav;
    }

    @RequestMapping(value = CREATE_TRIP_PATH, method = RequestMethod.GET)
    public ModelAndView createTripForm(
            @ModelAttribute("createTripForm") final CreateTripForm form,
            @RequestParam(value = "carAdded", required = false, defaultValue = "false") final boolean carAdded
    ) throws UserNotFoundException{
        LOGGER.debug("GET Request to {}", CREATE_TRIP_PATH);
        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final List<Car> userCars = carService.findCurrentUserCars();
        final ModelAndView mav = new ModelAndView("/create-trip/main");
        mav.addObject("cities", cities);
        mav.addObject("createCarUrl", "/cars/create?firstCar=true");
        mav.addObject("carAdded", carAdded);
        mav.addObject("cars", userCars);
        return mav;
    }

    @RequestMapping(value = CREATE_TRIP_PATH, method = RequestMethod.POST)
    public ModelAndView createTrip(
            @Valid @ModelAttribute("createTripForm") final CreateTripForm form,
            final BindingResult errors
    ) throws CityNotFoundException, CarNotFoundException, UserNotFoundException {
        LOGGER.debug("POST Request to {}", CREATE_TRIP_PATH);
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in CreateTripForm: {}", errors.getAllErrors());
            return createTripForm(form, false);
        }
        final Trip trip = tripService.createTrip(form.getOriginCityId(), form.getOriginAddress(), form.getDestinationCityId(), form.getDestinationAddress(), form.getCarId(), form.getDate(), form.getTime(),form.getPrice(), form.getMaxSeats(),form.getLastDate(), form.getTime());
        return new ModelAndView("redirect:/trips/" + trip.getTripId() + "?created=true");
    }

    @RequestMapping(value = RESERVED_TRIPS_PATH, method = RequestMethod.GET)
    public ModelAndView getReservedTrips(@RequestParam(value = "page",required = true,defaultValue = "1") final int page,
                                         @RequestParam(value = TIME_QUERY_PARAM_NAME, required = false, defaultValue = TIME_QUERY_PARAM_DEFAULT) final String time,
                                         @ModelAttribute("tripCancelled") final DefaultBoolean tripCancelled) throws UserNotFoundException{

        LOGGER.debug("GET Request to {}", RESERVED_TRIPS_PATH);
        final PagedContent<Trip> trips = Objects.equals(time, "past") ? tripService.getTripsWhereCurrentUserIsPassengerPast(page-1, PAGE_SIZE) : tripService.getTripsWhereCurrentUserIsPassengerFuture(page-1, PAGE_SIZE);

        final ModelAndView mav = new ModelAndView("/reserved-trips/main");
        mav.addObject("tripCancelled", tripCancelled.getValue());
        mav.addObject("trips", trips);
        mav.addObject("url", RESERVED_TRIPS_PATH);
        return mav;
    }

    @RequestMapping(value = CREATED_TRIPS_PATH, method = RequestMethod.GET)
    public ModelAndView getCreatedTrips(@RequestParam(value = "page",required = true,defaultValue = "1") final int page,
                                        @RequestParam(value = TIME_QUERY_PARAM_NAME, required = false, defaultValue = TIME_QUERY_PARAM_DEFAULT) final String time,
                                        @ModelAttribute("tripDeleted") final DefaultBoolean tripDeleted) throws UserNotFoundException{

        LOGGER.debug("GET Request to {}", CREATED_TRIPS_PATH);
        final PagedContent<Trip> trips = Objects.equals(time, "past") ? tripService.getTripsCreatedByCurrentUserPast(page-1, PAGE_SIZE) : tripService.getTripsCreatedByCurrentUserFuture(page-1, PAGE_SIZE);

        final ModelAndView mav = new ModelAndView("/created-trips/main");
        mav.addObject("trips", trips);
        mav.addObject("tripDeleted", tripDeleted.getValue());
        mav.addObject("url", CREATED_TRIPS_PATH);
        return mav;
    }

    @RequestMapping(value = "/trips/{id:\\d+$}/delete", method = RequestMethod.POST)
    public ModelAndView deleteTrip(@PathVariable("id") final int tripId, RedirectAttributes redirectAttributes) throws UserNotFoundException, TripNotFoundException{
        LOGGER.debug("POST Request to /trips/{}/delete", tripId);
        tripService.deleteTrip(tripId);
        redirectAttributes.addFlashAttribute("tripDeleted",new DefaultBoolean(true));
        return new ModelAndView(String.format("redirect:%s",CREATED_TRIPS_PATH));

    }
    @RequestMapping(value ="/trips/{id:\\d+$}/cancel", method = RequestMethod.POST)
    public ModelAndView cancelTrip(@PathVariable("id") final int tripId, RedirectAttributes redirectAttributes) throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException {
        LOGGER.debug("POST Request to /trips/{}/cancel", tripId);
        tripService.removeCurrentUserAsPassenger(tripId);
        redirectAttributes.addFlashAttribute("tripCancelled",new DefaultBoolean(true));
        return new ModelAndView(String.format("redirect:%s",RESERVED_TRIPS_PATH));
    }

    @RequestMapping(value ="/trips/{id:\\d+$}/deletePas/{user_id:\\d+$}", method = RequestMethod.POST)
    public ModelAndView rejectPassanger(@PathVariable("id") final int tripId,
                                        @PathVariable("user_id") final int userId,
                                        RedirectAttributes redirectAttributes){
        LOGGER.debug("POST Request to /trips/{}/deletePas/{}", tripId, userId);
        tripService.rejectPassenger(tripId,userId);
        redirectAttributes.addFlashAttribute("deletePass", new DefaultBoolean(true));
        return new ModelAndView(String.format("redirect:/trips/%d", tripId));
    }

    @RequestMapping(value ="/trips/{id:\\d+$}/AceptPas/{user_id:\\d+$}", method = RequestMethod.POST)
    public ModelAndView acceptPassanger(@PathVariable("id") final int tripId,
                                        @PathVariable("user_id") final int userId,
                                        RedirectAttributes redirectAttributes){
        LOGGER.debug("POST Request to /trips/{}/AceptPas/{}", tripId, userId);
        try{
            tripService.acceptPassenger(tripId,userId);
        }catch (NotAvailableSeatsException e){
            redirectAttributes.addFlashAttribute("notAvailableSeats", new DefaultBoolean(true));
            return new ModelAndView(String.format("redirect:/trips/%d", tripId));
        }
        redirectAttributes.addFlashAttribute("acceptPass", new DefaultBoolean(true));
        return new ModelAndView(String.format("redirect:/trips/%d", tripId));
    }
}

 */
