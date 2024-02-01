import UserPublicModel from "@/models/UserPublicModel";

interface IMake {
  user: UserPublicModel;
  isReport: boolean;
  isDriver: boolean;
}

export default IMake;
