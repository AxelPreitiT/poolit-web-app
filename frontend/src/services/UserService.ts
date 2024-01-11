import UsersApi from "@/api/UsersApi";
import Service from "./Service";
import { AxiosPromise } from "axios";
import UserPrivateModel from "@/models/UserPrivateModel";

class UserService extends Service {
  public static async login(
    email: string,
    password: string,
    rememberMe: boolean = false
  ): AxiosPromise {
    return this.resolveQuery(UsersApi.login(email, password, rememberMe));
  }

  public static async getCurrentUser(): AxiosPromise<UserPrivateModel> {
    return this.resolveQuery(UsersApi.getCurrentUser());
  }
}

export default UserService;
