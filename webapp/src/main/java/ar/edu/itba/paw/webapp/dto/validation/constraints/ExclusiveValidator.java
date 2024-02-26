package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.Exclusive;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ExclusiveValidator implements ConstraintValidator<Exclusive, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExclusiveValidator.class);
    private List<String> group1;
    private List<String> group2;

    @Override
    public void initialize(Exclusive constraintAnnotation) {
        this.group1 = Arrays.asList(constraintAnnotation.group1());
        this.group2 = Arrays.asList(constraintAnnotation.group2());
    }

    private boolean isEmpty(Object value){
        if(! (value instanceof Collection)){
            return value==null;
        }
        return ((Collection<?>)value).isEmpty(); //handle case of default empty collection for collection parameters
    }
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        List<String> usedFields = new ArrayList<>();
        List<String> badFields = new ArrayList<>();
        try {
            for(String fieldName : group1){
                Object val = FieldUtils.getFieldValue(object,fieldName);
                if(!isEmpty(val)){
                    usedFields.add(fieldName);
                }
            }
            for(String fieldName: group2){
                Object val = FieldUtils.getFieldValue(object,fieldName);
                if(!isEmpty(val)){
                    badFields.add(fieldName);
                }
            }
        }catch (Exception e){
            LOGGER.error("Error while getting fields values in NotNullTogetherValidator",e);
            return false;
        }
        context.unwrap(HibernateConstraintValidatorContext.class).addExpressionVariable("usedFields",String.join(", ",usedFields));
        context.unwrap(HibernateConstraintValidatorContext.class).addExpressionVariable("extraFields",String.join(", ",badFields));
        return usedFields.size() == 0 || badFields.size() == 0;
    }
}
