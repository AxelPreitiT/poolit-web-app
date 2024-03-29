package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.services.CarService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.Car;
import ar.edu.itba.paw.models.Passenger;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class AuthValidator {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthValidator.class);

    final UserService userService;
    final TripService tripService;
    final CarService carService;
    @Autowired
    public AuthValidator(final UserService userService, final TripService tripService, final CarService carService){
        this.userService = userService;
        this.tripService = tripService;
        this.carService = carService;
    }

    //check if wanted user is the user doing the request
    public boolean checkIfWantedIsSelf(Long id){
        if(id==null){
            return true;
        }
        final Optional<User> user = userService.getCurrentUser();
        if(!user.isPresent()){
            return false;
        }
        return user.get().getUserId() == id;
    }

    //check if user is the trip creator
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

    public boolean checkIfUserCanSearchPassengers(final long tripId, final LocalDateTime startDateTime, final LocalDateTime endDateTime, Passenger.PassengerState passengerState) throws TripNotFoundException {
        final Optional<User> optionalUser = userService.getCurrentUser();

        if (!optionalUser.isPresent()) {
            return false;
        }

        if(startDateTime==null || endDateTime==null || passengerState==null){
            return tripService.userIsDriver(tripId,optionalUser.get());//only driver can get all the passengers
        }

        //TOOD: check null values!
        return tripService.checkIfUserCanGetPassengers(tripId,optionalUser.get(),startDateTime,endDateTime,passengerState);
    }

    public boolean checkIfUserIsPassengerOf(final long userId){
        final Optional<User> user = userService.getCurrentUser();
        if(!user.isPresent()){
            return false;
        }
        final Optional<User> driver = userService.findById(userId);
        if(!driver.isPresent()){
            return true; //NOT FOUND
        }
        return tripService.userIsAcceptedPassengerOfDriver(user.get(),driver.get());
    }

    public boolean checkIfCarIsOwn(long carId){
        final Optional<Car> car = carService.findById(carId);
        if(!car.isPresent()){
            return true;//BAD REQUEST will be the status
        }
        final Optional<User> user = userService.getCurrentUser();
        if(!user.isPresent()){
            return false;
        }
        return user.get().getUserId() == car.get().getUser().getUserId();
    }

}
