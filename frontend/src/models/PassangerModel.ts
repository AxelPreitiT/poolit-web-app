interface PassangerModel {
  userId: string;
  selfUri: string;
  endDateTime: string;
  passengerState: string;
  startDateTime: string;
  tripUri: string;
  userUri: string;
  otherPassengersUriTemplate: string;
  passengerReviewsForTripUriTemplate: string;
  passengerReportsForTripUriTemplate: string;
}

export default PassangerModel;
