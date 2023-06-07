package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.PassengerReviewService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.exceptions.PassengerNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.TripNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import ar.edu.itba.paw.webapp.form.PassengerReviewForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class ReviewController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    private static final String TRIP_PATH_REDIRECT = "redirect:/trips/";
    private static final String REVIEWED_QUERY_PARAM = "?reviewed=true";
    private static final String BASE_PATH = "/reviews";
    private static final String BASE_TRIP_PATH = BASE_PATH + "/trips/{tripId:\\d+}";
    private static final String PASSENGERS_REVIEW_PATH = BASE_TRIP_PATH + "/passengers/{passengerId:\\d+}";

    private final PassengerReviewService passengerReviewService;
    private final TripService tripService;
    private final UserService userService;

    @Autowired
    public ReviewController(final PassengerReviewService passengerReviewService, final TripService tripService, final UserService userService) {
        this.passengerReviewService = passengerReviewService;
        this.tripService = tripService;
        this.userService = userService;
    }

    private String getTripRedirectPath(final long tripId) {
        return TRIP_PATH_REDIRECT + tripId + REVIEWED_QUERY_PARAM;
    }

    @RequestMapping(value = PASSENGERS_REVIEW_PATH, method = RequestMethod.POST)
    public ModelAndView reviewPassenger(
            @PathVariable("tripId") final long tripId,
            @PathVariable("passengerId") final long passengerId,
            @Valid @ModelAttribute("passengerReviewForm") final PassengerReviewForm passengerReviewForm
    ) {
        LOGGER.debug("POST request to /reviews/trips/{}/passengers/{}", tripId, passengerId);
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        final User reviewer = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        final User reviewed = userService.findById(passengerId).orElseThrow(() -> new UserNotFoundException(passengerId));
        final Passenger passenger = tripService.getPassenger(trip, reviewed).orElseThrow(() -> new PassengerNotFoundException(passengerId, tripId));
        passengerReviewService.createPassengerReview(trip, reviewer, passenger, passengerReviewForm.getRating(), passengerReviewForm.getComment(), passengerReviewForm.getOption());
        return new ModelAndView(getTripRedirectPath(tripId));
    }

}
