package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.form.CarReviewForm;
import ar.edu.itba.paw.webapp.form.DriverReviewForm;
import ar.edu.itba.paw.webapp.form.PassengerReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ReviewController extends LoggedUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MIN_PAGE_SIZE = 1;
    private static final int FIRST_PAGE = 0;

    private static final String TRIP_PATH_REDIRECT = "redirect:/trips/";
    private static final String REVIEWED_QUERY_PARAM = "?reviewed=true";
    private static final String BASE_PATH = "/reviews";
    private static final String PASSENGER_REVIEW_PATH = BASE_PATH + "/passengers/{passengerId:\\d+}";
    private static final String DRIVER_REVIEW_PATH = BASE_PATH + "/drivers/{driverId:\\d+}";
    private static final String BASE_TRIP_PATH = BASE_PATH + "/trips/{tripId:\\d+}";
    private static final String TRIP_PASSENGERS_REVIEW_PATH = BASE_TRIP_PATH + "/passengers/{passengerId:\\d+}";
    private static final String TRIP_DRIVERS_REVIEW_PATH = BASE_TRIP_PATH + "/drivers/{driverId:\\d+}";
    private static final String TRIP_CARS_REVIEW_PATH = BASE_TRIP_PATH + "/cars/{carId:\\d+}";

    private final PassengerReviewService passengerReviewService;
    private final DriverReviewService driverReviewService;
    private final CarReviewService carReviewService;
    private final TripService tripService;
    private final UserService userService;
    private final CarService carService;

    @Autowired
    public ReviewController(final PassengerReviewService passengerReviewService, final DriverReviewService driverReviewService, final CarReviewService carReviewService, final TripService tripService, final UserService userService, final CarService carService) {
        super(userService);
        this.passengerReviewService = passengerReviewService;
        this.driverReviewService = driverReviewService;
        this.carReviewService = carReviewService;
        this.tripService = tripService;
        this.userService = userService;
        this.carService = carService;
    }

    private String getTripRedirectPath(final long tripId) {
        return TRIP_PATH_REDIRECT + tripId + REVIEWED_QUERY_PARAM;
    }

    @RequestMapping(value = TRIP_PASSENGERS_REVIEW_PATH, method = RequestMethod.POST)
    public ModelAndView reviewPassenger(
            @PathVariable("tripId") final long tripId,
            @PathVariable("passengerId") final long passengerId,
            @Valid @ModelAttribute("passengerReviewForm") final PassengerReviewForm passengerReviewForm
    ) {
        LOGGER.debug("POST request to /reviews/trips/{}/passengers/{}", tripId, passengerId);
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        final User reviewer = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final User reviewed = userService.findById(passengerId).orElseThrow(() -> new UserNotFoundException(passengerId));
        final Passenger passenger = tripService.getPassenger(trip, reviewed).orElseThrow(() -> new PassengerNotFoundException(passengerId, tripId));
        passengerReviewService.createPassengerReview(trip, reviewer, passenger, passengerReviewForm.getRating(), passengerReviewForm.getComment(), passengerReviewForm.getOption());
        return new ModelAndView(getTripRedirectPath(tripId));
    }


    @RequestMapping(value = TRIP_DRIVERS_REVIEW_PATH, method = RequestMethod.POST)
    public ModelAndView reviewDriver(
            @PathVariable("tripId") final long tripId,
            @PathVariable("driverId") final long driverId,
            @Valid @ModelAttribute("driverReviewForm") final DriverReviewForm driverReviewForm
    ) {
        LOGGER.debug("POST request to /reviews/trips/{}/drivers/{}", tripId, driverId);
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        final User reviewer = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final Passenger reviewerPassenger = tripService.getPassenger(trip, reviewer).orElseThrow(() -> new PassengerNotFoundException(reviewer.getUserId(), tripId));
        final User reviewed = userService.findById(driverId).orElseThrow(() -> new UserNotFoundException(driverId));
        driverReviewService.createDriverReview(trip, reviewerPassenger, reviewed, driverReviewForm.getRating(), driverReviewForm.getComment(), driverReviewForm.getOption());
        return new ModelAndView(getTripRedirectPath(tripId));
    }


    @RequestMapping(value = TRIP_CARS_REVIEW_PATH, method = RequestMethod.POST)
    public ModelAndView reviewCar(
            @PathVariable("tripId") final long tripId,
            @PathVariable("carId") final long carId,
            @Valid @ModelAttribute("carReviewForm") final CarReviewForm carReviewForm
    ) {
        LOGGER.debug("POST request to /reviews/trips/{}/cars/{}", tripId, carId);
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        final User reviewer = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final Passenger reviewerPassenger = tripService.getPassenger(trip, reviewer).orElseThrow(() -> new PassengerNotFoundException(reviewer.getUserId(), tripId));
        final Car car = carService.findById(carId).orElseThrow(() -> new CarNotFoundException(carId));
        carReviewService.createCarReview(trip, reviewerPassenger, car, carReviewForm.getRating(), carReviewForm.getComment(), carReviewForm.getOption());
        return new ModelAndView(getTripRedirectPath(tripId));
    }


    @RequestMapping(value = PASSENGER_REVIEW_PATH, method = RequestMethod.GET)
    public ModelAndView showPassengerReviews(
            @PathVariable("passengerId") final long passengerId,
            @RequestParam(value = "page", required = true, defaultValue = "1") final int page
    ) {
        LOGGER.debug("GET request to /reviews/passengers/{}", passengerId);
        final User user = userService.findById(passengerId).orElseThrow(() -> new UserNotFoundException(passengerId));
        final PagedContent<PassengerReview> reviewsAsPassenger = passengerReviewService.getPassengerReviews(user, page-1, DEFAULT_PAGE_SIZE);
        final PagedContent<DriverReview> reviewsAsDriver = driverReviewService.getDriverReviews(user, FIRST_PAGE, MIN_PAGE_SIZE);
        final ModelAndView mav = new ModelAndView("reviews/passenger-review-list");
        mav.addObject("reviewsAsPassengerPaged", reviewsAsPassenger);
        mav.addObject("hasReviewsAsDriver", !reviewsAsDriver.getElements().isEmpty());
        return addUserReviewProperties(mav, user);
    }

    @RequestMapping(value = DRIVER_REVIEW_PATH, method = RequestMethod.GET)
    public ModelAndView showDriverReviews(
            @PathVariable("driverId") final long driverId,
            @RequestParam(value = "page", required = true, defaultValue = "1") final int page
    ) {
        LOGGER.debug("GET request to /reviews/drivers/{}", driverId);
        final User user = userService.findById(driverId).orElseThrow(() -> new UserNotFoundException(driverId));
        final PagedContent<DriverReview> reviewsAsDriver = driverReviewService.getDriverReviews(user, page-1, DEFAULT_PAGE_SIZE);
        final PagedContent<PassengerReview> reviewsAsPassenger = passengerReviewService.getPassengerReviews(user, FIRST_PAGE, MIN_PAGE_SIZE);
        final ModelAndView mav = new ModelAndView("reviews/driver-review-list");
        mav.addObject("reviewsAsDriverPaged", reviewsAsDriver);
        mav.addObject("hasReviewsAsPassenger", !reviewsAsPassenger.getElements().isEmpty());
        return addUserReviewProperties(mav, user);
    }

    private ModelAndView addUserReviewProperties(final ModelAndView mav, final User user) {
        final Double passengerRating = passengerReviewService.getPassengerRating(user);
        final Double driverRating = driverReviewService.getDriverRating(user);
        final PagedContent<Trip> createdTrips = tripService.getTripsCreatedByUser(user, FIRST_PAGE, MIN_PAGE_SIZE);
        mav.addObject("passengerRating", passengerRating);
        mav.addObject("driverRating", driverRating);
        mav.addObject("user", user);
        mav.addObject("countTrips", createdTrips.getTotalCount());
        return mav;
    }
}
