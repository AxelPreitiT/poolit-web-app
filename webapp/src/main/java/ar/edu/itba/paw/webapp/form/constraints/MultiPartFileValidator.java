package ar.edu.itba.paw.webapp.form.constraints;

import ar.edu.itba.paw.webapp.form.CreateCarForm;
import ar.edu.itba.paw.webapp.form.annotations.MPFile;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultiPartFileValidator implements ConstraintValidator<MPFile, MultipartFile> {
    private static final long SIZE = 10485760; // Tamaño máximo de archivo en bytes, 1024*1024*10

    @Override
    public void initialize(MPFile mpfile) {
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext){
        return !multipartFile.isEmpty() && multipartFile.getSize()<=SIZE;
    }
}

