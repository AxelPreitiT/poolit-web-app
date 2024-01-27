import CityModel from "@/models/CityModel";
import { AxiosPromise, AxiosRequestConfig } from "axios";
import AxiosApi from "./axios/AxiosApi";
import { parseTemplate } from "url-template";

class CitiesApi extends AxiosApi {
  // TODO: Use uri template
  public static getCityById: (uri: string) => AxiosPromise<CityModel> = (
    uri: string
  ) => this.get<CityModel>(uri);

  public static getAllCities = (
    uriTemplate: string,
    config?: AxiosRequestConfig
  ) => {
    const uri = parseTemplate(uriTemplate).expand({});
    return this.get<CityModel[]>(uri, config);
  };
}

export default CitiesApi;
