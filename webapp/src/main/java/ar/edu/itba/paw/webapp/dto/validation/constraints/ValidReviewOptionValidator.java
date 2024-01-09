package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.ValidReviewOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Method;
import java.util.List;

public class ValidReviewOptionValidator implements ConstraintValidator<ValidReviewOption,Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidReviewOptionValidator.class);

    private static final String RATING_FIELD = "rating";

    private static final String LIST_METHOD = "getRatings";
    private String option;

    @Override
    public void initialize(ValidReviewOption constraintAnnotation) {
        this.option = constraintAnnotation.reviewOption();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Integer reviewRating = (Integer) FieldUtils.getFieldValue(object,RATING_FIELD);
            Object reviewOption = FieldUtils.getFieldValue(object,option);
            Method listMethod = reviewOption.getClass().getMethod(LIST_METHOD);
            @SuppressWarnings("unchecked")
            List<Integer> acceptedRatings = (List<Integer>) listMethod.invoke(reviewOption);
            return acceptedRatings.contains(reviewRating);
        }catch (Exception e){
            LOGGER.error("Error while getting field values in ValidReviewOptionValidator",e);
        }
        return false;
    }
}
