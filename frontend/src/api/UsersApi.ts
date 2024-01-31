import UserPublicModel from "@/models/UserPublicModel";
import AxiosApi from "./axios/AxiosApi";
import UserPrivateModel from "@/models/UserPrivateModel";
import { AxiosPromise, AxiosResponse } from "axios";
import Jwt from "@/auth/Jwt";
import BasicAuth from "@/auth/BasicAuth";
import AuthApi from "./AuthApi";
import CurrentUserUriMissingError from "@/errors/CurrentUserUriMissingError";
import { RegisterFormSchemaType } from "@/forms/RegisterForm";
import RegisterModel from "@/models/RegisterModel";
import { EditProfileFormSchemaType } from "@/forms/EditProfileForm";

class UsersApi extends AxiosApi {
  private static readonly USERS_BASE_URI: string = "/users";
  private static readonly USERS_PUBLIC_ACCEPT_HEADER: string =
    "application/vnd.user.public.v1+json";
  private static readonly USERS_PRIVATE_ACCEPT_HEADER: string =
    "application/vnd.user.private.v1+json";
  private static readonly USERS_CONTENT_TYPE_HEADER: string =
    "application/vnd.user.v1+json";

  public static login: (
    email: string,
    password: string,
    rememberMe: boolean
  ) => AxiosPromise<void> = (
    email: string,
    password: string,
    rememberMe: boolean = false
  ) => {
    Jwt.setRememberMe(rememberMe);
    const authorization = BasicAuth.encode(email, password);
    return AuthApi.authenticate(authorization);
  };

  public static logout: () => void = () => {
    Jwt.removeTokens();
  };

  public static verifyAccount: (
    email: string,
    token: string
  ) => AxiosPromise<void> = (email: string, token: string) => {
    const authorization = BasicAuth.encode(email, token);
    return AuthApi.authenticate(authorization);
  };

  public static createUser: (
    registerForm: RegisterFormSchemaType
  ) => AxiosPromise<RegisterModel> = ({
    email,
    password,
    name,
    last_name,
    locale,
    telephone,
    city,
  }: RegisterFormSchemaType) => {
    // Todo: Concat /users
    return this.post(
      UsersApi.USERS_BASE_URI,
      {
        email,
        password,
        username: name,
        surname: last_name,
        mailLocale: locale,
        phone: telephone,
        bornCityId: city,
      },
      {
        headers: {
          "Content-Type": UsersApi.USERS_CONTENT_TYPE_HEADER,
        },
      }
    ).then((response: AxiosResponse) => {
      const userUri = response.headers.location as string;
      const newResponse = { ...response, data: { userUri } as RegisterModel };
      return newResponse;
    });
  };

  public static updateUserImage: (
    uri: string,
    image: File
  ) => AxiosPromise<void> = (uri: string, image: File) => {
    const formData = new FormData();
    formData.append("image", image);
    return this.put(uri, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
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

  public static getPrivateUser: (uri: string) => AxiosPromise<UserPrivateModel> =
      (uri: string) => {
        return this.get<UserPrivateModel>(uri, {
          headers: {
            Accept: UsersApi.USERS_PRIVATE_ACCEPT_HEADER,
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

  public static updateUser: (
    userUri: string,
    data: EditProfileFormSchemaType
  ) => AxiosPromise<void> = (
    userUri: string,
    { name, last_name, telephone, city, locale }
  ) => {
    return this.patch(
      userUri,
      {
        username: name,
        surname: last_name,
        phone: telephone,
        bornCityId: city,
        mailLocale: locale,
      },
      {
        headers: {
          "Content-Type": UsersApi.USERS_CONTENT_TYPE_HEADER,
        },
      }
    );
  };
}

export default UsersApi;
