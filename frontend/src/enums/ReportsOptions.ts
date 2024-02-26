import ReportOptionModel from "@/models/ReportOptionModel";
import ReportRelation from "./ReportRelation";

enum ReportsOptions {
  HARASSMENT = "HARASSMENT",
  MISCONDUCT = "MISCONDUCT",
  IDENTITY_FRAUD = "IDENTITY_FRAUD",
  CANNOT_DRIVE = "CANNOT_DRIVE",
  DANGEROUS_DRIVING = "DANGEROUS_DRIVING",
  DID_NOT_PAY = "DID_NOT_PAY",
  WRECK_CAR = "WRECK_CAR",
  OTHER = "OTHER",
}

const reportOptionsByRelation: Record<string, ReportsOptions[]> = {
  [ReportRelation.DRIVER_2_PASSENGER]: [
    ReportsOptions.HARASSMENT,
    ReportsOptions.MISCONDUCT,
    ReportsOptions.IDENTITY_FRAUD,
    ReportsOptions.DID_NOT_PAY,
    ReportsOptions.WRECK_CAR,
    ReportsOptions.OTHER,
  ],
  [ReportRelation.PASSENGER_2_DRIVER]: [
    ReportsOptions.HARASSMENT,
    ReportsOptions.MISCONDUCT,
    ReportsOptions.IDENTITY_FRAUD,
    ReportsOptions.CANNOT_DRIVE,
    ReportsOptions.DANGEROUS_DRIVING,
    ReportsOptions.OTHER,
  ],
  [ReportRelation.PASSENGER_2_PASSENGER]: [
    ReportsOptions.HARASSMENT,
    ReportsOptions.MISCONDUCT,
    ReportsOptions.IDENTITY_FRAUD,
    ReportsOptions.OTHER,
  ],
};

export const getReportsOptionsByRelation = (
  relation: ReportRelation
): ReportOptionModel[] => {
  const options = reportOptionsByRelation[relation].map((option) => ({
    id: option,
  }));
  return options;
};

export default ReportsOptions;
