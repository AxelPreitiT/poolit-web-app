package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CityNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 221315739072439789L;
    private static final Logger LOGGER = LoggerFactory.getLogger(CityNotFoundException.class);

    public CityNotFoundException(final long cityId){
        super();
        LOGGER.error("City not found with id {}", cityId, this);
    }
}
