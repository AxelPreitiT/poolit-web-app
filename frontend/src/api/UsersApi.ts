import UserPublicModel from "@/models/UserPublicModel";
import AxiosApi from "./axios/AxiosApi";
import UserPrivateModel from "@/models/UserPrivateModel";
import { AxiosPromise } from "axios";
import Jwt from "@/auth/Jwt";
import BasicAuth from "@/auth/BasicAuth";
import UtilsApi from "./UtilsApi";
import CurrentUserUriMissingError from "@/errors/CurrentUserUriMissingError";

class UsersApi extends AxiosApi {
  private static readonly USERS_PUBLIC_ACCEPT_HEADER: string =
    "application/vnd.user.public.v1+json";
  private static readonly USERS_PRIVATE_ACCEPT_HEADER: string =
    "application/vnd.user.private.v1+json";

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
    return UtilsApi.tryAuthentication({
      headers: {
        Authorization: authorization,
      },
    });
  };

  public static getPublicUser: (uri: string) => AxiosPromise<UserPublicModel> =
    (uri: string) => {
      return this.get<UserPublicModel>(uri, {
        headers: {
          Accept: UsersApi.USERS_PUBLIC_ACCEPT_HEADER,
        },
      });
    };

  public static getCurrentUser: () => AxiosPromise<UserPrivateModel> = () => {
    const jwtClaims = Jwt.getJwtClaims();
    const userUrl = jwtClaims?.userUrl;
    if (!userUrl) {
      throw new CurrentUserUriMissingError();
    }
    return this.get<UserPrivateModel>(userUrl, {
      headers: {
        Accept: UsersApi.USERS_PRIVATE_ACCEPT_HEADER,
      },
    });
  };
}

export default UsersApi;
