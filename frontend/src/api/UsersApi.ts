import UserPublicModel from "@/models/UserPublicModel";
import AxiosApi from "./axios/AxiosApi";
import ErrorModel from "@/models/ErrorModel";
import UserPrivateModel from "@/models/UserPrivateModel";
import { AxiosResponse } from "axios";
import BasicAuth from "./BasicAuth";
import Jwt from "./axios/Jwt";

class UsersApi {
  private static readonly USERS_BASE_PATH: string = "/users";
  private static readonly USERS_PUBLIC_ACCEPT_HEADER: string =
    "application/vnd.user.output.public.v1+json";
  private static readonly USERS_PRIVATE_ACCEPT_HEADER: string =
    "application/vnd.user.output.private.v1+json";
  private static readonly DEFAULT_REQUEST_PATH: string = `${
    UsersApi.USERS_BASE_PATH
  }/${1}`;

  public static login: (
    email: string,
    password: string,
    rememberMe: boolean
  ) => Promise<AxiosResponse<UserPublicModel>> = (
    email: string,
    password: string,
    rememberMe: boolean = false
  ) => {
    Jwt.setRememberMe(rememberMe);
    const authorization: string = BasicAuth.encode(email, password);
    return AxiosApi.get<UserPublicModel>(UsersApi.DEFAULT_REQUEST_PATH, {
      headers: {
        Accept: UsersApi.USERS_PUBLIC_ACCEPT_HEADER,
        Authorization: authorization,
      },
    });
  };

  public static getPublicUserById: (
    id: number
  ) => Promise<AxiosResponse<UserPublicModel | ErrorModel>> = (id: number) => {
    return AxiosApi.get<UserPublicModel>(`${UsersApi.USERS_BASE_PATH}/${id}`, {
      headers: {
        Accept: UsersApi.USERS_PUBLIC_ACCEPT_HEADER,
      },
    });
  };

  public static getPrivateUserById: (
    id: number
  ) => Promise<AxiosResponse<UserPrivateModel | ErrorModel>> = (id: number) => {
    return AxiosApi.get<UserPrivateModel>(`${UsersApi.USERS_BASE_PATH}/${id}`, {
      headers: {
        Accept: UsersApi.USERS_PRIVATE_ACCEPT_HEADER,
      },
    });
  };
}

export default UsersApi;
