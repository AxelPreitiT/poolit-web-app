package ar.edu.itba.paw.webapp.dto.validation.constraints;

import ar.edu.itba.paw.webapp.dto.validation.annotations.NotAllNull;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotAllNullValidator implements ConstraintValidator<NotAllNull,Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotAllNullValidator.class);

    private boolean isEmpty(Object value){
        if(! (value instanceof Collection)){
            return value==null;
        }
        return ((Collection<?>)value).isEmpty(); //handle case of default empty collection for collection parameters
    }
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        List<String> fieldNames = new ArrayList<>();
        int usedFields = 0;
         try {
             for(Field field : object.getClass().getDeclaredFields()){
                 fieldNames.add(field.getName());
                 field.setAccessible(true);
                 if(!isEmpty(field.get(object))){
                     usedFields++;
                 }
             }
         }catch (Exception e){
             LOGGER.error("Error while getting fields values in NotAllNullValidator",e);
             return false;
         }
        context.unwrap(HibernateConstraintValidatorContext.class).addExpressionVariable("fieldNames",String.join(", ",fieldNames));
         return usedFields>0;
    }
}
