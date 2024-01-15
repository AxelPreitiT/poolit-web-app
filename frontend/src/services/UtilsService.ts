import UtilsApi from "@/api/UtilsApi";
import Service from "./Service";

class UtilsService extends Service {
  public static tryAuthentication = async () => {
    await this.resolveQuery(UtilsApi.tryAuthentication());
    return true;
  };
}

export default UtilsService;
