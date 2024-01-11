import { AxiosError, InternalAxiosRequestConfig } from "axios";
import AuthorizationHeaderMissingError from "@/errors/AuthorizationHeaderMissingError";
import Jwt from "@/auth/Jwt";
import ApiLocale from "./ApiLocale";

const localeRequestIntercept: (
  config: InternalAxiosRequestConfig
) => InternalAxiosRequestConfig = (config) => {
  const newConfig = { ...config };
  const headers = newConfig.headers;
  if (headers && headers["Accept-Language"]) {
    return newConfig;
  }
  const locale = ApiLocale.getLocale();
  if (locale) {
    newConfig.headers["Accept-Language"] = locale;
    return newConfig;
  }
  return newConfig;
};

const authRequestIntercept: (
  config: InternalAxiosRequestConfig
) => InternalAxiosRequestConfig = (config) => {
  const newConfig = { ...config };
  const headers = newConfig.headers;
  if (headers && headers.Authorization) {
    return newConfig;
  }
  const authToken = Jwt.getAuthToken();
  if (authToken) {
    newConfig.headers.Authorization = authToken;
    return newConfig;
  }
  const refreshToken = Jwt.getRefreshToken();
  if (refreshToken) {
    newConfig.headers.Authorization = refreshToken;
    return newConfig;
  }
  throw new AuthorizationHeaderMissingError("Authorization header missing");
};

export const AxiosRequestInterceptor = (config: InternalAxiosRequestConfig) => {
  config = localeRequestIntercept(config);
  config = authRequestIntercept(config);
  return config;
};

export const AxiosRequestErrorInterceptor = (error: AxiosError) =>
  Promise.reject(error);
