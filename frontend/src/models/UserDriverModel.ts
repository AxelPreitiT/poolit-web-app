import UserPublicModel from "./UserPublicModel";

interface UserDriverModel extends UserPublicModel {
  email: string;
  phone: string;
}

export default UserDriverModel;
