import CarBrandModel from "@/models/CarBrandModel";
import Service from "./Service";
import CarBrandsApi from "@/api/CarBrandsApi";

class CarBrandsService extends Service {
  public static getCarBrands: (
    uriTemplate: string
  ) => Promise<CarBrandModel[]> = async (uriTemplate: string) => {
    return await this.resolveQuery(CarBrandsApi.getCarBrands(uriTemplate));
  };

  public static getCarBrandById: (uri: string) => Promise<CarBrandModel> =
    async (uri: string) => {
      return await this.resolveQuery(CarBrandsApi.getCarBrandByUri(uri));
    };
}

export default CarBrandsService;
