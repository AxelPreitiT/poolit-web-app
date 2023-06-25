package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.exceptions.CityNotFoundException;
import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.reviews.DriverReview;
import ar.edu.itba.paw.models.reviews.PassengerReview;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import ar.edu.itba.paw.interfaces.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.webapp.form.CreateUserForm;
import ar.edu.itba.paw.webapp.form.UpdateUserForm;
import ar.edu.itba.paw.webapp.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final CityService cityService;

    private final CarService carService;

    private final TripService tripService;

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
                          final CarService carService,
                          final PassengerReviewService passengerReviewService,
                          final DriverReviewService driverReviewService) {
        this.cityService = cityService;
        this.userService = userService;
        this.pawUserDetailsService = pawUserDetailsService;
        this.tripService = tripService;
        this.carService = carService;
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
    ) throws EmailAlreadyExistsException, CityNotFoundException {
        LOGGER.debug("POST Request to {}", CREATE_USER_PATH);
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in CreateUserForm: {}", errors.getAllErrors());
            return createUserGet(form);
        }
        try {
            userService.createUser(form.getUsername(), form.getSurname(), form.getEmail(), form.getPhone(),
                    form.getPassword(), form.getBornCityId(), form.getMailLocale(), null, form.getImageFile().getBytes());
        }catch (EmailAlreadyExistsException e){
            errors.rejectValue("email", "validation.email.alreadyExists");
            LOGGER.warn("Email already exists: {}", form.getEmail());
            return createUserGet(form);
        } catch (CityNotFoundException e){
            throw e;
        } catch (Exception e) {
            throw new RuntimeException();
        }
        final ModelAndView mav = new ModelAndView("/users/login");
        mav.addObject("sentToken", true);
        return mav;
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
    public ModelAndView profileView(@RequestParam(value = "carAdded", required = false, defaultValue = "false") final Boolean carAdded,
                                    @ModelAttribute("updateUserForm") final UpdateUserForm form) throws UserNotFoundException, UserNotLoggedInException {
        LOGGER.debug("GET Request to /users/profile");
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final ModelAndView mav;

        final List<Trip> futureTripsAsPassenger = tripService.getTripsWhereCurrentUserIsPassengerFuture( 0, PAGE_SIZE).getElements();
        final List<Trip> pastTripsAsPassenger = tripService.getTripsWhereCurrentUserIsPassengerPast( 0, PAGE_SIZE).getElements();
        final List<PassengerReview> reviewsAsPassenger = passengerReviewService.getPassengerReviewsOwnUser( FIRST_PAGE, REVIEW_PAGE_SIZE).getElements();
        final Double passengerRating = passengerReviewService.getPassengerRatingOwnUser();
        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);

        form.setBornCityId(user.getBornCity().getId());
        form.setMailLocale(user.getMailLocale().getLanguage());
        form.setPhone(user.getPhone());
        form.setSurname(user.getSurname());
        form.setUsername(user.getName());

        if(userService.isUser(user)){
            mav = new ModelAndView("/users/user-profile");
        } else {
            final List<Trip> futureTripsAsDriver = tripService.getTripsCreatedByCurrentUserFuture(FIRST_PAGE, PAGE_SIZE).getElements();
            final List<Trip> pastTripsAsDriver = tripService.getTripsCreatedByCurrentUserPast( FIRST_PAGE, PAGE_SIZE).getElements();
            final List<Car> cars = carService.findCurrentUserCars();
            final PagedContent<Trip> createdTrips = tripService.getTripsCreatedByCurrentUser(FIRST_PAGE,0);
            final List<DriverReview> reviewsAsDriver = driverReviewService.getDriverReviewsOwnUser( FIRST_PAGE, REVIEW_PAGE_SIZE).getElements();
            final Double driverRating = driverReviewService.getDriverRatingOwnUser();
            mav = new ModelAndView("/users/driver-profile");
            mav.addObject("futureTripsAsDriver", futureTripsAsDriver);
            mav.addObject("pastTripsAsDriver", pastTripsAsDriver);
            mav.addObject("cars", cars);
            mav.addObject("reviewsAsDriver", reviewsAsDriver);
            mav.addObject("driverRating", driverRating);
            mav.addObject("countTrips", createdTrips.getTotalCount());
            mav.addObject("carAdded", carAdded);
        }
        mav.addObject("cities", cities);
        mav.addObject("user", user);
        mav.addObject("passengerRating", passengerRating);
        mav.addObject("futureTripsAsPassenger", futureTripsAsPassenger);
        mav.addObject("pastTripsAsPassenger", pastTripsAsPassenger);
        mav.addObject("reviewsAsPassenger", reviewsAsPassenger);
        return mav;
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.POST)
    public ModelAndView profileViewUpdate(@Valid @ModelAttribute("updateUserForm") final UpdateUserForm form,
                                          final BindingResult errors) throws IOException, CityNotFoundException, UserNotFoundException, UserNotLoggedInException {
        LOGGER.debug("POST Request to /users/profile");
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in updateUserForm: {}", errors.getAllErrors());
            return profileView(false,form);
        }

        userService.modifyUser( form.getUsername(),form.getSurname(),form.getPhone(),form.getBornCityId(),form.getMailLocale(), form.getImageFile().getBytes());

        return profileView(false,form);
    }

    //TODO: cambiar para que haga un redirect al privado si es el mismo usuario
    @RequestMapping(value = "/profile/{id:\\d+$}", method = RequestMethod.GET)
    public ModelAndView publicProfile(@PathVariable("id") final long userId) throws UserNotFoundException
    {
        //Si es el usuario actual, lo redirigimos a su perfil privado
        if(userService.isCurrentUser(userId)){
            return new ModelAndView("redirect:/users/profile");
        }
        LOGGER.debug("GET Request to /profile/{}", userId);
        final User user = userService.findById(userId).orElseThrow(UserNotFoundException::new);
        final Double passengerRating = passengerReviewService.getPassengerRating(userId);
        final List<PassengerReview> reviewsAsPassenger = passengerReviewService.getPassengerReviews(userId, FIRST_PAGE, REVIEW_PAGE_SIZE).getElements();
//        boolean isBlocked = userService.isBlocked(userId);
        final ModelAndView mav = new ModelAndView("/users/public-profile");

        if(userService.isDriver(user)){
            final Double driverRating = driverReviewService.getDriverRating(userId);
            final List<DriverReview> reviewsAsDriver = driverReviewService.getDriverReviews(userId, FIRST_PAGE, REVIEW_PAGE_SIZE).getElements();
            final PagedContent<Trip> createdTrips = tripService.getTripsCreatedByUser(user,FIRST_PAGE,0);
            mav.addObject("driverRating", driverRating);
            mav.addObject("reviewsAsDriver", reviewsAsDriver);
            mav.addObject("countTrips",createdTrips.getTotalCount());
        }
//        mav.addObject("isBlocked", isBlocked);
        mav.addObject("user", user);
        mav.addObject("passengerRating", passengerRating);
        mav.addObject("reviewsAsPassenger", reviewsAsPassenger);
        return mav;
    }
    @RequestMapping(value="/profile/{id:\\d+$}/block", method = RequestMethod.POST)
    public ModelAndView profileBlock(@PathVariable("id") final long userId) throws UserNotFoundException{
        LOGGER.debug("Blocking /profile/{}", userId);

        userService.blockUser(userId);

        return new ModelAndView("redirect:/profile/" + userId);

    }
    @RequestMapping(value="/profile/{id:\\d+$}/unblock", method = RequestMethod.POST)
    public ModelAndView profileUnblock(@PathVariable("id") final long userId) throws UserNotFoundException{
        LOGGER.debug("Unblocking /profile/{}", userId);

        userService.unblockUser(userId);

        return new ModelAndView("redirect:/profile/" + userId);

    }
    @RequestMapping(value = "/changeRole", method = RequestMethod.POST)
    public ModelAndView changeRoleToDriver() throws UserNotFoundException, UserNotLoggedInException {
        LOGGER.debug("POST Request to /changeRole");
        //TODO no estoy seguro que hacer aca para facade
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        pawUserDetailsService.update(user);
        userService.changeToDriver();
        return new ModelAndView("redirect:/trips/create");
    }

    @RequestMapping(value = "/register/confirm")
    public ModelAndView confirmRegistration(@RequestParam("token") final String token) {

        if (userService.confirmRegister(token)) {
            return new ModelAndView("redirect:/");
        }
        final ModelAndView mav = new ModelAndView("/users/sendToken");
        mav.addObject("failToken", true);
        mav.addObject("emailForm", new EmailForm());
        return mav;
    }

    @RequestMapping(value = "/users/sendToken", method = RequestMethod.GET)
    public ModelAndView getTokenView(@ModelAttribute("emailForm") final EmailForm form) {
         return new ModelAndView("/users/sendToken");
    }

    @RequestMapping(value = "/users/sendToken", method = RequestMethod.POST)
    public ModelAndView sendToken(@Valid @ModelAttribute("emailForm") final EmailForm form,
                                  final BindingResult errors) throws Exception {

        if(errors.hasErrors()){
            return getTokenView(form);
        }
        final ModelAndView mav = new ModelAndView("/users/login");
        //sendVerification devuelve falso si ya esta verificado
        if(!userService.sendVerificationEmail(form.getEmail())){
            mav.addObject("alreadyValidation", true);
            return mav;
        }
        mav.addObject("sentToken", true);
        return mav;
    }


}
