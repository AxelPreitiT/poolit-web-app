package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.models.reports.ReportOptions;
import ar.edu.itba.paw.models.reports.ReportRelations;
import ar.edu.itba.paw.models.reports.TripReportCollection;
import ar.edu.itba.paw.models.trips.Trip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportService {

    Report createReport(long reportedId, long tripId, String description, ReportRelations relation, ReportOptions reason) throws UserNotFoundException, TripNotFoundException;

    Optional<Report> findById(long reportId);

    void acceptReport(long reportId, String reason) throws TripNotFoundException, ReportNotFoundException, UserNotFoundException;

    void rejectReport(long reportId, String reason) throws ReportNotFoundException;

    TripReportCollection getTripReportCollection(long tripId) throws TripNotFoundException, UserNotLoggedInException, PassengerNotFoundException;

    PagedContent<Report> getReports(int page, int pageSize);

}
