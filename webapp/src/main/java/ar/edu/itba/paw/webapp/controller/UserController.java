package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.webapp.exceptions.CityNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.webapp.form.CreateUserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Controller
public class UserController extends LoggedUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final CityService cityService;

    private final CarService carService;

    private final TripService tripService;

    private final ImageService imageService;
    private final PassengerReviewService passengerReviewService;
    private final DriverReviewService driverReviewService;


    private final PawUserDetailsService pawUserDetailsService;

    private final UserService userService;
    private final static long DEFAULT_PROVINCE_ID = 1;
    private final static int REVIEW_PAGE_SIZE = 3;
    private final static int FIRST_PAGE = 0;
    private final static String BASE_RELATED_PATH = "/users/";
    private final static String CREATE_USER_PATH = BASE_RELATED_PATH + "create";
    private final static String LOGIN_USER_PATH = BASE_RELATED_PATH + "login";


    private final static int PAGE_SIZE = 3;

    @Autowired
    public UserController(final CityService cityService, final  UserService userService,
                          final PawUserDetailsService pawUserDetailsService, final TripService tripService,
                          final CarService carService, final ImageService imageService, final PassengerReviewService passengerReviewService,
                          final DriverReviewService driverReviewService) {
        super(userService);
        this.cityService = cityService;
        this.userService = userService;
        this.pawUserDetailsService = pawUserDetailsService;
        this.tripService = tripService;
        this.carService = carService;
        this.imageService = imageService;
        this.passengerReviewService = passengerReviewService;
        this.driverReviewService = driverReviewService;
    }

    @RequestMapping(value = CREATE_USER_PATH, method = RequestMethod.GET)
    public ModelAndView createUserGet(
             @ModelAttribute("createUserForm") final CreateUserForm form
    ) {
        LOGGER.debug("GET Request to {}", CREATE_USER_PATH);
        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final ModelAndView mav = new ModelAndView("users/register");
        mav.addObject("cities", cities);
        mav.addObject("postUrl", CREATE_USER_PATH);
        return mav;
    }


    @RequestMapping(value = CREATE_USER_PATH, method = RequestMethod.POST)
    public ModelAndView createUserPost(
            @Valid @ModelAttribute("createUserForm") final CreateUserForm form, final BindingResult errors
    ) throws IOException {
        LOGGER.debug("POST Request to {}", CREATE_USER_PATH);
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in CreateUserForm: {}", errors.getAllErrors());
            return createUserGet(form);
        }
        final byte[] data = form.getImageFile().getBytes();
        final Image image=imageService.createImage(data);
        final City originCity = cityService.findCityById(form.getBornCityId()).orElseThrow(() -> new CityNotFoundException(form.getBornCityId()));
        try {
            userService.createUser(form.getUsername(), form.getSurname(), form.getEmail(), form.getPhone(),
                    form.getPassword(), originCity, new Locale(form.getMailLocale()), null, image.getImageId());
            userService.loginUser(form.getEmail(), form.getPassword());
        }catch (EmailAlreadyExistsException e){
            errors.rejectValue("email", "validation.email.alreadyExists");
            LOGGER.warn("Email already exists: {}", form.getEmail());
            return createUserGet(form);
        }
        return new ModelAndView("redirect:/" );
    }

    @RequestMapping(value = LOGIN_USER_PATH, method = RequestMethod.GET)
    public ModelAndView loginUserGet() {
        LOGGER.debug("GET Request to {}", LOGIN_USER_PATH);
        final ModelAndView mav = new ModelAndView("users/login");
        return mav;
    }

    @RequestMapping(value = LOGIN_USER_PATH, method = RequestMethod.POST)
    public ModelAndView loginUserPost() {
        LOGGER.debug("POST Request to {}", LOGIN_USER_PATH);
        return new ModelAndView("users/login");
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView profileView(@RequestParam(value = "carAdded", required = false, defaultValue = "false") final Boolean carAdded){
        LOGGER.debug("GET Request to /users/profile");
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final ModelAndView mav;

        final List<Trip> futureTripsAsPassenger = tripService.getTripsWhereUserIsPassengerFuture(user, 0, PAGE_SIZE).getElements();
        final List<Trip> pastTripsAsPassenger = tripService.getTripsWhereUserIsPassengerPast(user, 0, PAGE_SIZE).getElements();
        final List<PassengerReview> reviewsAsPassenger = passengerReviewService.getPassengerReviews(user, FIRST_PAGE, REVIEW_PAGE_SIZE).getElements();
        final Double passengerRating = passengerReviewService.getPassengerRating(user);

        if(Objects.equals(user.getRole(), "USER")){
            mav = new ModelAndView("/users/user-profile");
        } else {
            final List<Trip> futureTripsAsDriver = tripService.getTripsCreatedByUserFuture(user, FIRST_PAGE, PAGE_SIZE).getElements();
            final List<Trip> pastTripsAsDriver = tripService.getTripsCreatedByUserPast(user, FIRST_PAGE, PAGE_SIZE).getElements();
            final List<Car> cars = carService.findByUser(user);
            final PagedContent<Trip> createdTrips = tripService.getTripsCreatedByUser(user,FIRST_PAGE,0);
            final List<DriverReview> reviewsAsDriver = driverReviewService.getDriverReviews(user, FIRST_PAGE, REVIEW_PAGE_SIZE).getElements();
            final Double driverRating = driverReviewService.getDriverRating(user);
            mav = new ModelAndView("/users/driver-profile");
            mav.addObject("futureTripsAsDriver", futureTripsAsDriver);
            mav.addObject("pastTripsAsDriver", pastTripsAsDriver);
            mav.addObject("cars", cars);
            mav.addObject("reviewsAsDriver", reviewsAsDriver);
            mav.addObject("driverRating", driverRating);
            mav.addObject("countTrips", createdTrips.getTotalCount());
            mav.addObject("carAdded", carAdded);
        }

        mav.addObject("user", user);
        mav.addObject("passengerRating", passengerRating);
        mav.addObject("futureTripsAsPassenger", futureTripsAsPassenger);
        mav.addObject("pastTripsAsPassenger", pastTripsAsPassenger);
        mav.addObject("reviewsAsPassenger", reviewsAsPassenger);
        return mav;
    }

    @RequestMapping(value = "/profile/{id:\\d+$}", method = RequestMethod.GET)
    public ModelAndView profilePost(@PathVariable("id") final long userId)
    {
        LOGGER.debug("GET Request to /profile/{}", userId);
        final User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        final Double passengerRating = passengerReviewService.getPassengerRating(user);
        final List<PassengerReview> reviewsAsPassenger = passengerReviewService.getPassengerReviews(user, FIRST_PAGE, REVIEW_PAGE_SIZE).getElements();
        final ModelAndView mav = new ModelAndView("/users/public-profile");

        if(Objects.equals(user.getRole(), "DRIVER")){
            final Double driverRating = driverReviewService.getDriverRating(user);
            final List<DriverReview> reviewsAsDriver = driverReviewService.getDriverReviews(user, FIRST_PAGE, REVIEW_PAGE_SIZE).getElements();
            final PagedContent<Trip> createdTrips = tripService.getTripsCreatedByUser(user,FIRST_PAGE,0);
            mav.addObject("driverRating", driverRating);
            mav.addObject("reviewsAsDriver", reviewsAsDriver);
            mav.addObject("countTrips",createdTrips.getTotalCount());
        }
        mav.addObject("user", user);
        mav.addObject("passengerRating", passengerRating);
        mav.addObject("reviewsAsPassenger", reviewsAsPassenger);
        return mav;
    }
    @RequestMapping(value = "/changeRole", method = RequestMethod.POST)
    public ModelAndView changeRoleToDriver(){
        LOGGER.debug("POST Request to /changeRole");
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        pawUserDetailsService.update(user);
        userService.changeToDriver(user);
        return new ModelAndView("redirect:/trips/create");
    }

}
