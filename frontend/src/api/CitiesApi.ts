import CityModel from "@/models/CityModel";
import { AxiosPromise, AxiosRequestConfig } from "axios";
import AxiosApi from "./axios/AxiosApi";
import { parseTemplate } from "url-template";

class CitiesApi extends AxiosApi {
  // private static readonly CITY_ID_TEMPLATE_KEY: string = "/cityId";

  public static getCityById: (uri: string) => AxiosPromise<CityModel> = (
    uri: string
  ) => this.get<CityModel>(uri);

  // Todo: On deploy, as we will use OPTIONS method, useRequestInterceptor should be false always
  public static getAllCities = (
    uriTemplate: string,
    config?: AxiosRequestConfig
  ) => {
    const uri = parseTemplate(uriTemplate).expand({});
    return this.get<CityModel[]>(uri, config);
  };
}

export default CitiesApi;
