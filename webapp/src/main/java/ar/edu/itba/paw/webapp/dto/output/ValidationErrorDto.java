package ar.edu.itba.paw.webapp.dto.output;

import javax.validation.ConstraintViolation;

public class ValidationErrorDto {

    private String message;
    private String field;

    public static ValidationErrorDto fromValidationException(final ConstraintViolation<?> violation){
        final ValidationErrorDto dto = new ValidationErrorDto();
        dto.message = violation.getMessage();
        final String path = violation.getPropertyPath().toString();
        if(path.lastIndexOf('.')!=path.indexOf('.')){
            //Not a class level validation (path is "class.arg0.field"), its a field validation
            dto.field= path.substring(path.lastIndexOf(".")+1);
        }
        return dto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
