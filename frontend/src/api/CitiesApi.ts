import CityModel from "@/models/CityModel";
import { AxiosPromise, AxiosRequestConfig } from "axios";
import AxiosApi from "./axios/AxiosApi";

class CitiesApi extends AxiosApi {
  private static readonly CITIES_BASE_PATH: string = "/cities";

  public static getCityById: (uri: string) => AxiosPromise<CityModel> = (
    uri: string
  ) => {
    return this.get<CityModel>(uri);
  };

  public static getOptions: (options: AxiosRequestConfig) => AxiosPromise = (
    options: AxiosRequestConfig
  ) => {
    return this.options(CitiesApi.CITIES_BASE_PATH, options);
  };
}

export default CitiesApi;
