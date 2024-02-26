import UserPublicModel from "./UserPublicModel";

interface UserPrivateModel extends UserPublicModel {
  cityUri: string;
  email: string;
  futureCreatedTripsUri: string;
  futureReservedTripsUri: string;
  mailLocale: string;
  pastCreatedTripsUri: string;
  pastReservedTripsUri: string;
  phone: string;
  recommendedTripsUri: string;
  role: string;
  driverRating: number;
  passengerRating: number;
  carsUri: string;
  reviewsDriverUri: string;
  reviewsPassengerUri: string;
  tripCount: number;
  reportsPublished: number;
  reportsReceived: number;
  reportsApproved: number;
  reportsRejected: number;
}

export default UserPrivateModel;
