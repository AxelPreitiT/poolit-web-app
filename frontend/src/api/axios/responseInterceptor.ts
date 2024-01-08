import { AxiosError, AxiosRequestConfig, AxiosResponse } from "axios";
import Jwt from "./Jwt";
import UsersApi from "../UsersApi";
import Axios from "./axios";

const unauthorizedHttpStatusCode = 401;
const defaultApiCall = () => UsersApi.getPublicUserById(1);

export const AxiosResponseInterceptor = (response: AxiosResponse) => {
  const authToken = response.headers.Authorization;
  const refreshToken = response.headers["Authorization-refresh"];
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
    const refreshToken = Jwt.getRefreshToken();
    if (refreshToken) {
      await defaultApiCall();
      const authToken = Jwt.getAuthToken();
      if (authToken) {
        const prevRequest = error.config as AxiosRequestConfig;
        prevRequest.headers = {
          ...(prevRequest.headers || {}),
          Authorization: authToken,
        };
        return Axios(prevRequest);
      }
    }
  }
  return Promise.reject(error);
};
