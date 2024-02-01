import CarFeatureModel from "@/models/CarFeatureModel";
import AxiosApi from "./axios/AxiosApi";
import { AxiosPromise } from "axios";
import { parseTemplate } from "url-template";

class CarFeaturesApi extends AxiosApi {
  private static readonly CAR_FEATURE_TYPE =
      "application/vnd.car-feature.v1+json";
  private static readonly CAR_FEATURE_LIST_TYPE =
      "application/vnd.car-feature.list.v1+json"
  public static getCarFeatures: (
    uri: string
  ) => AxiosPromise<CarFeatureModel[]> = (uriTemplate: string) => {
    const uri = parseTemplate(uriTemplate).expand({});
    return this.get<CarFeatureModel[]>(uri, {
          headers:{
            Accept:CarFeaturesApi.CAR_FEATURE_LIST_TYPE,
          }
        });
  };

  public static getCarFeatureByUri: (
    featureUri: string
  ) => AxiosPromise<CarFeatureModel> = (featureUri: string) => {
    return this.get<CarFeatureModel>(featureUri,{
      headers:{
        Accept:CarFeaturesApi.CAR_FEATURE_TYPE,
      }
    });
  };
}

export default CarFeaturesApi;
