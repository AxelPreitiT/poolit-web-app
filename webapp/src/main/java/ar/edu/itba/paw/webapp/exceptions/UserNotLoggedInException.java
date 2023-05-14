package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserNotLoggedInException extends RuntimeException {
    private static final long serialVersionUID = 121312390745766939L;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserNotLoggedInException.class);

    public UserNotLoggedInException() {
        super();
        LOGGER.error("User not logged in", this);
    }
}
