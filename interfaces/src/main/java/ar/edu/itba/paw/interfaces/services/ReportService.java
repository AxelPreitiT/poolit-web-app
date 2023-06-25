package ar.edu.itba.paw.interfaces.services;

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

    Report createReport(long reportedId, long tripId, String description, ReportRelations relation, ReportOptions reason);

    Optional<Report> findById(long reportId);

    List<Report> getAllReports();

    void acceptReport(long reportId, String reason) throws TripNotFoundException, UserNotFoundException;

    void rejectReport(long reportId, String reason);

    TripReportCollection getTripReportCollection(long tripId);

    PagedContent<Report> getReports(int page, int pageSize);

}
