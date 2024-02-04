package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.interfaces.exceptions.*;
import ar.edu.itba.paw.interfaces.exceptions.TripNotFoundException;
import ar.edu.itba.paw.interfaces.exceptions.UserNotFoundException;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.reports.*;

import java.util.Optional;

public interface ReportService {

    Report createReport(long reportedId, long tripId, String description, ReportRelations relation, ReportOptions reason) throws UserNotFoundException, TripNotFoundException, PassengerNotFoundException;

    Optional<Report> findById(long reportId);

    void acceptOrRejectReport(final long reportId, final ReportState reportState, final String reason) throws ReportNotFoundException, ReportAlreadyProcessedException, UserNotFoundException, PassengerNotFoundException, TripNotFoundException;

    PagedContent<Report> getReports(int page, int pageSize);
    PagedContent<Report> getReport(final long reporterUserId, final long reportedUserId, final long tripId);

    PagedContent<Report> getReportsMadeByUserOnTrip(final long reporterUserId, final long tripId, final int page, final int pageSize) throws UserNotFoundException, TripNotFoundException;

}
