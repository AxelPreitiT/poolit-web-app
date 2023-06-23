package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;

public class ReportAdminForm {

    @Size(min = 10)
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
