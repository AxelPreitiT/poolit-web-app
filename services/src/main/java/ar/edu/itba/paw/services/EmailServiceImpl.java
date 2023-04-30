package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.trips.Trip;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StreamUtils;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
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

    private Map<String, Object> getVariablesMap(Context context) {
        Map<String, Object> variablesMap = new HashMap<>();
        for (String variableName : context.getVariableNames()) {
            variablesMap.put(variableName, context.getVariable(variableName));
        }
        return variablesMap;
    }

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMailNewPassenger(Trip trip, User passenger) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("poolit.noreply@gmail.com"));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(trip.getDriver().getEmail()));
        message.setSubject("Nuevo pasajero en tu viaje!");
        // Variables para el html
        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);

        // Cargo el template
        final String htmlContent = loadTemplate("new-passenger-mail", getVariablesMap(ctx));

        message.setContent(htmlContent,"text/html; charset=UTF-8");

        mailSender.send(message);
    }
    //TODO: catch exceptions here and send others
    @Override
    public void sendMailTripDeletedToPassenger(Trip trip, User passenger) throws MessagingException,IOException{
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("poolit.noreply@gmail.com"));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(passenger.getEmail()));
        message.setSubject("Tu viaje fue cancelado");
        // Variables para el html
        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);

        // Cargo el template
        final String htmlContent = loadTemplate("delete-trip-passenger-mail", getVariablesMap(ctx));

        message.setContent(htmlContent,"text/html; charset=UTF-8");

        mailSender.send(message);
    }
    @Override
    public void sendMailTripDeletedToDriver(Trip trip) throws MessagingException,IOException{
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("poolit.noreply@gmail.com"));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(trip.getDriver().getEmail()));
        message.setSubject("Nuevo pasajero en tu viaje!");
        // Variables para el html
        final Context ctx = new Context();
        ctx.setVariable("trip", trip);

        // Cargo el template
        final String htmlContent = loadTemplate("delete-trip-driver-mail", getVariablesMap(ctx));

        message.setContent(htmlContent,"text/html; charset=UTF-8");

        mailSender.send(message);
    }



    @Override
    public void sendMailNewTrip(Trip trip) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("poolit.noreply@gmail.com"));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(trip.getDriver().getEmail()));
        message.setSubject("Nuevo viaje en Poolit creado!");

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);

        final String htmlContent = loadTemplate("create-trip-mail", getVariablesMap(ctx));

        message.setContent(htmlContent, "text/html; charset=UTF-8");

        mailSender.send(message);
    }

    @Override
    public void sendMailTripConfirmation(Trip trip, User passenger) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        message.setFrom(new InternetAddress("poolit.noreply@gmail.com"));

        message.addRecipient(Message.RecipientType.TO,new InternetAddress(passenger.getEmail()));
        message.setSubject("Nuevo viaje confirmado!");

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);

        final String htmlContent = loadTemplate("trip-confirmation-mail", getVariablesMap(ctx));

        message.setContent(htmlContent, "text/html; charset=UTF-8");

        mailSender.send(message);
    }

    private String loadTemplate(String name, Map<String, Object> model) throws IOException {
        final String templateFileName = name + ".html";
        final ClassPathResource resource = new ClassPathResource("templates/" + templateFileName);
        final byte[] templateBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        final String template = new String(templateBytes, StandardCharsets.UTF_8);
        final TemplateEngine templateEngine = new SpringTemplateEngine();
        final Context context = new Context();
        context.setVariables(model);
        return templateEngine.process(template, context);
    }

}



