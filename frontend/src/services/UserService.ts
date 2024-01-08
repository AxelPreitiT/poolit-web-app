import UsersApi from "@/api/UsersApi";
import Service from "./Service";
import UserPublicModel from "@/models/UserPublicModel";

class UserService extends Service {
  public static async login(
    email: string,
    password: string,
    rememberMe: boolean = false
  ): Promise<UserPublicModel> {
    const response = await UsersApi.login(email, password, rememberMe);
    return this.resolveResponse(response);
  }
}

export default UserService;
