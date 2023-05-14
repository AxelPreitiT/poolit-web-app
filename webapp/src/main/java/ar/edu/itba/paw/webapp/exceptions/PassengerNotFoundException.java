package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PassengerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3631763748631763748L;
    private static final Logger LOGGER = LoggerFactory.getLogger(PassengerNotFoundException.class);

    public PassengerNotFoundException(final long passengerId, final long tripId) {
        super();
        LOGGER.error("Passenger with id {} not found in trip with id {}", passengerId, tripId, this);
    }

}
