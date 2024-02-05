package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
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
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final String baseUrl;
    private static final String FROM = "poolit.noreply@gmail.com";

    private final SpringTemplateEngine templateEngine;

    private final JavaMailSender mailSender;

    private final MessageSource messageSource;

    @Autowired
    public EmailServiceImpl(SpringTemplateEngine templateEngine, JavaMailSender mailSender, MessageSource messageSource, @Qualifier("baseUrl") String baseUrl) {
        this.templateEngine = templateEngine;
        templateEngine.setTemplateEngineMessageSource(messageSource);
        this.mailSender = mailSender;
        this.messageSource = messageSource;
        this.baseUrl = baseUrl;
    }

    private Map<String, Object> getVariablesMap(Context context) {
        LOGGER.debug("Getting variables from context {}", context);
        Map<String, Object> variablesMap = new HashMap<>();
        for (String variableName : context.getVariableNames()) {
            variablesMap.put(variableName, context.getVariable(variableName));
        }
        LOGGER.debug("Variables map: {}", variablesMap);
        return variablesMap;
    }

    private String loadTemplate(String name, Locale mailLocale, Map<String, Object> model) throws IOException {
        LOGGER.debug("Loading template {} with Locale {}", name, mailLocale);
        final String templateFileName = name + ".html";
        final ClassPathResource resource = new ClassPathResource("templates/" + templateFileName);
        final byte[] templateBytes = StreamUtils.copyToByteArray(resource.getInputStream());
        final String template = new String(templateBytes, StandardCharsets.UTF_8);
        final Context context = new Context(mailLocale);
        context.setVariables(model);
        return templateEngine.process(template, context);
    }

    public void sendEmail(final String to, final String subject, final String emailTemplate, final Context context, final Locale mailLocale) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(FROM));

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        message.setSubject(subject, "UTF-8");


        context.setVariable("baseUrl", baseUrl);
        final String htmlContent = loadTemplate(emailTemplate, mailLocale, getVariablesMap(context));

        message.setContent(htmlContent, "text/html; charset=UTF-8");

        LOGGER.info("Sending email to '{}' with subject '{}' and Locale '{}'", to, subject, mailLocale);
//        TODO: activar para deploy
        mailSender.send(message);
    }

    @Async
    @Override
    public void sendVerificationEmail(User user, String token) {
        String subject = messageSource.getMessage("emails.sendToken.subject",null,user.getMailLocale());

        // Variables para el html
        final Context ctx = new Context();
        ctx.setVariable("user", user);
        ctx.setVariable("token", token);
        ctx.setVariable("email", user.getEmail());
        ctx.setLocale(user.getMailLocale());

        //enviamos el mail
        try {
            sendEmail(user.getEmail(),subject,"verification-email",ctx, user.getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a verification email to user with id {}",user.getUserId(),e);
        }

    }


    @Async
    @Override
    public void sendMailNewPassenger(Trip trip, Passenger passenger) {
        String subject = messageSource.getMessage("emails.subject.newPassengerDriver", null, trip.getDriver().getMailLocale());

        // Variables para el html
        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);
        ctx.setLocale(trip.getDriver().getMailLocale());

        //enviamos el mail
        try{
            sendEmail(trip.getDriver().getEmail(), subject, "new-passenger-mail", ctx, trip.getDriver().getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a new passenger email to driver with id {} for trip {}",trip.getDriver().getUserId(), trip.getTripId(),e);
        }

    }

    @Async
    @Override
    public void sendMailTripCancelledToDriver(Trip trip, Passenger passenger) {
        String subject = messageSource.getMessage("emails.subject.passengerCancelTrip", null, trip.getDriver().getMailLocale());

        // Variables para el html
        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);
        ctx.setLocale(trip.getDriver().getMailLocale());

        //enviamos el mail
        try {
            sendEmail(trip.getDriver().getEmail(), subject, "trip-cancelled-driver", ctx, trip.getDriver().getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending an email to driver with id {} for the cancellation of trip with id {}",trip.getDriver().getUserId(),trip.getTripId(),e);
        }
    }

    @Async
    @Override
    public void sendMailTripDeletedToPassenger(Trip trip, Passenger passenger) {
        String subject = messageSource.getMessage("emails.subject.tripCancelledPassenger", null, passenger.getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);
        ctx.setLocale(passenger.getMailLocale());
        try {
            sendEmail(passenger.getEmail(), subject, "delete-trip-passenger-mail", ctx, passenger.getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a trip deleted email to passenger {} for trip {}",passenger.getUserId(),trip.getTripId(),e);
        }
    }

    @Async
    @Override
    public void sendMailTripDeletedToDriver(Trip trip) {
        String subject = messageSource.getMessage("emails.subject.tripCancelledDriver", null, trip.getDriver().getMailLocale());

        // Variables para el html
        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setLocale(trip.getDriver().getMailLocale());
        try {
            sendEmail(trip.getDriver().getEmail(), subject, "delete-trip-driver-mail", ctx, trip.getDriver().getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a trip deleted email to driver {} for trip {}",trip.getDriver().getUserId(),trip.getTripId(),e);
        }
    }

    @Async
    @Override
    public void sendMailTripTruncatedToPassenger(Trip trip, Passenger passenger, LocalDateTime nextOccurrence) {
        String subject = messageSource.getMessage("emails.subject.tripCancelledPassenger",null,passenger.getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);
        ctx.setVariable("nextOccurrenceDate",nextOccurrence.toLocalDate());
        ctx.setLocale(passenger.getMailLocale());
        try {
            sendEmail(passenger.getEmail(), subject, "trip-truncated-passenger", ctx, passenger.getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a trip truncated email to passenger {} for trip {} with the next occurrence of {}",passenger.getUserId(),trip.getTripId(),nextOccurrence,e);
        }
    }

    @Async
    @Override
    public void sendMailNewTrip(Trip trip) {
        String subject = messageSource.getMessage("emails.subject.newTripCreated", null, trip.getDriver().getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setLocale(trip.getDriver().getMailLocale());
        try {
            sendEmail(trip.getDriver().getEmail(), subject, "create-trip-mail", ctx, trip.getDriver().getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a new email trip for trip with id {} and driver {}",trip.getTripId(), trip.getDriver().getUserId(),e);
        }
    }

    @Async
    @Override
    public void sendMailTripConfirmation(Trip trip, Passenger passenger) {
        String subject = messageSource.getMessage("emails.subject.newTripPassenger", null, passenger.getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);
        ctx.setLocale(passenger.getMailLocale());
        try {
            sendEmail(trip.getDriver().getEmail(), subject, "trip-confirmation-mail", ctx, passenger.getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a trip confirmation email to passenger {} for trip {}",passenger.getUserId(), trip.getTripId(), e);
        }
    }

    //Se manda al driver indicando que alguien quiere ser pasajero
    @Async
    @Override
    public void sendMailNewPassengerRequest(Trip trip, Passenger passenger) {
        String subject = messageSource.getMessage("emails.subject.newPassenger", null, passenger.getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);
        ctx.setLocale(trip.getDriver().getMailLocale());
        try {
            sendEmail(trip.getDriver().getEmail(), subject, "request-passenger-mail", ctx, passenger.getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a new passenger request email for passenger {} for trip {}",passenger.getUserId(),trip.getTripId(),e);
        }
    }

    //Se manda al pasajero indicando que se recibio su solicitud
    @Async
    @Override
    public void sendMailTripRequest(Trip trip, Passenger passenger) {
        String subject = messageSource.getMessage("emails.subject.newRequestPassenger", null, passenger.getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);
        ctx.setLocale(passenger.getMailLocale());
        try {
            sendEmail(passenger.getEmail(), subject, "request-trip-mail", ctx, passenger.getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a trip request email for passenger {} for trip {}",passenger.getUserId(), trip.getTripId(), e);
        }
    }

    //Se manda al pasajero indicando que fue aceptado en el viaje
    @Async
    @Override
    public void sendMailTripConfirmed(Trip trip, Passenger passenger) {
        String subject = messageSource.getMessage("emails.subject.tripConfirmation", null, passenger.getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);
        ctx.setLocale(passenger.getMailLocale());
        try {
            sendEmail(passenger.getEmail(), subject, "accept-passenger-mail", ctx, passenger.getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a trip confirmation email for passenger {} for trip {}",passenger.getUserId(), trip.getTripId(),e);
        }
    }

    //Se manda al pasajero indicando que fue rechazado en el viaje
    @Async
    @Override
    public void sendMailTripRejected(Trip trip, Passenger passenger) {
        String subject = messageSource.getMessage("emails.subject.tripReject", null, passenger.getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("trip", trip);
        ctx.setVariable("passenger", passenger);
        ctx.setLocale(passenger.getMailLocale());
        try {
            sendEmail(passenger.getEmail(), subject, "reject-passenger-mail", ctx, passenger.getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending a trip rejection email for passenger {} for trip {}",passenger.getUserId(), trip.getTripId(), e);
        }
    }

    //Mail al usuario que hizo el reporte para indicarle que fue rechazado
    @Async
    @Override
    public void sendMailRejectReport(Report report) {
        String subject = messageSource.getMessage("emails.subject.reportReponse", null, report.getReporter().getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("report", report);
        ctx.setLocale(report.getReporter().getMailLocale());
        try {
            sendEmail(report.getReporter().getEmail(), subject, "reject-report-mail", ctx, report.getReporter().getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending an email for report rejection for user {}",report.getReporter().getUserId(),e);
        }
    }


    //Mail al usuario que hizo el reporte para indicarle que fue aeptado
    @Async
    @Override
    public void sendMailAcceptReport(Report report) {
        String subject = messageSource.getMessage("emails.subject.reportReponse", null, report.getReporter().getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("report", report);
        ctx.setLocale(report.getReporter().getMailLocale());
        try {
            sendEmail(report.getReporter().getEmail(), subject, "accept-report-mail", ctx, report.getReporter().getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending an email for report acceptance for user {}",report.getReporter().getUserId(),e);
        }
    }


    //Mail al usuario que recibio el reporte para decirle que fue banneado
    @Async
    @Override
    public void sendMailBanReport(Report report) {
        String subject = messageSource.getMessage("emails.subject.BandReport", null, report.getReported().getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("report", report);
        ctx.setLocale(report.getReported().getMailLocale());
        try {
            sendEmail(report.getReported().getEmail(), subject, "band-report-mail", ctx, report.getReported().getMailLocale());
        }catch (Exception e){
            LOGGER.debug("There was an error sending an email for user {} to indicate it has been banned",report.getReported().getUserId(),e);
        }
    }

    //Mail al admin para decirle que existe un nuevo reporte
    @Async
    @Override
    public void sendMailNewReport(Report report, User admin) {
        String subject = messageSource.getMessage("emails.subject.NewReport", null, admin.getMailLocale());

        final Context ctx = new Context();
        ctx.setVariable("report", report);
        ctx.setLocale(admin.getMailLocale());
        try {
            sendEmail(admin.getEmail(), subject, "new-report-mail", ctx, admin.getMailLocale());
        }catch (Exception e){
            LOGGER.error("There was an error sending an email for admin {} indicating a new report has been filed",admin.getUserId(),e);
        }
    }
}



