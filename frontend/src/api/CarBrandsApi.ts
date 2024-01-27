import { AxiosPromise } from "axios";
import AxiosApi from "./axios/AxiosApi";
import CarBrandModel from "@/models/CarBrandModel";
import { parseTemplate } from "url-template";

class CarBrandsApi extends AxiosApi {
  private static readonly BRAND_ID_TEMPLATE_KEY: string = "/brandId";

  public static getCarBrands: (
    uriTemplate: string
  ) => AxiosPromise<CarBrandModel[]> = (uriTemplate: string) => {
    const uri = parseTemplate(uriTemplate).expand({});
    return this.get<CarBrandModel[]>(uri);
  };

  public static getCarBrandById: (
    uriTemplate: string,
    brandId: string
  ) => AxiosPromise<CarBrandModel> = (uriTemplate: string, brandId: string) => {
    const uri = parseTemplate(uriTemplate).expand({
      [this.BRAND_ID_TEMPLATE_KEY]: brandId,
    });
    return this.get<CarBrandModel>(uri);
  };
}

export default CarBrandsApi;
