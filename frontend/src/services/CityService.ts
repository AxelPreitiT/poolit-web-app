import Service from "./Service";
import CitiesApi from "@/api/CitiesApi.ts";
import CityModel from "@/models/CityModel.ts";

class CityService extends Service {
  public static getCityById = async (uri: string): Promise<CityModel> => {
    return await this.resolveQuery(CitiesApi.getCityById(uri));
  };
}

export default CityService;
