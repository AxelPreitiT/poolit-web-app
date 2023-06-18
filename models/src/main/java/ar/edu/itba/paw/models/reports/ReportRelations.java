package ar.edu.itba.paw.models.reports;

import java.util.Arrays;
import java.util.List;

public enum ReportRelations {

    DRIVER_2_PASSENGER(
            Arrays.asList(
                    ReportOptions.HARASSMENT,
                    ReportOptions.MISCONDUCT,
                    ReportOptions.IDENTITY_FRAUD,
                    ReportOptions.DID_NOT_PAY,
                    ReportOptions.WRECK_CAR,
                    ReportOptions.OTHER
            )
    ),
    PASSENGER_2_DRIVER(
            Arrays.asList(
                    ReportOptions.HARASSMENT,
                    ReportOptions.MISCONDUCT,
                    ReportOptions.IDENTITY_FRAUD,
                    ReportOptions.CANNOT_DRIVE,
                    ReportOptions.DANGEROUS_DRIVING,
                    ReportOptions.OTHER
            )
    ),
    PASSENGER_2_PASSENGER(
            Arrays.asList(
                    ReportOptions.HARASSMENT,
                    ReportOptions.MISCONDUCT,
                    ReportOptions.IDENTITY_FRAUD,
                    ReportOptions.OTHER
            )
    );


    private final List<ReportOptions> reportOptions;

    ReportRelations(List<ReportOptions> reportOptions) {
        this.reportOptions = reportOptions;
    }

    public List<ReportOptions> getReportOptions() {
        return reportOptions;
    }
}
