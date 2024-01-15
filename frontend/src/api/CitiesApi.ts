import CityModel from "@/models/CityModel";
import { AxiosPromise, AxiosRequestConfig } from "axios";
import AxiosApi from "./axios/AxiosApi";

class CitiesApi extends AxiosApi {
  private static readonly CITIES_BASE_PATH: string = "/cities";

  public static getCityById: (uri: string) => AxiosPromise<CityModel> = (
    uri: string
  ) => this.get<CityModel>(uri);

  public static getOptions: (config: AxiosRequestConfig) => AxiosPromise = (
    config?: AxiosRequestConfig
  ) => this.options(CitiesApi.CITIES_BASE_PATH, config);

  // Todo: On deploy, as we will use OPTIONS method, useRequestInterceptor should be false always
  public static getAllCities = (config?: AxiosRequestConfig) =>
    this.get<CityModel[]>(CitiesApi.CITIES_BASE_PATH, config);
}

export default CitiesApi;
