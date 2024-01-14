import Service from "./Service";
import CitiesApi from "@/api/CitiesApi.ts";
import CityModel from "@/models/CityModel.ts";

class CityService extends Service {
  public static getCityById = async (uri: string): Promise<CityModel> =>
    await this.resolveQuery(CitiesApi.getCityById(uri));

  public static getAllCities = async (): Promise<CityModel[]> =>
    await this.resolveQuery(CitiesApi.getAllCities());
}

export default CityService;
