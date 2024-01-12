import CityModel from "@/models/CityModel";
import { AxiosPromise } from "axios";
import AxiosApi from "./axios/AxiosApi";

class CitiesApi extends AxiosApi {
  public static getCityById: (uri: string) => AxiosPromise<CityModel> = (
    uri: string
  ) => {
    return this.get<CityModel>(uri);
  };
}

export default CitiesApi;
