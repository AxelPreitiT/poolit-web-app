import CarFeaturesApi from "@/api/CarFeaturesApi";
import Service from "./Service";
import CarFeatureModel from "@/models/CarFeatureModel";

class CarFeaturesService extends Service {
  public static getCarFeatures = async (
    uriTemplate: string
  ): Promise<CarFeatureModel[]> => {
    return await this.resolveQuery(CarFeaturesApi.getCarFeatures(uriTemplate));
  };
}

export default CarFeaturesService;
