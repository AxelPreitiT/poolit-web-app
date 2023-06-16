package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.models.CarBrand;
import ar.edu.itba.paw.models.FeatureCar;
import ar.edu.itba.paw.webapp.form.CreateCarForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CarController.class);


    public AdminController(){

    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView createCarForm() {
        LOGGER.debug("GET admin view");
        final ModelAndView mav = new ModelAndView("admin/main");
        return mav;
    }
}
