import CarFeatureModel from "@/models/CarFeatureModel";
import AxiosApi from "./axios/AxiosApi";
import { AxiosPromise } from "axios";
import { parseTemplate } from "url-template";

class CarFeaturesApi extends AxiosApi {
  public static getCarFeatures: (
    uri: string
  ) => AxiosPromise<CarFeatureModel[]> = (uriTemplate: string) => {
    const uri = parseTemplate(uriTemplate).expand({});
    return this.get<CarFeatureModel[]>(uri);
  };

  public static getCarFeatureByUri: (
    featureUri: string
  ) => AxiosPromise<CarFeatureModel> = (featureUri: string) => {
    return this.get<CarFeatureModel>(featureUri);
  };
}

export default CarFeaturesApi;
