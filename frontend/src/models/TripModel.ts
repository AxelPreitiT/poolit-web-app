interface TripModel {
  carUri: string;
  destinationAddress: string;
  destinationCityUri: string;
  driverUri: string;
  endDateTime: string;
  maxSeats: string;
  // occupiedSeats: string;
  originAddress: string;
  originCityUri: string;
  passengersUriTemplate: string;
  pricePerTrip: number;
  // queryEndDateTime: string;
  selfUri: string;
  startDateTime: string;
  // totalPrice: number;
  // totalTrips: number;
  tripId: number;
  driverReviewsUriTemplate: string;
  carReviewsUriTemplate: string;
  tripStatus: string;
  deleted: false;
  driverReportsUriTemplate: string;
  // queryStartDateTime: string;
}

export default TripModel;
