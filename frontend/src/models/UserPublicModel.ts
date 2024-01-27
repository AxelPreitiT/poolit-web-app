interface UserPublicModel {
  id: number;
  driverRating: number;
  passengerRating: number;
  reviewsDriverUri: string;
  reviewsPassengerUri: string;
  imageUri: string;
  selfUri: string;
  surname: string;
  username: string;
  tripCount: number;
}

export default UserPublicModel;
