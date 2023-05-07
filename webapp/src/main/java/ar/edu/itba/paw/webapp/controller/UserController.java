package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.*;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.webapp.exceptions.CityNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.ImageNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.form.CreateCarForm;
import ar.edu.itba.paw.webapp.auth.AuthUser;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import ar.edu.itba.paw.webapp.form.CreateUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.itba.paw.interfaces.exceptions.EmailAlreadyExistsException;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class UserController extends LoggedUserController {

    private final CityService cityService;

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
    public UserController(final CityService cityService, final  UserService userService,
                          final PawUserDetailsService pawUserDetailsService, final TripService tripService,
                        final CarService carService, final ImageService imageService){
        super(userService);
        this.cityService = cityService;
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
        List<City> cities = cityService.getCitiesByProvinceId(DEFAULT_PROVINCE_ID);
        final ModelAndView mav = new ModelAndView("users/register");
        mav.addObject("cities", cities);
        mav.addObject("postUrl", CREATE_USER_PATH);
        return mav;
    }

    @RequestMapping(value = CREATE_USER_PATH, method = RequestMethod.POST)
    public ModelAndView createUserPost(
            @Valid @ModelAttribute("createUserForm") final CreateUserForm form, final BindingResult errors
    ) throws IOException {
        if(errors.hasErrors()){
            return createUserGet(form);
        }
        byte[] data = form.getImageFile().getBytes();
        Image image=imageService.createImage(data);
        City originCity = cityService.findCityById(form.getBornCityId()).orElseThrow(CityNotFoundException::new);
        try {
            userService.createUser(form.getUsername(), form.getSurname(), form.getEmail(), form.getPhone(),
                    form.getPassword(), form.getBirthdate(), originCity, null, image.getImageId());
        }catch (EmailAlreadyExistsException e){
            errors.rejectValue("email", "validation.email.alreadyExists");
            return createUserGet(form);
        }
        return new ModelAndView("redirect:/users/login" );
    }

    @RequestMapping(value = LOGIN_USER_PATH, method = RequestMethod.GET)
    public ModelAndView loginUserGet() {
        final ModelAndView mav = new ModelAndView("users/login");
        return mav;
    }

    @RequestMapping(value = LOGIN_USER_PATH, method = RequestMethod.POST)
    public ModelAndView loginUserPost() {
        return new ModelAndView("users/login");
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView profileView(){
//        SecurityContext pepe = SecurityContextHolder.getContext();
        //TODO: ver por que explota si no esta autenticado
//        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        final User user = userService.findByEmail(authUser.getUsername()).orElseThrow(UserNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if(Objects.equals(user.getRole(), "USER")){
            List<Trip> futureTrips = tripService.getTripsWhereUserIsPassengerFuture(user, 0, PAGE_SIZE).getElements();
            List<Trip> pastTrips = tripService.getTripsWhereUserIsPassengerPast(user, 0, PAGE_SIZE).getElements();

            final ModelAndView mav = new ModelAndView("/users/user-profile");
            mav.addObject("user", user);
            mav.addObject("futureTrips", futureTrips);
            mav.addObject("pastTrips", pastTrips);
            return mav;
        }
        final List<Trip> futureTrips = tripService.getTripsCreatedByUserFuture(user, 0, PAGE_SIZE).getElements();
        final List<Trip> pastTrips = tripService.getTripsCreatedByUserPast(user, 0, PAGE_SIZE).getElements();
        final List<Car> cars = carService.findByUser(user);

        final ModelAndView mav = new ModelAndView("/users/driver-profile");
        mav.addObject("user", user);
        mav.addObject("futureTrips", futureTrips);
        mav.addObject("pastTrips",pastTrips);
        mav.addObject("cars", cars);
        return mav;
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.POST)
    public ModelAndView profilePost(){
//        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        final User user = userService.findByEmail(authUser.getUsername()).orElseThrow(UserNotFoundException::new);
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);

        if(Objects.equals(user.getRole(), "DRIVER")){
            //TODO: traer los que son a partir de ahora y los de antes (hacer el servicio)
            final List<Trip> futureTrips = tripService.getTripsWhereUserIsPassengerFuture(user, 0, PAGE_SIZE).getElements();
            final List<Trip> pastTrips = tripService.getTripsWhereUserIsPassengerPast(user, 0, PAGE_SIZE).getElements();

            pawUserDetailsService.update(user);
            userService.changeRole(user.getUserId(), user.getRole());

            final ModelAndView mav = new ModelAndView("/users/user-profile");
            mav.addObject("user", user);
            mav.addObject("futureTrips", futureTrips);
            mav.addObject("pastTrips", pastTrips);
            return mav;
        }
        //TODO: traer las que ya pasaron y las que van a hacerse
        final List<Trip> futureTrips = tripService.getTripsCreatedByUserFuture(user, 0, PAGE_SIZE).getElements();
        final List<Trip> pastTrips = tripService.getTripsCreatedByUserPast(user, 0, PAGE_SIZE).getElements();
        final List<Car> cars = carService.findByUser(user);

        pawUserDetailsService.update(user);
        userService.changeRole(user.getUserId(), user.getRole());

        final ModelAndView mav = new ModelAndView("/users/driver-profile");
        mav.addObject("user", user);
        mav.addObject("futureTrips", futureTrips);
        mav.addObject("pastTrips",pastTrips);
        mav.addObject("cars", cars);
        return mav;

    }

    /*
    @RequestMapping(value = "/profile/user", method = RequestMethod.GET)
    public ModelAndView GetUserprofile(){

        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userService.findByEmail(authUser.getUsername()).get();

        List<Trip> trips = tripService.getTripsWhereUserIsPassenger(user, 0, 3).getElements();

        final ModelAndView mav = new ModelAndView("/users/user-profile");
        mav.addObject("user", user);
        mav.addObject("trips", trips);
        return mav;
    }

    @RequestMapping(value = "/profile/user", method = RequestMethod.POST)
    public ModelAndView PostUserprofile(){
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userService.findByEmail(authUser.getUsername()).get();
        List<Trip> trips = tripService.getTripsWhereUserIsPassenger(user, 0, 3).getElements();

        pawUserDetailsService.update(user);
        userService.changeRole(user.getUserId(), user.getRole());

        final ModelAndView mav = new ModelAndView("/users/user-profile");
        mav.addObject("user", user);
        mav.addObject("trips", trips);
        return mav;
    }

    @RequestMapping(value = "/profile/driver", method = RequestMethod.GET)
    public ModelAndView GetDriverprofile(){

        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userService.findByEmail(authUser.getUsername()).get();
        List<Trip> trips = tripService.getTripsCreatedByUser(user, 0, 3).getElements();

        final ModelAndView mav = new ModelAndView("/users/driver-profile");
        mav.addObject("user", user);
        mav.addObject("trips", trips);
        return mav;
    }

    @RequestMapping(value = "/profile/driver", method = RequestMethod.POST)
    public ModelAndView Driverprofile(){
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userService.findByEmail(authUser.getUsername()).get();
        List<Trip> trips = tripService.getTripsCreatedByUser(user, 0, 3).getElements();

        pawUserDetailsService.update(user);
        userService.changeRole(user.getUserId(), user.getRole());

        final ModelAndView mav = new ModelAndView("/users/driver-profile");
        mav.addObject("user", user);
        mav.addObject("trips", trips);
        return mav;
    }
*/

    /*
    @ModelAttribute("userLogged")
    public User loggedUser(){
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userService.findByEmail(authUser.getUsername()).isPresent()){
            return userService.findByEmail(authUser.getUsername()).get();
        }
        return null;
    }
    */
}
