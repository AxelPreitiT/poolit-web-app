package ar.edu.itba.paw.models.reports;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.models.trips.Trip;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator ="reports_report_id_seq" )
    @SequenceGenerator(sequenceName = "reports_report_id_seq" , name = "reports_report_id_seq", allocationSize = 1)
    @Column(name = "report_id")
    private long reportId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reporter_id")
    protected User reporter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reported_id")
    protected User reported;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_id")
    protected Trip trip;

    @Column(name = "description")
    String description;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReportState status;

    @Column(name = "relation")
    @Enumerated(EnumType.STRING)
    private ReportRelations relation;

    @Column(name = "reason")
    @Enumerated(EnumType.STRING)
    private ReportOptions reason;

    @Column(name = "admin_reason")
    private String adminReason;

    protected Report() {
        // Just for Hibernate
    }

    public Report(User reporter, User reported, Trip trip, String description, LocalDateTime date, ReportRelations relation, ReportOptions reason) {
        this.reporter = reporter;
        this.reported = reported;
        this.trip = trip;
        this.description = description;
        this.date = date;
        this.status = ReportState.IN_REVISION;
        this.relation = relation;
        this.reason = reason;
        this.adminReason = null;
    }

    public long getReportId() {
        return reportId;
    }

    public User getReporter() {
        return reporter;
    }

    public User getReported() {
        return reported;
    }

    public Trip getTrip() {
        return trip;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    public void setReported(User reported) {
        this.reported = reported;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


    public ReportRelations getRelation() {
        return relation;
    }

    public ReportOptions getReason() {
        return reason;
    }

    public void setRelation(ReportRelations relation) {
        this.relation = relation;
    }

    public void setReason(ReportOptions reason) {
        this.reason = reason;
    }

    public String getAdminReason() {
        return adminReason;
    }

    public ReportState getStatus() {
        return status;
    }

    public void setStatus(ReportState status) {
        this.status = status;
    }

    public void setAdminReason(String adminReason) {
        this.adminReason = adminReason;
    }
}
