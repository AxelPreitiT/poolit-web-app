import { AxiosPromise } from "axios";
import AxiosApi from "./axios/AxiosApi";
import CarBrandModel from "@/models/CarBrandModel";
import { parseTemplate } from "url-template";

class CarBrandsApi extends AxiosApi {
  private static readonly CAR_BRAND_TYPE =
      "application/vnd.car-brand.v1+json"
  private static readonly CAR_BRAND_LIST_TYPE =
      "application/vnd.car-brand.list.v1+json"
  public static getCarBrands: (
    uriTemplate: string
  ) => AxiosPromise<CarBrandModel[]> = (uriTemplate: string) => {
    const uri = parseTemplate(uriTemplate).expand({});
    return this.get<CarBrandModel[]>(uri,{
      headers:{
        Accept:CarBrandsApi.CAR_BRAND_LIST_TYPE,
      }
    });
  };

  public static getCarBrandByUri: (uri: string) => AxiosPromise<CarBrandModel> =
    (uri: string) => {
      return this.get<CarBrandModel>(uri,{
        headers:{
          Accept:CarBrandsApi.CAR_BRAND_TYPE,
        }
      });
    };
}

export default CarBrandsApi;
