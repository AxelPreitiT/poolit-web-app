package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CityService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.City;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class HelloWorldController {
    private final UserService us;
    private final CityService cs;

    @Autowired
    public HelloWorldController(UserService us, CityService cs){
        this.us = us;
        this.cs = cs;
    }

//    @RequestMapping(value = "/test", method = RequestMethod.GET)
//    public ModelAndView test() {
//        final ModelAndView mav = new ModelAndView("static/not-found-404");
//        mav.addObject("cities", cs.getCitiesByProvinceId(1));
//        Trip trip = new Trip(1, new City(1, "Agronomia", 1), "San martin 500", new City(2, "Balvanera", 1), "Corrientes 500", LocalDateTime.now(), LocalDateTime.now().plusDays(2), 3, new User(1, "Pablo", "Perez", "pperez@gmail.com", "123456789", "hola", LocalDateTime.now(), new City(1, "Agronomia", 1), "DRIVER"), new Car(1, "ABC123", "Ford ka azul", new User(1, "Pablo", "Perez", "pperez@gmail.com", "123456789", "hola", LocalDateTime.now(), new City(1, "Agronomia", 1), "DRIVER")), 2, 2500);
//        ArrayList<Trip> trips = new ArrayList<>();
//        trips.add(trip);
//        mav.addObject("trips", trips);
//        return mav;
//    }
//    @RequestMapping("/")
//    public ModelAndView helloWorld(){
//        final ModelAndView mav = new ModelAndView("helloworld/index");
//        mav.addObject("user",us.createUser("paw@itba.edu.ar","myPassword"));
//        return mav;
//    }
//    @RequestMapping("/{id:\\d+}")
//    public ModelAndView profile(@PathVariable("id") final long userId){
//        final ModelAndView mav = new ModelAndView("/helloworld/profile");
//        mav.addObject("userId",userId);
//        return mav;
//    }
//    @RequestMapping(value="/register", method = RequestMethod.GET)
//    public ModelAndView registerForm(){
//        return new ModelAndView("/helloworld/register");
//    }
//
//    @RequestMapping(value="/register", method = RequestMethod.POST)
//    public ModelAndView register(@RequestParam(value = "email", required = true) final String email,
//                                 @RequestParam(value = "password", required = true) final String password){
//        final User user = us.createUser(email,password);
//
//        //Volvemos a index pero con esa informacion
//        final ModelAndView mav = new ModelAndView("helloworld/index");
//        mav.addObject("user",user);
//        return mav;
//    }
}
