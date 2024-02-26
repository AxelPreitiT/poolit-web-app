package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.LessOrEqualThan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LessOrEqualThanValidator implements ConstraintValidator<LessOrEqualThan, Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LessOrEqualThanValidator.class);
    private String field1;
    private String field2;

    @Override
    public void initialize(LessOrEqualThan constraintAnnotation) {
        this.field1 = constraintAnnotation.value1();
        this.field2 = constraintAnnotation.value2();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Comparable start = (Comparable) FieldUtils.getFieldValue(object,field1);
            Object end = FieldUtils.getFieldValue(object,field2);
            return start == null || end == null || start.compareTo(end)<=0;
        }catch (Exception e){
            LOGGER.error("Error while getting field values in LessOrEqualThanValidator",e);
        }
        return true;
    }
}
