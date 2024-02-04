package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.ValidOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.List;

public class ValidOptionValidator implements ConstraintValidator<ValidOption,Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidOptionValidator.class);

    private static final String RATING_FIELD = "rating";

    private static final String LIST_METHOD = "getRatings";
    //the option chosen
    private String chosenValue;

    //the method use to get the list of accepted values
    private String listMethod;
    //the field in the dto that contains the list of accepted values
    private String listableField;

    @Override
    public void initialize(ValidOption constraintAnnotation) {
        this.chosenValue = constraintAnnotation.chosenValue();
        this.listMethod = constraintAnnotation.listMethod();
        this.listableField = constraintAnnotation.listableField();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Object chosenOption = FieldUtils.getFieldValue(object,this.chosenValue);
            Object field = FieldUtils.getFieldValue(object,this.listableField);
            Method listMethod = field.getClass().getMethod(this.listMethod);
            @SuppressWarnings("unchecked")
            List<Object> acceptedRatings = (List<Object>) listMethod.invoke(field);
            return acceptedRatings.contains(chosenOption);
        }catch (Exception e){
            LOGGER.error("Error while getting field values in ValidReviewOptionValidator",e);
        }
        return false;
    }
}
