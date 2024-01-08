import UserPublicModel from "@/models/UserPublicModel";
import AxiosApi from "./axios/AxiosApi";
import UserPrivateModel from "@/models/UserPrivateModel";
import { AxiosPromise } from "axios";
import BasicAuth from "./BasicAuth";
import Jwt from "./axios/Jwt";
import CitiesApi from "./CitiesApi";

class UsersApi {
  private static readonly USERS_BASE_PATH: string = "/users";
  private static readonly USERS_PUBLIC_ACCEPT_HEADER: string =
    "application/vnd.user.output.public.v1+json";
  private static readonly USERS_PRIVATE_ACCEPT_HEADER: string =
    "application/vnd.user.output.private.v1+json";

  public static login: (
    email: string,
    password: string,
    rememberMe: boolean
  ) => AxiosPromise = (
    email: string,
    password: string,
    rememberMe: boolean = false
  ) => {
    Jwt.setRememberMe(rememberMe);
    const authorization: string = BasicAuth.encode(email, password);
    return CitiesApi.getOptions({
      headers: {
        Authorization: authorization,
      },
    });
  };

  public static getPublicUserById: (
    id: number
  ) => AxiosPromise<UserPublicModel> = (id: number) => {
    return AxiosApi.get<UserPublicModel>(`${UsersApi.USERS_BASE_PATH}/${id}`, {
      headers: {
        Accept: UsersApi.USERS_PUBLIC_ACCEPT_HEADER,
      },
    });
  };

  public static getPrivateUserById: (
    id: number
  ) => AxiosPromise<UserPrivateModel> = (id: number) => {
    return AxiosApi.get<UserPrivateModel>(`${UsersApi.USERS_BASE_PATH}/${id}`, {
      headers: {
        Accept: UsersApi.USERS_PRIVATE_ACCEPT_HEADER,
      },
    });
  };
}

export default UsersApi;
