package ar.edu.itba.paw.webapp.dto.output;

import javax.validation.ConstraintViolation;

public class ValidationErrorDto {

    private String message;
    private String field;

    public static ValidationErrorDto fromValidationException(final ConstraintViolation<?> violation){
        final ValidationErrorDto dto = new ValidationErrorDto();
        dto.message = violation.getMessage();
//        dto.message = violation.getMessageTemplate();
        final String path = violation.getPropertyPath().toString();
        dto.field= path.substring(path.lastIndexOf(".")+1);
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
