import CityModel from "@/models/CityModel";
import { AxiosPromise, AxiosRequestConfig } from "axios";
import AxiosApi from "./axios/AxiosApi";
import { parseTemplate } from "url-template";

class CitiesApi extends AxiosApi {

  private static readonly  CITY_LIST_TYPE =
      "application/vnd.city.list.v1+json";
  private static readonly CITY_TYPE =
      "application/vnd.city.v1+json"
  public static getCityByUri: (uri: string) => AxiosPromise<CityModel> = (
    uri: string
  ) => this.get<CityModel>(uri,{
    headers:{
      Accept:CitiesApi.CITY_TYPE,
    }
  });

  public static getAllCities = (
    uriTemplate: string,
    config?: AxiosRequestConfig
  ) => {
    const config_header: AxiosRequestConfig = {
      headers:{
        Accept: CitiesApi.CITY_LIST_TYPE,
        ...config?.headers
      },
      ...config
    }
    const uri = parseTemplate(uriTemplate).expand({});
    return this.get<CityModel[]>(uri, config_header);
  };
}

export default CitiesApi;
