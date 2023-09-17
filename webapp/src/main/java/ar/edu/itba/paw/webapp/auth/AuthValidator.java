package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.UserRole;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotLoggedInException;
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

    public boolean checkIfWantedIsSelf(HttpServletRequest request, long id){
        final Optional<User> user = userService.getCurrentUser();
        if(!user.isPresent()){
            return false;
        }
        return user.get().getUserId() == id;
    }
    public boolean checkIfUserIsTripCreator(HttpServletRequest servletRequest) throws TripNotFoundException, UserNotLoggedInException {
        int tripId = Integer.parseInt(servletRequest.getRequestURI().replaceFirst(".*/trips/","").replaceFirst("/.*",""));
        final User user = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        if(!user.getIsDriver()){
            LOGGER.debug("User with id {} tried to delete a trip without being a driver",user.getUserId());
            return false;
        }
        //User is a driver
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        boolean ans = trip.getDriver().equals(user);
        if(!ans){
            LOGGER.debug("User {} tried to delete a trip without being it's creator",user.getUserId());
        }
        return ans;
    }
}
