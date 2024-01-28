import ReportOptionModel from "@/models/ReportOptionModel";

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

export const getReportsOptions = (): ReportOptionModel[] => {
  const options = Object.values(ReportsOptions).map((option) => ({
    id: option,
  }));
  return options;
};

export default ReportsOptions;
