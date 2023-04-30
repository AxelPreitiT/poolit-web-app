package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StreamUtils;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private static final String FROM = "poolit.noreply@gmail.com";

    private final SpringTemplateEngine templateEngine;

    private final JavaMailSender mailSender;

    private final MessageSource messageSource;
    @Autowired
    public EmailServiceImpl(SpringTemplateEngine templateEngine, JavaMailSender mailSender, MessageSource messageSource){
        this.templateEngine = templateEngine;
        templateEngine.setTemplateEngineMessageSource(messageSource);
        this.mailSender = mailSender;
        this.messageSource = messageSource;
    }

    private Map<String, Object> getVariablesMap(Context context) {
        Map<String, Object> variablesMap = new HashMap<>();
        for (String variableName : context.getVariableNames()) {
            variablesMap.put(variableName, context.getVariable(variableName));
        }
        return variablesMap;
    }

    private String loadTemplate(String name, Map<String, Object> model) throws IOException {
        final String templateFileName = name + ".html";
        final ClassPathResource resource = new ClassPathResource("templates/" + templateFileName);
        final byte[] templateBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        final String template = new String(templateBytes, StandardCharsets.UTF_8);
        final Context context = new Context(LocaleContextHolder.getLocale());
        context.setVariables(model);
        return templateEngine.process(template, context);
    }

    @Async
    public void sendEmail(final String to,final String subject,final String emailTemplate,final Context context) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(FROM));

        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

        message.setSubject(subject,"UTF-8");

        final String htmlContent = loadTemplate(emailTemplate, getVariablesMap(context));

        message.setContent(htmlContent,"text/html; charset=UTF-8");

        mailSender.send(message);
    }

    @Override
    public void sendMailNewPassenger(Trip trip, User passenger) throws MessagingException, IOException {
        String subject = messageSource.getMessage("emails.subject.newPassengerDriver",null,LocaleContextHolder.getLocale());
        // Variables para el html
        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);
        //enviamos el mail
        sendEmail(trip.getDriver().getEmail(),subject,"new-passenger-mail",ctx);
    }

    @Override
    public void sendMailTripDeletedToPassenger(Trip trip, User passenger) throws MessagingException,IOException{
        String subject = messageSource.getMessage("emails.subject.tripCancelledPassenger",null,LocaleContextHolder.getLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);

        sendEmail(passenger.getEmail(),subject,"delete-trip-passenger-email",ctx);
    }

    @Override
    public void sendMailTripDeletedToDriver(Trip trip) throws MessagingException,IOException{
        String subject = messageSource.getMessage("emails.subject.tripCancelledDriver",null,LocaleContextHolder.getLocale());
        // Variables para el html
        final Context ctx = new Context();
        ctx.setVariable("trip", trip);

        sendEmail(trip.getDriver().getEmail(),subject,"delete-trip-driver-mail",ctx);
    }

    @Override
    public void sendMailNewTrip(Trip trip) throws MessagingException, IOException {
        String subject = messageSource.getMessage("emails.subject.newTripCreated",null,LocaleContextHolder.getLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);

        sendEmail(trip.getDriver().getEmail(),subject,"create-trip-mail",ctx);
    }

    @Override
    public void sendMailTripConfirmation(Trip trip, User passenger) throws MessagingException, IOException {
        String subject = messageSource.getMessage("emails.subject.newTripPassenger",null,LocaleContextHolder.getLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);

        sendEmail(passenger.getEmail(),subject,"trip-confirmation-mail",ctx);
    }

}



