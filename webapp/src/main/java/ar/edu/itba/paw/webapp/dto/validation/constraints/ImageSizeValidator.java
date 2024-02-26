package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.ImageSize;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageSizeValidator implements ConstraintValidator<ImageSize, byte[]> {

    private static final long MAX_SIZE = 1024*1024*10; // Tamaño máximo de archivo en bytes, 10 MB
    @Override
    public boolean isValid(byte[] value, ConstraintValidatorContext context) {
        return value.length<=MAX_SIZE;
    }
}
