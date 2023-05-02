package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.auth.AuthUser;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.form.CreateCarForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class CarController extends LoggedUserController {
    private final UserService userService;
    private final ImageService imageService;
    private final CarService carService;

    @Autowired
    public CarController(UserService userService, ImageService imageService,CarService carService) {
            super(userService);
            this.carService = carService;
            this.userService = userService;
            this.imageService = imageService;
    }

    @RequestMapping(value = "/cars/create", method = RequestMethod.GET)
    public ModelAndView createCarForm(@ModelAttribute("createCarForm") final CreateCarForm form) {
        final ModelAndView mav = new ModelAndView("create-auto/main");
        return mav;
    }

    @RequestMapping(value = "/cars/create", method = RequestMethod.POST)
    public ModelAndView postCar(@Valid @ModelAttribute("createCarForm") final CreateCarForm form,
                                final BindingResult errors) throws IOException {
        if(errors.hasErrors()){
            return createCarForm(form);
        }
        final AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final User user = userService.findByEmail(authUser.getUsername()).orElseThrow(UserNotFoundException::new);
        byte[] data = form.getImageFile().getBytes();
        Image image=imageService.createImage(data);
        carService.createCar(form.getPlate(),form.getCarInfo(),user , image.getImageId() );
        //Hacer un redirect si se hace desde el perfil la creacion de autos
        ModelAndView modelAndView = new ModelAndView("create-auto/success");
        return modelAndView;

    }
}
