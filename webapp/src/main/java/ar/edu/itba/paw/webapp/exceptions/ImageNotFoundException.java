package ar.edu.itba.paw.webapp.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 6211531235658647789L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageNotFoundException.class);

    public ImageNotFoundException(final long imageId){
        super();
        LOGGER.error("Image not found with id {}", imageId, this);
    }
}
