package ar.edu.itba.paw.models.reports;

public enum ReportState {
    AVAILABLE,
    IN_REVISION,
    APPROVED,
    REJECTED,
    DISABLED;

    public String getName() {
        return this.name();
    }

    public String getSpringMessageCode() {
        return "report.state." + getName();
    }
}
