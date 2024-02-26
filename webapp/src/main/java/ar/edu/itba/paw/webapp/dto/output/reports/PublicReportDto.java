package ar.edu.itba.paw.webapp.dto.output.reports;

import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class PublicReportDto {
    private long reportId;
    private String description;
    private LocalDateTime dateTime;

    private URI selfUri;
    private URI reportedUri;
    private URI tripUri;

    public PublicReportDto(){}
    protected PublicReportDto(final UriInfo uriInfo, final Report report){
        this.reportId = report.getReportId();
        this.description = report.getDescription();
        this.dateTime = report.getDate();
        this.selfUri = uriInfo.getBaseUriBuilder().path(UrlHolder.REPORT_BASE).path(String.valueOf(report.getReportId())).build();
        this.reportedUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(report.getReported().getUserId())).build();
        this.tripUri = uriInfo.getBaseUriBuilder().path(UrlHolder.TRIPS_BASE).path(String.valueOf(report.getTrip().getTripId())).build();
    }

    public static PublicReportDto fromReport(final UriInfo uriInfo, final Report report){
        return new PublicReportDto(uriInfo,report);
    }

    public long getReportId() {
        return reportId;
    }

    public void setReportId(long reportId) {
        this.reportId = reportId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }

    public URI getReportedUri() {
        return reportedUri;
    }

    public void setReportedUri(URI reportedUri) {
        this.reportedUri = reportedUri;
    }

    public URI getTripUri() {
        return tripUri;
    }

    public void setTripUri(URI tripUri) {
        this.tripUri = tripUri;
    }
}
