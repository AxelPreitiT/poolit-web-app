import UsersApi from "@/api/UsersApi";
import Service from "./Service";
import UserPrivateModel from "@/models/UserPrivateModel";
import { RegisterFormSchemaType } from "@/forms/RegisterForm";
import { LoginFormSchemaType } from "@/forms/LoginForm";
import UserPublicModel from "@/models/UserPublicModel.ts";

class UserService extends Service {
  public static login = async (data: LoginFormSchemaType) => {
    const { email, password, remember_me: rememberMe } = data;
    await this.resolveQuery(UsersApi.login(email, password, !!rememberMe));
  };

  public static logout = async () => {
    UsersApi.logout();
  };

  public static verifyAccount = async (email: string, token: string) => {
    await this.resolveQuery(UsersApi.verifyAccount(email, token));
  };

  public static register = async (registerForm: RegisterFormSchemaType) => {
    // TODO: Resolve upload image on register
    await this.resolveQuery(UsersApi.createUser(registerForm));
    // const response = await this.resolveQuery(UsersApi.createUser(registerForm));
    // const { userUri } = response;
    // if (registerForm.image) {
    // TODO: Concat /image
    // await this.resolveQuery(
    //   UsersApi.updateUserImage(`${userUri}/image`, registerForm.image)
    // );
    // }
  };

  public static getCurrentUser = async (): Promise<UserPrivateModel> =>
    await this.resolveQuery(UsersApi.getCurrentUser());

  public static getUserById = async  (uri : string): Promise<UserPublicModel> =>
      await this.resolveQuery(UsersApi.getPublicUser(uri));
}

export default UserService;
