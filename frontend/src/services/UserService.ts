import UsersApi from "@/api/UsersApi";
import Service from "./Service";
import UserPrivateModel from "@/models/UserPrivateModel";

class UserService extends Service {
  public static login = async (
    email: string,
    password: string,
    rememberMe: boolean = false
  ) => {
    await this.resolveQuery(UsersApi.login(email, password, rememberMe));
  };

  public static getCurrentUser = async (): Promise<UserPrivateModel> => {
    return await this.resolveQuery(UsersApi.getCurrentUser());
  };
}

export default UserService;
