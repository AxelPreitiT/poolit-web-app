import { AxiosError, InternalAxiosRequestConfig } from "axios";
import Jwt from "@/auth/Jwt";
import ApiLocale from "./ApiLocale";

const localeRequestIntercept: (
  config: InternalAxiosRequestConfig
) => InternalAxiosRequestConfig = (config) => {
  const newConfig = { ...config };
  if (newConfig.headers && newConfig.headers["Accept-Language"]) {
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
  if (newConfig.headers && newConfig.headers["Authorization"]) {
    return newConfig;
  }
  const authToken = Jwt.getAuthToken();
  if (authToken) {
    newConfig.headers["Authorization"] = authToken;
    return newConfig;
  }
  const refreshToken = Jwt.getRefreshToken();
  if (refreshToken) {
    newConfig.headers["Authorization"] = refreshToken;
    return newConfig;
  }
  return newConfig;
};

export const AxiosRequestInterceptor = (config: InternalAxiosRequestConfig) => {
  console.log("config pre", config);
  config = localeRequestIntercept(config);
  config = authRequestIntercept(config);
  console.log("config post", config);
  return config;
};

export const AxiosRequestErrorInterceptor = (error: AxiosError) =>
  Promise.reject(error);
