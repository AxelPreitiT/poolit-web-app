package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.models.reports.ReportState;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DecideReportDto {

    @NotNull
    private ReportState reportState;

    @Size(min = 10,max = 200)
    private String reason;

    public ReportState getReportState() {
        return reportState;
    }

    public void setReportState(ReportState reportState) {
        this.reportState = reportState;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
