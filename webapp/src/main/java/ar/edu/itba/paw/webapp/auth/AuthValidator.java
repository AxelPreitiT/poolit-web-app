package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.exceptions.TripNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotLoggedInException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class AuthValidator {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthValidator.class);

    final UserService userService;
    final TripService tripService;
    @Autowired
    public AuthValidator(final UserService userService, final TripService tripService){
        this.userService = userService;
        this.tripService = tripService;
    }
    public boolean checkIfUserIsTripCreator(HttpServletRequest servletRequest){
        int tripId = Integer.parseInt(servletRequest.getRequestURI().replaceFirst(".*/trips/","").replaceFirst("/.*",""));
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        if(!user.getRole().equals("DRIVER")){
            LOGGER.debug("User with id {} tried to delete a trip without being a driver",user.getUserId());
            return false;
        }
        //User is a driver
        final Trip trip = tripService.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        boolean ans = trip.getDriver().equals(user);
        if(!ans){
            LOGGER.debug("User {} tried to delete a trip without being it's creator",user.getUserId());
        }
        return ans;
    }
}
