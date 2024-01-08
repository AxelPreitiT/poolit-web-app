import { AxiosError, InternalAxiosRequestConfig } from "axios";
import Jwt from "./Jwt";

export const AxiosRequestInterceptor = (config: InternalAxiosRequestConfig) => {
  const headers = config.headers;
  if (headers && headers.Authorization) {
    return config;
  }
  const authToken = Jwt.getAuthToken();
  config.headers.Authorization = authToken;
  return config;
};

export const AxiosRequestErrorInterceptor = (error: AxiosError) =>
  Promise.reject(error);
