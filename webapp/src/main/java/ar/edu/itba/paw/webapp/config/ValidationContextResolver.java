package ar.edu.itba.paw.webapp.config;

import org.glassfish.jersey.server.validation.ValidationConfig;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.util.Locale;

//https://www.baeldung.com/spring-validation-message-interpolation
//https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-resource-bundle-locator
//https://docs.jboss.org/hibernate/validator/5.1/reference/en-US/html/chapter-message-interpolation.html#section-custom-message-interpolation
//https://stackoverflow.com/questions/30734766/bean-validation-message-interpolation-with-array-constraint-parameter-used-as-va

@Provider
public class ValidationContextResolver implements ContextResolver<ValidationConfig> {

    @Override
    public ValidationConfig getContext(Class<?> type) {
        final ValidationConfig config = new ValidationConfig();
        config.messageInterpolator(new CustomMessageInterpolator());
        return config;
    }

    private static class CustomMessageInterpolator implements MessageInterpolator{
        private final MessageInterpolator messageInterpolator;

        public CustomMessageInterpolator(){
            //Set default locale for messages (and avoid default JVM locale)
            Locale.setDefault(Locale.ENGLISH);
            messageInterpolator = Validation.byDefaultProvider()
                    .configure()
                    //Use resource bundle in i18n directory
                    .messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("i18n/ValidationMessages")))
                    .buildValidatorFactory()
                    .getMessageInterpolator();
        }


        @Override
        public String interpolate(String messageTemplate, Context context) {
            return messageInterpolator.interpolate(messageTemplate,context, LocaleContextHolder.getLocale());
        }

        @Override
        public String interpolate(String messageTemplate, Context context, Locale locale) {
            return messageInterpolator.interpolate(messageTemplate,context, LocaleContextHolder.getLocale());
        }
    }
}
