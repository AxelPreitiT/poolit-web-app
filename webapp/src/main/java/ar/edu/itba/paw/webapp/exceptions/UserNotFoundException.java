package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 3213155749078647789L;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserNotFoundException.class);

    public UserNotFoundException(final long userId){
        super();
        LOGGER.error("User not found with id {}", userId, this);
    }

    public UserNotFoundException(final String email){
        super();
        LOGGER.error("User not found with email {}", email, this);
    }
}
