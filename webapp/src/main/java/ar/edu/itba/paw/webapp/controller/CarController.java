package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.ImageService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.models.Image;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.webapp.form.CreateCarForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class CarController extends LoggedUserController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarController.class);

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
        LOGGER.debug("GET Request to /cars/create");
        //Por si se necesitan las marcas para el front
        //List<CarBrand> brands = Arrays.asList(CarBrand.values());
        return new ModelAndView("create-car/main");
    }

    @RequestMapping(value = "/cars/create", method = RequestMethod.POST)
    public ModelAndView postCar(@Valid @ModelAttribute("createCarForm") final CreateCarForm form,
                                final BindingResult errors) throws IOException {
        LOGGER.debug("POST Request to /cars/create");
        if(errors.hasErrors()){
            LOGGER.warn("Errors found in CreateCarForm: {}", errors.getAllErrors());
            return createCarForm(form);
        }
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        try {
            byte[] data = form.getImageFile().getBytes();
            final Image image=imageService.createImage(data);
            //TODO: Manejar la marca y los features con forms algo asi
            //carService.createCar(form.getPlate(),form.getCarInfo(),user , image.getImageId(), form.getSeats(), form.getBrand(), form.getListFeatures());
            ArrayList<FeatureCar> listFeatures = new ArrayList<>();
            listFeatures.add(FeatureCar.AIR);
            listFeatures.add(FeatureCar.PET_FRIENDLY);
            carService.createCar(form.getPlate(),form.getCarInfo(),user , image.getImageId(), form.getSeats(), CarBrand.HONDA, listFeatures);
        } catch (IOException e) {
            LOGGER.error("Error while reading image file", e);
            throw e;
        }
        return new ModelAndView("redirect:/users/profile?carAdded=true");
    }
}
