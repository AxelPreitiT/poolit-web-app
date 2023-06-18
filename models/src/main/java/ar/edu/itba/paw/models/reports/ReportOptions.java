package ar.edu.itba.paw.models.reports;

public enum ReportOptions {

    HARASSMENT,
    MISCONDUCT,
    IDENTITY_FRAUD,
    CANNOT_DRIVE,
    DANGEROUS_DRIVING,
    DID_NOT_PAY,
    WRECK_CAR,
    OTHER;

    public String getName() {
        return this.name();
    }

    public String getSpringMessageCode() {
        return "report.option." + this.getName();
    }

}
