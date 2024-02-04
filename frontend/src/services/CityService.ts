import Service from "./Service";
import CitiesApi from "@/api/CitiesApi.ts";
import CityModel from "@/models/CityModel.ts";

class CityService extends Service {
  public static getCityByUri = async (uri: string): Promise<CityModel> =>
    await this.resolveQuery(CitiesApi.getCityByUri(uri));

  public static getAllCities = async (
    uriTemplate: string
  ): Promise<CityModel[]> =>
    await this.resolveQuery(CitiesApi.getAllCities(uriTemplate));
}

export default CityService;
