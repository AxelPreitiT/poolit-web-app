package ar.edu.itba.paw.webapp.dto.output.reports;

import ar.edu.itba.paw.models.reports.Report;
import ar.edu.itba.paw.models.reports.ReportOptions;
import ar.edu.itba.paw.models.reports.ReportRelations;
import ar.edu.itba.paw.models.reports.ReportState;
import ar.edu.itba.paw.webapp.controller.utils.UrlHolder;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlType;
import java.net.URI;

@XmlType(name = "")
public class PrivateReportDto extends PublicReportDto{

    private ReportState reportState;
    private ReportRelations relation;
    private ReportOptions reportOption;
    private String adminReason;

    private URI reporterUri;

    public PrivateReportDto(){}

    protected PrivateReportDto(final UriInfo uriInfo, final Report report){
        super(uriInfo,report);
        this.reportState = report.getStatus();
        this.relation = report.getRelation();
        this.reportOption = report.getReason();
        this.adminReason = report.getAdminReason();
        this.reporterUri = uriInfo.getBaseUriBuilder().path(UrlHolder.USER_BASE).path(String.valueOf(report.getReporter().getUserId())).build();
    }
    public static PrivateReportDto fromReport(final UriInfo uriInfo, final Report report){
        return new PrivateReportDto(uriInfo,report);
    }



    public ReportState getReportState() {
        return reportState;
    }

    public void setReportState(ReportState reportState) {
        this.reportState = reportState;
    }

    public ReportRelations getRelation() {
        return relation;
    }

    public void setRelation(ReportRelations relation) {
        this.relation = relation;
    }

    public ReportOptions getReportOption() {
        return reportOption;
    }

    public void setReportOption(ReportOptions reportOption) {
        this.reportOption = reportOption;
    }

    public String getAdminReason() {
        return adminReason;
    }

    public void setAdminReason(String adminReason) {
        this.adminReason = adminReason;
    }

    public URI getReporterUri() {
        return reporterUri;
    }

    public void setReporterUri(URI reporterUri) {
        this.reporterUri = reporterUri;
    }
}
