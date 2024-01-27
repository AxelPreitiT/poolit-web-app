import CarBrandModel from "@/models/CarBrandModel";
import Service from "./Service";
import CarBrandsApi from "@/api/CarBrandsApi";

class CarBrandsService extends Service {
  public static getCarBrands: (
    uriTemplate: string
  ) => Promise<CarBrandModel[]> = async (uriTemplate: string) => {
    return await this.resolveQuery(CarBrandsApi.getCarBrands(uriTemplate));
  };

  public static getCarBrandById: (
    uriTemplate: string,
    brandId: string
  ) => Promise<CarBrandModel> = async (
    uriTemplate: string,
    brandId: string
  ) => {
    return await this.resolveQuery(
      CarBrandsApi.getCarBrandById(uriTemplate, brandId)
    );
  };
}

export default CarBrandsService;
