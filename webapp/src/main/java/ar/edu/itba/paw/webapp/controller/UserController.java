package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.webapp.exceptions.CityNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.form.CreateCarForm;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUser;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import ar.edu.itba.paw.webapp.form.CreateUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class UserController {

    private final CityService cityService;

    private final CarService carService;

    private final TripService tripService;

    private final PawUserDetailsService pawUserDetailsService;

    private final UserService userService;
    private final static long DEFAULT_PROVINCE_ID = 1;
    private final static String BASE_RELATED_PATH = "/users/";
    private final static String CREATE_USER_PATH = BASE_RELATED_PATH + "create";
    private final static String LOGIN_USER_PATH = BASE_RELATED_PATH + "login";

    @Autowired
    public UserController(final CityService cityService, final  UserService userService,
                          final PawUserDetailsService pawUserDetailsService, final TripService tripService,
                        final CarService carService){
        this.cityService = cityService;
        this.userService = userService;
        this.pawUserDetailsService = pawUserDetailsService;
        this.tripService = tripService;
        this.carService = carService;
    }

    @RequestMapping(value = CREATE_USER_PATH, method = RequestMethod.GET)
    public ModelAndView createUserGet(
            @Valid @ModelAttribute("createUserForm") final CreateUserForm form
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
    ){
        if(errors.hasErrors()){
            return createUserGet(form);
        }
        City originCity = cityService.findCityById(form.getBornCityId()).orElseThrow(CityNotFoundException::new);
        userService.createUserIfNotExists(form.getUsername(), form.getSurname(), form.getEmail(), form.getPhone(),
                form.getPassword(), form.getBirthdate(), originCity, null);
        return new ModelAndView("redirect:/users/login" );
    }

    @RequestMapping(value = LOGIN_USER_PATH, method = RequestMethod.GET)
    public ModelAndView loginUserGet() {
        final ModelAndView mav = new ModelAndView("users/login");
        mav.addObject("postUrl", LOGIN_USER_PATH);
        return mav;
    }

    @RequestMapping(value = LOGIN_USER_PATH, method = RequestMethod.POST)
    public ModelAndView loginUserPost() {
        return new ModelAndView("/helloworld/index");
    }

    /*
    @RequestMapping(value = LOGIN_USER_PATH + "/error", method = RequestMethod.POST)
    public ModelAndView loginUserPostError() {
        final ModelAndView mav = new ModelAndView("users/login");
        mav.addObject("postUrl", LOGIN_USER_PATH);
        return mav;
    }
    */

    /*
    @RequestMapping("/loginFailed")
    public void loginFailed(HttpServletRequest request) {
        AuthenticationException authenticationException = (AuthenticationException) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (authenticationException != null) {
            if(authenticationException.getCause() != null) {
                throw (AuthenticationException) authenticationException.getCause();
            } else {
                throw authenticationException;
            }
        }
    }
    */

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView profileView(){
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userService.findByEmail(authUser.getUsername()).orElseThrow(UserNotFoundException::new);

        if(Objects.equals(user.getRole(), "USER")){
            List<Trip> trips = tripService.getTripsWhereUserIsPassenger(user, 0, 3).getElements();

            final ModelAndView mav = new ModelAndView("/users/user-profile");
            mav.addObject("user", user);
            mav.addObject("trips", trips);
            return mav;
        }

        List<Trip> trips = tripService.getTripsCreatedByUser(user, 0, 3).getElements();
        List<Car> cars = carService.findByUser(user);

        final ModelAndView mav = new ModelAndView("/users/driver-profile");
        mav.addObject("user", user);
        mav.addObject("trips", trips);
        mav.addObject("cars", cars);
        return mav;
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.POST)
    public ModelAndView profilePost(){
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userService.findByEmail(authUser.getUsername()).orElseThrow(UserNotFoundException::new);


        if(Objects.equals(user.getRole(), "DRIVER")){
            List<Trip> trips = tripService.getTripsWhereUserIsPassenger(user, 0, 3).getElements();

            pawUserDetailsService.update(user);
            userService.changeRole(user.getUserId(), user.getRole());

            final ModelAndView mav = new ModelAndView("/users/user-profile");
            mav.addObject("user", user);
            mav.addObject("trips", trips);
            return mav;
        }

        List<Trip> trips = tripService.getTripsCreatedByUser(user, 0, 3).getElements();
        List<Car> cars = carService.findByUser(user);

        pawUserDetailsService.update(user);
        userService.changeRole(user.getUserId(), user.getRole());

        final ModelAndView mav = new ModelAndView("/users/driver-profile");
        mav.addObject("user", user);
        mav.addObject("trips", trips);
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
