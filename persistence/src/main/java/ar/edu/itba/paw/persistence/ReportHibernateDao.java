package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.ReportDao;
import ar.edu.itba.paw.models.PagedContent;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.models.reports.ReportOptions;
import ar.edu.itba.paw.models.reports.ReportRelations;
import ar.edu.itba.paw.models.reports.ReportState;
import ar.edu.itba.paw.models.trips.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class ReportHibernateDao implements ReportDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserHibernateDao.class);

    @PersistenceContext
    private EntityManager em;


    @Override
    public Report createReport(User reporter, User reported, Trip trip, String description, LocalDateTime date, ReportRelations relation, ReportOptions reason) {
        LOGGER.debug("Adding new Report to the database");
        final Report report = new Report(reporter, reported, trip, description, date, relation, reason);
        em.persist(report);
        LOGGER.info("Report with id {} added to the database", report.getReportId());
        LOGGER.debug("New {}", report);
        return report;
    }

    @Override
    public Optional<Report> findById(long reportId) {
        LOGGER.debug("Looking for report with id {} in the database", reportId);
        final Optional<Report> result = Optional.ofNullable(em.find(Report.class, reportId));
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }

    @Override
    public void resolveReport(long reportId, String reason, ReportState state){
        LOGGER.debug("Resolveing report with id {}", reportId);
        Optional<Report> maybeReport = findById(reportId);
        if (maybeReport.isPresent()) {
            final Report report = maybeReport.get();
            report.setStatus(state);
            report.setAdminReason(reason);
            em.persist(report);
        }
    }


    @Override
    public PagedContent<Report> getReports(int page, int pageSize) {
        LOGGER.debug("Looking for all reports in page {} with page size {}",page,pageSize);
        Query nativeCountQuery = em.createNativeQuery("SELECT COUNT(report_id) FROM reports WHERE status='IN_REVISION'");
        final int totalCount = ((Number) nativeCountQuery.getSingleResult()).intValue();
        if (totalCount == 0) {
            LOGGER.debug("No reports");
            return PagedContent.emptyPagedContent();
        }

        // 1+1 query
        Query nativeQuery = em.createNativeQuery("SELECT report_id FROM reports  WHERE status='IN_REVISION' ORDER BY date ASC");
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult(page * pageSize);

        final List<?> maybeReportIdList = nativeQuery.getResultList();
        if(maybeReportIdList.isEmpty()){
            return new PagedContent<>(Collections.emptyList(),page,pageSize, totalCount);
        }
        final List<Long> reportIdList = maybeReportIdList.stream().map(id -> ((Number) id).longValue()).collect(Collectors.toList());
        final TypedQuery<Report> reportQuery = em.createQuery("FROM Report rp WHERE rp.reportId IN :reportIdList ORDER BY date ASC", Report.class);
        reportQuery.setParameter("reportIdList", reportIdList);
        List<Report> result = reportQuery.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return new PagedContent<>(result, page, pageSize, totalCount);
    }

    @Override
    public PagedContent<Report> getReportsMadeByUserOnTrip(final User reporter, final Trip trip, final int page, final int pageSize){
        LOGGER.debug("Looking for all reports made by user {} on trip {} in page {} with page size {}",reporter.getUserId(), trip.getTripId(),page,pageSize);
        Query nativeCountQuery = em.createNativeQuery("SELECT COUNT(report_id) FROM reports r WHERE r.reporter_id = :reporter_id AND r.trip_id = :trip_id");
        nativeCountQuery.setParameter("reporter_id",reporter.getUserId());
        nativeCountQuery.setParameter("trip_id",trip.getTripId());
        final int totalCount = ((Number) nativeCountQuery.getSingleResult()).intValue();
        if (totalCount == 0) {
            LOGGER.debug("No reports");
            return PagedContent.emptyPagedContent();
        }

        // 1+1 query
        Query nativeQuery = em.createNativeQuery("SELECT report_id FROM reports  r WHERE r.reporter_id = :reporter_id AND r.trip_id = :trip_id ORDER BY date ASC");
        nativeQuery.setParameter("reporter_id",reporter.getUserId());
        nativeQuery.setParameter("trip_id",trip.getTripId());
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult(page * pageSize);

        final List<?> maybeReportIdList = nativeQuery.getResultList();
        if(maybeReportIdList.isEmpty()){
            return new PagedContent<>(Collections.emptyList(),page,pageSize, totalCount);
        }
        final List<Long> reportIdList = maybeReportIdList.stream().map(id -> ((Number) id).longValue()).collect(Collectors.toList());
        final TypedQuery<Report> reportQuery = em.createQuery("FROM Report rp WHERE rp.reportId IN :reportIdList ORDER BY date ASC", Report.class);
        reportQuery.setParameter("reportIdList", reportIdList);
        List<Report> result = reportQuery.getResultList();
        LOGGER.debug("Found {} in the database", result);
        return new PagedContent<>(result, page, pageSize, totalCount);
    }

    @Override
    public Optional<Report>  getReportByTripAndUsers(long tripId, long reporterId, long reportedId){
        LOGGER.debug("Getting report with tripId {}, reporterId {} and reportedId {}", tripId, reporterId, reportedId);
        final Optional<Report> result = em.createQuery("from Report r where r.trip.id = :tripId and r.reporter.id = :reporterId and r.reported.id = :reportedId", Report.class)
                .setParameter("tripId", tripId)
                .setParameter("reporterId", reporterId)
                .setParameter("reportedId", reportedId).getResultList().stream().findFirst();
        LOGGER.debug("Found {} in the database", result.isPresent() ? result.get() : "nothing");
        return result;
    }
}
