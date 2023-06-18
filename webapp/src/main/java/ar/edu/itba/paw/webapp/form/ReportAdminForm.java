package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ReportAdminForm {

    @Pattern(regexp = ".+")
    private String reason;

    public String getComment() {
        return reason;
    }
}
