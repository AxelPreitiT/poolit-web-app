import UsersApi from "@/api/UsersApi";
import Service from "./Service";
import { AxiosPromise } from "axios";

class UserService extends Service {
  public static async login(
    email: string,
    password: string,
    rememberMe: boolean = false
  ): AxiosPromise {
    return this.resolveQuery(UsersApi.login(email, password, rememberMe));
  }
}

export default UserService;
