package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.models.reports.ReportOptions;
import ar.edu.itba.paw.models.reports.ReportRelations;
import ar.edu.itba.paw.models.reports.ReportState;
import ar.edu.itba.paw.models.trips.Trip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportDao {

    Report create(User reporter, User reported, Trip trip, String description, LocalDateTime date, ReportRelations relation, ReportOptions reason);

    Optional<Report> findById(long reportId);

    void resolveReport(long reportId, String reason, ReportState state);

    List<Report> getAllReports();

    Optional<Report>  getReportByTripAndUsers(long tripId, long reporterId, long reportedId);
}
