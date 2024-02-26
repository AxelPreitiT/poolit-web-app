import DiscoveryModel from "@/models/DiscoveryModel";
import Service from "./Service";
import DiscoveryApi from "@/api/DiscoveryApi";

class DiscoveryService extends Service {
  public static getDiscovery = async (): Promise<DiscoveryModel> => {
    return await this.resolveQuery(DiscoveryApi.getDiscovery());
  };
}

export default DiscoveryService;
