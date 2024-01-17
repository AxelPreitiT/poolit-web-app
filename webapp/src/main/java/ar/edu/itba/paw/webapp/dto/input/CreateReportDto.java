package ar.edu.itba.paw.webapp.dto.input;

import ar.edu.itba.paw.models.reports.ReportOptions;
import ar.edu.itba.paw.models.reports.ReportRelations;
import ar.edu.itba.paw.webapp.dto.validation.annotations.ValidOption;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



@ValidOption(chosenValue = "reason", listableField = "relation", listMethod = "getReportOptions")
public class CreateReportDto {

    @NotNull
    private ReportRelations relation;

    @NotNull
    private ReportOptions reason;

    @Size(min = 10, max = 200)
    private String comment;

    @NotNull
    private Integer reportedId;

    @NotNull
    private Integer tripId;

    public ReportRelations getRelation() {
        return relation;
    }

    public void setRelation(ReportRelations relation) {
        this.relation = relation;
    }

    public ReportOptions getReason() {
        return reason;
    }

    public void setReason(ReportOptions reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getReportedId() {
        return reportedId;
    }

    public void setReportedId(Integer reportedId) {
        this.reportedId = reportedId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }
}
