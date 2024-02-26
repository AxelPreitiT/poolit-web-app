package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.ImageType;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


//https://javadoc.io/doc/org.glassfish.jersey.media/jersey-media-multipart/latest/org/glassfish/jersey/media/multipart/package-summary.html
public class ImageTypeValidator implements ConstraintValidator<ImageType, FormDataBodyPart> {

    private static final String ACCEPTED_MEDIA_TYPES = "image/";
    @Override
    public boolean isValid(FormDataBodyPart value, ConstraintValidatorContext context) {
        return value != null && value.getMediaType().toString().contains(ACCEPTED_MEDIA_TYPES);
    }
}
