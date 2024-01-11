import CityModel from "@/models/CityModel";
import { AxiosPromise, AxiosRequestConfig } from "axios";
import AxiosApi from "./axios/AxiosApi";

class CitiesApi extends AxiosApi {
  private static readonly CITIES_BASE_PATH: string = "/cities";

  public static getCityById: (id: number) => AxiosPromise<CityModel> = (
    id: number
  ) => {
    return this.get<CityModel>(`${CitiesApi.CITIES_BASE_PATH}/${id}`);
  };

  public static getOptions: (options: AxiosRequestConfig) => AxiosPromise = (
    options: AxiosRequestConfig
  ) => {
    return this.options(CitiesApi.CITIES_BASE_PATH, options);
  };
}

export default CitiesApi;
