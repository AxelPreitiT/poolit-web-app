package ar.edu.itba.paw.interfaces.services;

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

    Report create(User reporter, User reported, Trip trip, String description, LocalDateTime date, ReportRelations relation, ReportOptions reason);

    Optional<Report> findById(long reportId);

    List<Report> getAllReports();

    void acceptReport(long reportId, String reason);

    void rejectReport(long reportId, String reason);

    TripReportCollection getTripReportCollection(long tripId);
}
