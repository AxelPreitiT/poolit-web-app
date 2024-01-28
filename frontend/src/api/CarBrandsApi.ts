import { AxiosPromise } from "axios";
import AxiosApi from "./axios/AxiosApi";
import CarBrandModel from "@/models/CarBrandModel";
import { parseTemplate } from "url-template";

class CarBrandsApi extends AxiosApi {
  public static getCarBrands: (
    uriTemplate: string
  ) => AxiosPromise<CarBrandModel[]> = (uriTemplate: string) => {
    const uri = parseTemplate(uriTemplate).expand({});
    return this.get<CarBrandModel[]>(uri);
  };

  public static getCarBrandByUri: (uri: string) => AxiosPromise<CarBrandModel> =
    (uri: string) => {
      return this.get<CarBrandModel>(uri);
    };
}

export default CarBrandsApi;
