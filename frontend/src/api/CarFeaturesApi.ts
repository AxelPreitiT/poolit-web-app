import CarFeatureModel from "@/models/CarFeatureModel";
import AxiosApi from "./axios/AxiosApi";
import { AxiosPromise } from "axios";
import { parseTemplate } from "url-template";

class CarFeaturesApi extends AxiosApi {
  private static readonly FEATURE_ID_TEMPLATE_KEY: string = "/featureId";

  public static getCarFeatures: (
    uri: string
  ) => AxiosPromise<CarFeatureModel[]> = (uriTemplate: string) => {
    const uri = parseTemplate(uriTemplate).expand({});
    return this.get<CarFeatureModel[]>(uri);
  };

  public static getCarFeatureById: (
    uri: string,
    featureId: string
  ) => AxiosPromise<CarFeatureModel> = (
    uriTemplate: string,
    featureId: string
  ) => {
    const uri = parseTemplate(uriTemplate).expand({
      [this.FEATURE_ID_TEMPLATE_KEY]: featureId,
    });
    return this.get<CarFeatureModel>(uri);
  };
}

export default CarFeaturesApi;
