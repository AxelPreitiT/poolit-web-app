import { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import Axios from "./axios";
import Jwt from "@/auth/Jwt";

const unauthorizedHttpStatusCode = 401;

export const AxiosResponseInterceptor = (response: AxiosResponse) => {
  const authToken = response.headers.authorization;
  const refreshToken = response.headers["authorization-refresh"];
  if (authToken) {
    Jwt.storeAuthToken(authToken);
  }
  if (refreshToken) {
    Jwt.storeRefreshToken(refreshToken);
  }
  return response;
};

export const AxiosResponseErrorInterceptor = async (error: AxiosError) => {
  if (error.response?.status === unauthorizedHttpStatusCode) {
    // TODO: check if refresh token is expired (run interceptor only once)
    const refreshToken = Jwt.getRefreshToken();
    if (refreshToken) {
      const prevRequest = error.config as AxiosRequestConfig;
      prevRequest.headers = {
        ...(prevRequest.headers || {}),
        Authorization: refreshToken,
      };
      return Axios(prevRequest);
    }
  }
  return Promise.reject(error);
};
