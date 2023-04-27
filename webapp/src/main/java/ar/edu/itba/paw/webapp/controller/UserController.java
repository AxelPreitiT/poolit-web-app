package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUser;
import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import ar.edu.itba.paw.webapp.form.CreateUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private final CityService cityService;

    private final PawUserDetailsService pawUserDetailsService;

    private final UserService userService;
    private final static long DEFAULT_PROVINCE_ID = 1;
    private final static String BASE_RELATED_PATH = "/users/";
    private final static String CREATE_USER_PATH = BASE_RELATED_PATH + "create";
    private final static String LOGIN_USER_PATH = BASE_RELATED_PATH + "login";

    @Autowired
    public UserController(final CityService cityService, final  UserService userService, final PawUserDetailsService pawUserDetailsService){
        this.cityService = cityService;
        this.userService = userService;
        this.pawUserDetailsService = pawUserDetailsService;
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
        Optional<City> originCity = cityService.findCityById(form.getBornCityId());
        userService.createUserIfNotExists(form.getUsername(), form.getSurname(), form.getEmail(), form.getPhone(),
                form.getPassword(), form.getBirthdate(), originCity.get(), null);
        return new ModelAndView("redirect:/" );
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

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profileView(){

        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userService.findByEmail(authUser.getUsername()).get();
        final ModelAndView mav = new ModelAndView("/users/profile");
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ModelAndView profilePost(){
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userService.findByEmail(authUser.getUsername()).get();

        pawUserDetailsService.update(user);
        userService.changeRole(user.getUserId(), user.getRole());

        final ModelAndView mav = new ModelAndView("/users/profile");
        mav.addObject("user", user);
        return mav;
    }
}
