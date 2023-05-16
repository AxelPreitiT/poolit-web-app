package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TripNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 121312390745766939L;
    private static final Logger LOGGER = LoggerFactory.getLogger(TripNotFoundException.class);

    public TripNotFoundException(final long tripId){
        super();
        LOGGER.error("Trip not found with id {}", tripId, this);
    }
}
