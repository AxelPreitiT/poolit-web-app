import { AxiosError, InternalAxiosRequestConfig } from "axios";
import AuthorizationHeaderMissingError from "@/errors/AuthorizationHeaderMissingError";
import Jwt from "@/auth/Jwt";

export const AxiosRequestInterceptor = (config: InternalAxiosRequestConfig) => {
  const headers = config.headers;
  if (headers && headers.Authorization) {
    return config;
  }
  const authToken = Jwt.getAuthToken();
  if (authToken) {
    config.headers.Authorization = authToken;
    return config;
  }
  const refreshToken = Jwt.getRefreshToken();
  if (refreshToken) {
    config.headers.Authorization = refreshToken;
    return config;
  }
  Promise.reject(
    new AuthorizationHeaderMissingError("Authorization header missing")
  );
  return config;
};

export const AxiosRequestErrorInterceptor = (error: AxiosError) =>
  Promise.reject(error);
