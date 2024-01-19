interface TripModel {
  tripId: number;
  carUri: string;
  destinationAddress: number;
  destinationCityUri: string;
  driverUri: string;
  endDateTime: string;
  maxSeats: string;
  occupiedSeats: string;
  originAddress: string;
  originCityUri: string;
  pricePerTrip: number;
  queryEndDateTime: string;
  selfUri: string;
  startDateTime: string;
  totalPrice: number;
  totalTrips: number;
}

export default TripModel;
