import { AxiosPromise, AxiosRequestConfig } from "axios";
import AxiosApi from "./axios/AxiosApi";

class UtilsApi extends AxiosApi {
  public static tryAuthentication: (
    options?: AxiosRequestConfig
  ) => AxiosPromise = (options?: AxiosRequestConfig) => {
    return AxiosApi.options("/", options || {});
  };
}

export default UtilsApi;
