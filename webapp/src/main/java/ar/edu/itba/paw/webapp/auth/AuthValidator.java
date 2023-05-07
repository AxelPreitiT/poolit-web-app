package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.webapp.exceptions.TripNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Component
public class AuthValidator {
    final UserService userService;
    final TripService tripService;
    @Autowired
    public AuthValidator(final UserService userService, final TripService tripService){
        this.userService = userService;
        this.tripService = tripService;
    }
    public boolean checkIfUserIsTripCreator(HttpServletRequest servletRequest){
        int tripId = Integer.parseInt(servletRequest.getRequestURI().replaceFirst(".*/trips/","").replaceFirst("/.*",""));
        //TODO: preguntar si esta bien lanzar estas excepciones aca
        final User user = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        if(!user.getRole().equals("DRIVER")){
            return false;
        }
        //User is a driver
        Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        return trip.getDriver().equals(user);
    }
}
