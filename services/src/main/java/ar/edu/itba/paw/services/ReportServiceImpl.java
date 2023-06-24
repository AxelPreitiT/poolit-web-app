package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.persistence.ReportDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.interfaces.services.EmailService;
import ar.edu.itba.paw.interfaces.services.ReportService;
import ar.edu.itba.paw.interfaces.services.TripService;
import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.Passenger;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reports.*;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ReportDao reportDao;

    private final EmailService emailService;
    private final TripService tripService;
    private final UserService userService;

    @Autowired
    public ReportServiceImpl(final ReportDao reportDao, final TripService tripService, final UserService userService, final EmailService emailService) {
        this.reportDao = reportDao;
        this.tripService = tripService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Transactional
    @Override
    public Report createReport(long reportedId, long tripId, String description, ReportRelations relation, ReportOptions reason) throws UserNotFoundException, TripNotFoundException {
        User reported = userService.findById(reportedId).orElseThrow(UserNotFoundException::new);
        Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        User reporter = userService.getCurrentUser().orElseThrow(UserNotFoundException::new);
        LocalDateTime date = LocalDateTime.now();
        Report resp = reportDao.createReport(reporter, reported, trip, description, date, relation, reason);
        List<User> admins = userService.getAdmins();
        for ( User admin:  admins) {
            try {
                emailService.sendMailNewReport(resp, admin);
            }
            catch( Exception e){
                LOGGER.error("There was an error sending the email", e);
            }
        }
        return resp;
    }

    @Transactional
    @Override
    public Optional<Report> findById(long reportId) {
        return reportDao.findById(reportId);
    }

    @Transactional
    @Override
    public void rejectReport(long reportId, String reason) throws ReportNotFoundException {
        reportDao.resolveReport(reportId, reason, ReportState.REJECTED);
        Report report = reportDao.findById(reportId).orElseThrow(ReportNotFoundException::new);
        try {
            emailService.sendMailRejectReport(report);
        }
        catch( Exception e){
            LOGGER.error("There was an error sending the email", e);
        }
    }

    @Transactional
    @Override
    public void acceptReport(long reportId, String reason) throws TripNotFoundException, ReportNotFoundException {
        reportDao.resolveReport(reportId, reason, ReportState.APPROVED);
        Report report = reportDao.findById(reportId).orElseThrow(ReportNotFoundException::new);
        try {
            emailService.sendMailAcceptReport(report);
        }
        catch( Exception e){
            LOGGER.error("There was an error sending the email", e);
        }
        try {
            emailService.sendMailBanReport(report);
        }
        catch( Exception e){
            LOGGER.error("There was an error sending the email", e);
        }
        PagedContent<Trip> pgTripDriver = tripService.getTripsCreatedByUserFuture(report.getReported(), 0, 10);
        for(int i = 0; i < pgTripDriver.getTotalPages(); i++){
            List<Trip> trips = pgTripDriver.getElements();
            for(Trip trip : trips){
                tripService.deleteTrip(trip.getTripId());
            }
            pgTripDriver = tripService.getTripsCreatedByUserFuture(report.getReported(), i+1, 10);
        }

        PagedContent<Trip> pgTripUser = tripService.getTripsWhereUserIsPassengerFuture(report.getReported(), 0, 10);
        for(int i = 0; i < pgTripUser.getTotalPages(); i++){
            List<Trip> trips = pgTripUser.getElements();
            for(Trip trip : trips){
                tripService.rejectPassenger(trip.getTripId(), report.getReported().getUserId());
            }
            pgTripUser = tripService.getTripsWhereUserIsPassengerFuture(report.getReported(), i+1, 10);
        }
        userService.banUser(report.getReported());
    }

    public Optional<Report> getReportByTripAndUsers(long tripId, long reporterId, long reportedId){
        return reportDao.getReportByTripAndUsers(tripId, reporterId, reportedId);
    }

    @Transactional
    @Override
    public TripReportCollection getTripReportCollection(long tripId) throws TripNotFoundException, UserNotLoggedInException, PassengerNotFoundException {
        Trip trip = tripService.findById(tripId).orElseThrow(TripNotFoundException::new);
        User currentUser = userService.getCurrentUser().orElseThrow(UserNotLoggedInException::new);
        ItemReport<User> driverToReport;
        List<ItemReport<Passenger>> passengersToReport;
        if(tripService.userIsDriver(tripId, currentUser)) {
            driverToReport = null;
            List<Passenger> passengers = tripService.getAcceptedPassengers(trip, trip.getStartDateTime(), trip.getEndDateTime());
            passengersToReport = passengers.stream().filter(
                    passenger -> !passenger.getUser().equals(currentUser)
            ).map(
                    passenger -> new ItemReport<>(passenger, ReportRelations.DRIVER_2_PASSENGER, getPassengerReportState(trip, currentUser, passenger))
            ).sorted(
                    Comparator.comparing(ItemReport::getState)
            ).collect(Collectors.toList());
        }
        else if(tripService.userIsPassenger(tripId, currentUser)) {
            Passenger currentPassenger = tripService.getPassenger(tripId, currentUser).orElseThrow(PassengerNotFoundException::new);
            if(!currentPassenger.getAccepted()) {
                return TripReportCollection.empty();
            }
            driverToReport = new ItemReport<>(trip.getDriver(), ReportRelations.PASSENGER_2_DRIVER, getDriverReportState(trip, currentPassenger));
            List<Passenger> passengers = tripService.getAcceptedPassengers(trip, currentPassenger.getStartDateTime(), currentPassenger.getEndDateTime());
            passengersToReport = passengers.stream().filter(
                    passenger -> !passenger.getUser().equals(currentUser)
            ).map(
                    passenger -> new ItemReport<>(passenger, ReportRelations.PASSENGER_2_PASSENGER, getPassengerReportState(trip, currentUser, passenger))
            ).sorted(
                    Comparator.comparing(ItemReport::getState)
            ).collect(Collectors.toList());
        }
        else {
            IllegalStateException e = new IllegalStateException();
            LOGGER.error("The user with id {} is not passenger neither the driver of the trip with id {}", currentUser.getUserId(), tripId);
            throw e;
        }
        return new TripReportCollection(driverToReport, passengersToReport);
    }

    @Transactional
    @Override
    public PagedContent<Report> getReports(int page, int pageSize) {
        return reportDao.getReports(page, pageSize);
    }

    private ReportState getDriverReportState(Trip trip, Passenger reporter) {
        LocalDateTime now = LocalDateTime.now();
        if(reporter.getStartDateTime().isAfter(now)) {
            return ReportState.DISABLED;
        }
        Optional<Report> maybeReport = getReportByTripAndUsers(trip.getTripId(), reporter.getUserId(), trip.getDriver().getUserId());
        if(!maybeReport.isPresent()) {
            return ReportState.AVAILABLE;
        }
        return maybeReport.get().getStatus();
    }

    private ReportState getPassengerReportState(Trip trip, User reporter, Passenger reported) {
        LocalDateTime now = LocalDateTime.now();
        if(reported.getStartDateTime().isAfter(now)) {
            return ReportState.DISABLED;
        }
        Optional<Report> maybeReport = getReportByTripAndUsers(trip.getTripId(), reporter.getUserId(), reported.getUserId());
        if (!maybeReport.isPresent()) {
            return ReportState.AVAILABLE;
        }
        return maybeReport.get().getStatus();
    }


}
