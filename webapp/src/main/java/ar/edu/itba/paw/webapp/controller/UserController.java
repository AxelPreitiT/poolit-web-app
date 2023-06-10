package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.exceptions.CityNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.webapp.form.CreateUserForm;
import ar.edu.itba.paw.webapp.form.UpdateUserForm;
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

    private final ReviewService reviewService;
    private final CarService carService;

    private final TripService tripService;

    private final ImageService imageService;


    private final PawUserDetailsService pawUserDetailsService;

    private final UserService userService;
    private final static long DEFAULT_PROVINCE_ID = 1;
    private final static String BASE_RELATED_PATH = "/users/";
    private final static String CREATE_USER_PATH = BASE_RELATED_PATH + "create";
    private final static String LOGIN_USER_PATH = BASE_RELATED_PATH + "login";


    private final static int PAGE_SIZE = 3;

    @Autowired
    public UserController(final CityService cityService, ReviewService reviewService, final  UserService userService,
                          final PawUserDetailsService pawUserDetailsService, final TripService tripService,
                          final CarService carService, final ImageService imageService) {
        super(userService);
        this.cityService = cityService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.pawUserDetailsService = pawUserDetailsService;
        this.tripService = tripService;
        this.carService = carService;
        this.imageService = imageService;
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
    public ModelAndView profileView(@RequestParam(value = "carAdded", required = false, defaultValue = "false") final Boolean carAdded,
                                    @ModelAttribute("updateUserForm") final UpdateUserForm form){
        LOGGER.debug("GET Request to /users/profile");
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);

        final List<Trip> futureTripsPassenger = tripService.getTripsWhereUserIsPassengerFuture(user, 0, PAGE_SIZE).getElements();
        final List<Trip> pastTripsPassenger = tripService.getTripsWhereUserIsPassengerPast(user, 0, PAGE_SIZE).getElements();
        final List<Review> reviewsAsUser = reviewService.getUsersIdReviews(user);
        final List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);

        form.setBornCityId(user.getBornCity().getId());
        form.setMailLocale(user.getMailLocale().getLanguage());
        form.setPhone(user.getPhone());
        form.setSurname(user.getSurname());
        form.setUsername(user.getName());

        if(Objects.equals(user.getRole(), "USER")){

            final ModelAndView mav = new ModelAndView("/users/user-profile");
            mav.addObject("cities", cities);
            mav.addObject("user", user);
            mav.addObject("futureTripsPassanger", futureTripsPassenger);
            mav.addObject("pastTripsPassanger", pastTripsPassenger);
            mav.addObject("reviewsAsUser", reviewsAsUser);
            return mav;
        }
        final List<Review> reviews = reviewService.getDriverReviews(user);
        final List<Trip> futureTrips = tripService.getTripsCreatedByUserFuture(user, 0, PAGE_SIZE).getElements();
        final List<Trip> pastTrips = tripService.getTripsCreatedByUserPast(user, 0, PAGE_SIZE).getElements();
        final List<Car> cars = carService.findByUser(user);
        final Double rating = reviewService.getDriverRating(user);

        final ModelAndView mav = new ModelAndView("/users/driver-profile");
        mav.addObject("user", user);
        mav.addObject("rating", rating);
        mav.addObject("futureTrips", futureTrips);
        mav.addObject("pastTrips",pastTrips);
        mav.addObject("futureTripsPassanger", futureTripsPassenger);
        mav.addObject("pastTripsPassanger",pastTripsPassenger);
        mav.addObject("cars", cars);
        mav.addObject("carAdded", carAdded);
        mav.addObject("reviews", reviews);
        mav.addObject("reviewsAsUser", reviewsAsUser);
        mav.addObject("cities", cities);
        return mav;
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.POST)
    public ModelAndView profileViewUpdate(@Valid @ModelAttribute("updateUserForm") final UpdateUserForm form,
                                          final BindingResult errors) throws IOException{
        LOGGER.debug("POST Request to /users/profile");
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in updateUserForm: {}", errors.getAllErrors());
            return profileView(false,form);
        }
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);

        userService.modifyUser(user.getUserId(), form.getUsername(),form.getSurname(),form.getPhone(),form.getBornCityId(),new Locale(form.getMailLocale()), form.getImageFile().getBytes());

        return profileView(false,form);
    }

    @RequestMapping(value = "/profile/{id:\\d+$}", method = RequestMethod.GET)
    public ModelAndView profilePost(@PathVariable("id") final long userId)
    {
        LOGGER.debug("GET Request to /profile/{}", userId);
        final User user = userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        if(Objects.equals(user.getRole(), "USER")){
            List<Review> reviews = reviewService.getUsersIdReviews(user);

            final ModelAndView mav = new ModelAndView("/users/public-profile");
            mav.addObject("user", user);
            mav.addObject("reviews", reviews);
            return mav;
        }
        final List<Review> reviews = reviewService.getDriverReviews(user);
        final Double rating = reviewService.getDriverRating(user);
        final PagedContent<Trip> createdTrips = tripService.getTripsCreatedByUser(user,0,0);

        final ModelAndView mav = new ModelAndView("/users/public-profile");
        mav.addObject("user", user);
        mav.addObject("rating", rating);
        mav.addObject("countTrips",createdTrips.getTotalCount());
        mav.addObject("reviews", reviews);
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
