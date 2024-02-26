enum ReportRelation {
  DRIVER_2_PASSENGER = "DRIVER_2_PASSENGER",
  PASSENGER_2_DRIVER = "PASSENGER_2_DRIVER",
  PASSENGER_2_PASSENGER = "PASSENGER_2_PASSENGER",
}

export const getReportRelation = (
  isCurrentUserDriver: boolean,
  isReportedUserDriver: boolean
): ReportRelation => {
  if (isCurrentUserDriver) {
    return ReportRelation.DRIVER_2_PASSENGER;
  } else {
    return isReportedUserDriver
      ? ReportRelation.PASSENGER_2_DRIVER
      : ReportRelation.PASSENGER_2_PASSENGER;
  }
};

export default ReportRelation;
