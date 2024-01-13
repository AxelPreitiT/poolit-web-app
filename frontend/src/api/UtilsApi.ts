import { AxiosPromise, AxiosRequestConfig } from "axios";
import CitiesApi from "./CitiesApi";
import AxiosApi from "./axios/AxiosApi";

class UtilsApi extends AxiosApi {
  public static tryAuthentication: (
    options?: AxiosRequestConfig
  ) => AxiosPromise = (options?: AxiosRequestConfig) => {
    return CitiesApi.getOptionsGaston(options || {});
  };
}

export default UtilsApi;
