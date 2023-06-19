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
    ){
        @Override
        public String getReporterSpringMessageCode() {
            return "report.role.driver";
        }

        @Override
        public String getReportedSpringMessageCode() {
            return "report.role.passenger";
        }

        @Override
        public boolean isReporterADriver() {
            return true;
        }

        @Override
        public boolean isReportedADriver() {
            return false;
        }
    },
    PASSENGER_2_DRIVER(
            Arrays.asList(
                    ReportOptions.HARASSMENT,
                    ReportOptions.MISCONDUCT,
                    ReportOptions.IDENTITY_FRAUD,
                    ReportOptions.CANNOT_DRIVE,
                    ReportOptions.DANGEROUS_DRIVING,
                    ReportOptions.OTHER
            )
    ){
        @Override
        public String getReporterSpringMessageCode() {
            return "report.role.passenger";
        }

        @Override
        public String getReportedSpringMessageCode() {
            return "report.role.driver";
        }

        @Override
        public boolean isReporterADriver() {
            return false;
        }

        @Override
        public boolean isReportedADriver() {
            return true;
        }
    },
    PASSENGER_2_PASSENGER(
            Arrays.asList(
                    ReportOptions.HARASSMENT,
                    ReportOptions.MISCONDUCT,
                    ReportOptions.IDENTITY_FRAUD,
                    ReportOptions.OTHER
            )
    ){
        @Override
        public String getReporterSpringMessageCode() {
            return "report.role.passenger";
        }

        @Override
        public String getReportedSpringMessageCode() {
            return "report.role.passenger";
        }

        @Override
        public boolean isReporterADriver() {
            return false;
        }

        @Override
        public boolean isReportedADriver() {
            return false;
        }
    };


    private final List<ReportOptions> reportOptions;

    public abstract String getReporterSpringMessageCode();
    public abstract String getReportedSpringMessageCode();
    public abstract boolean isReporterADriver();
    public abstract boolean isReportedADriver();

    ReportRelations(List<ReportOptions> reportOptions) {
        this.reportOptions = reportOptions;
    }

    public List<ReportOptions> getReportOptions() {
        return reportOptions;
    }
}
