import UserPublicModel from "./UserPublicModel";

interface UserPrivateModel extends UserPublicModel {
  type: string;
  cityId: number;
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
}

export default UserPrivateModel;
