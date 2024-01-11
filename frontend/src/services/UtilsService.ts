import UtilsApi from "@/api/UtilsApi";
import { AxiosPromise, AxiosRequestConfig } from "axios";
import Service from "./Service";

class UtilsService extends Service {
  public static async tryAuthentication(
    options: AxiosRequestConfig
  ): AxiosPromise {
    return this.resolveQuery(UtilsApi.tryAuthentication(options));
  }
}

export default UtilsService;
