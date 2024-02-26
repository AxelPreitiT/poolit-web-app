import { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import Axios from "./axios";
import Jwt from "@/auth/Jwt";
import AccountNotVerifiedError from "@/errors/AccountNotVerifiedError";

const unauthorizedHttpStatusCode = 401;
const jwtAuthTokenHeader = "jwt-authorization";
const jwtRefreshTokenHeader = "jwt-refresh-authorization";
const accountVerificationHeader = "account-verification";

type AxiosRetryRequestConfig = AxiosRequestConfig & { retried?: boolean };

export const AxiosResponseInterceptor = (response: AxiosResponse) => {
  const authToken = response.headers[jwtAuthTokenHeader];
  const refreshToken = response.headers[jwtRefreshTokenHeader];
  if (authToken) {
    Jwt.storeAuthToken(authToken);
  }
  if (refreshToken) {
    Jwt.storeRefreshToken(refreshToken);
  }
  return response;
};

export const AxiosResponseErrorInterceptor = async (error: AxiosError) => {
  const errorConfig = error.config as AxiosRetryRequestConfig;
  if (
    error.response?.status === unauthorizedHttpStatusCode &&
    !errorConfig.retried
  ) {
    if (error.response.headers[accountVerificationHeader]) {
      throw new AccountNotVerifiedError();
    }
    const refreshToken = Jwt.getRefreshToken();
    if (refreshToken) {
      const prevRequest = {
        ...(errorConfig || {}),
        headers: {
          Authorization: refreshToken,
        },
        retried: true,
      };
      return Axios(prevRequest);
    }
  }
  return Promise.reject(error);
};
