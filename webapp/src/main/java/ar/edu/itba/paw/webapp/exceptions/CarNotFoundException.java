package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 521311231237345289L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CarNotFoundException.class);

    public CarNotFoundException(final long carId){
        super();
        LOGGER.error("Car not found with id {}", carId, this);
    }

}
