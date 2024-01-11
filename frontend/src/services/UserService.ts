import UsersApi from "@/api/UsersApi";
import Service from "./Service";

class UserService extends Service {
  public static async login(
    email: string,
    password: string,
    rememberMe: boolean = false
  ) {
    await this.resolveQuery(UsersApi.login(email, password, rememberMe));
  }

  public static async getCurrentUser() {
    return await this.resolveQuery(UsersApi.getCurrentUser());
  }
}

export default UserService;
