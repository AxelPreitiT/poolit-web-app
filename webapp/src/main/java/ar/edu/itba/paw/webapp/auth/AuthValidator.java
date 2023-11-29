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

    public boolean checkIfWantedIsSelf(long id){
        final Optional<User> user = userService.getCurrentUser();
        if(!user.isPresent()){
            return false;
        }
        return user.get().getUserId() == id;
    }

    public boolean checkIfUserIsTripCreator(long tripId) throws TripNotFoundException {
        final Optional<User> optionalUser = userService.getCurrentUser();
        if (!optionalUser.isPresent()) {
            return false;
        }
        final User user = optionalUser.get();
        if (!user.getIsDriver()) {
            return false;
        }
        final Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
//        Compare id because of lazy loading of driver
        final boolean ans = user.getUserId() == trip.getDriver().getUserId();
        if (!ans) {
            LOGGER.debug("User {} tried to delete a trip {} without being it's creator", user.getUserId(), trip.getTripId());
        }
        return ans;
    }
}
