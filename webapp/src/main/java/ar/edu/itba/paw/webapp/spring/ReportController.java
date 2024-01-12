package ar.edu.itba.paw.webapp.spring;

import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.interfaces.services.ReportService;
import ar.edu.itba.paw.webapp.form.ReportForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ReportController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    private static final String TRIP_PATH_REDIRECT = "redirect:/trips/";
    private static final String REPORTED_QUERY_PARAM = "?reported=true";
    private static final String REPORT_ERROR_QUERY_PARAM = "?report_error=true";
    private static final String BASE_PATH = "/reports";
    private static final String BASE_TRIP_PATH = BASE_PATH + "/trips/{tripId:\\d+}";
    private static final String TRIP_PASSENGERS_REPORT_PATH = BASE_TRIP_PATH + "/passengers/{passengerId:\\d+}";
    private static final String TRIP_DRIVERS_REPORT_PATH = BASE_TRIP_PATH + "/drivers/{driverId:\\d+}";

    private final ReportService reportService;

    @Autowired
    public ReportController(final ReportService reportService) {
        this.reportService = reportService;
    }

    private String getTripRedirectPath(final long tripId) {
        return TRIP_PATH_REDIRECT + tripId + REPORTED_QUERY_PARAM;
    }

    private String getTripErrorRedirectPath(final long tripId) {
        return TRIP_PATH_REDIRECT + tripId + REPORT_ERROR_QUERY_PARAM;
    }

    //reporta a un pasajero
    @RequestMapping(value = TRIP_PASSENGERS_REPORT_PATH, method = RequestMethod.POST)
    public ModelAndView reportPassenger(
            @PathVariable("tripId") final long tripId,
            @PathVariable("passengerId") final long passengerId,
            @Valid @ModelAttribute("reportForm") final ReportForm reportForm,
            final BindingResult errors
            ) throws UserNotFoundException, TripNotFoundException {
        if(errors.hasErrors()){
            return new ModelAndView(getTripErrorRedirectPath(tripId));
        }
        LOGGER.debug("POST request to /reports/trips/{}/passengers/{}", tripId, passengerId);
        reportService.createReport(passengerId, tripId, reportForm.getComment(), reportForm.getRelation(), reportForm.getReason());
        return new ModelAndView(getTripRedirectPath(tripId));
    }

    //reporta a un conductor

    @RequestMapping(value = TRIP_DRIVERS_REPORT_PATH, method = RequestMethod.POST)
    public ModelAndView reportDriver(
            @PathVariable("tripId") final long tripId,
            @PathVariable("driverId") final long driverId,
            @Valid @ModelAttribute("reportForm") final ReportForm reportForm,
            final BindingResult errors
    ) throws UserNotFoundException, TripNotFoundException {
        if(errors.hasErrors()){
            return new ModelAndView(getTripErrorRedirectPath(tripId));
        }
        LOGGER.debug("POST request to /reports/trips/{}/drivers/{}", tripId, driverId);
        reportService.createReport(driverId, tripId, reportForm.getComment(), reportForm.getRelation(), reportForm.getReason());
        return new ModelAndView(getTripRedirectPath(tripId));
    }


}
