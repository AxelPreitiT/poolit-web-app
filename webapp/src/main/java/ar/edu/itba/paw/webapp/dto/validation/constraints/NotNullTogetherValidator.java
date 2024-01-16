package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.NotNullTogether;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.util.FieldUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class NotNullTogetherValidator implements ConstraintValidator<NotNullTogether, Object> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotNullTogetherValidator.class);

    private String[] fields;

    @Override
    public void initialize(NotNullTogether constraintAnnotation) {
        this.fields = constraintAnnotation.fields();;
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        List<String> present = new ArrayList<>();
        List<String> missing = new ArrayList<>();
        try {
            for(String field : fields){
                if(FieldUtils.getFieldValue(object,field)==null){
                    missing.add(field);
                }else {
                    present.add(field);
                }
            }
        }catch (Exception e){
            LOGGER.error("Error while getting fields values in NotNullTogetherValidator",e);
        }
        context.unwrap(HibernateConstraintValidatorContext.class).addExpressionVariable("presentFields",String.join(", ",present));
        context.unwrap(HibernateConstraintValidatorContext.class).addExpressionVariable("missingFields",String.join(", ",missing));
        //o todos son null,             o ninguno es null
        //present.size() == 0 || (present.size()!=0 && missing.size()==0)
        return (present.size()==0 || missing.size()==0);
    }
}
