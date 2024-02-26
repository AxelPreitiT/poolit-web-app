package ar.edu.itba.paw.webapp.config.converters;


public class ConversionException extends RuntimeException {

    final String messageCode;

    public ConversionException(final String messageCode){
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }
}
