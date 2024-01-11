package ar.edu.itba.paw.webapp.dto.validation.constraints;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class MinFieldValidator implements ConstraintValidator<Annotation,Number> {

    private long minValue;
    @Override
    public void initialize(Annotation constraintAnnotation) {
        //This is to use the same validator for @Page, @PageSize, @MinField
        Class<?> annotationClass = constraintAnnotation.getClass();
        try {
            Method minValueMethod = annotationClass.getMethod("value");
            this.minValue = (long) minValueMethod.invoke(constraintAnnotation);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            this.minValue = 0;
        }
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        //this is a simplified version of import org.hibernate.validator.internal.constraintvalidators.bv.MinValidatorForNumber for our use case
        if (value instanceof BigDecimal){
            return ((BigDecimal) value).compareTo(BigDecimal.valueOf(minValue))>=0;
        }
        return value.longValue() >= minValue;
    }
}
