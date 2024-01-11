import UtilsApi from "@/api/UtilsApi";
import { AxiosRequestConfig } from "axios";
import Service from "./Service";

class UtilsService extends Service {
  public static async tryAuthentication(options?: AxiosRequestConfig) {
    await this.resolveQuery(UtilsApi.tryAuthentication(options));
  }
}

export default UtilsService;
