import UtilsApi from "@/api/UtilsApi";
import { AxiosRequestConfig } from "axios";
import Service from "./Service";

class UtilsService extends Service {
  public static tryAuthentication = async (options?: AxiosRequestConfig) => {
    await this.resolveQuery(UtilsApi.tryAuthentication(options));
    return true;
  };
}

export default UtilsService;
