import Service from "@/services/Service.ts";
import CarModel from "@/models/CarModel.ts";
import CarApi from "@/api/CarApi.ts";
import UserPrivateModel from "@/models/UserPrivateModel";
import { CreateCarFormSchemaType } from "@/forms/CreateCarForm";
import CarReviewModel from "@/models/CarReviewModel";
import PaginationModel from "@/models/PaginationModel";

class CarService extends Service {
  public static getCarsByUser = async (
    uriTemplate: string,
    user: UserPrivateModel
  ): Promise<CarModel[]> => {
    return await this.resolveQuery(CarApi.getCarsByUser(uriTemplate, user));
  };

  public static getCarByUri = async (uri: string): Promise<CarModel> => {
    return await this.resolveQuery(CarApi.getCarByUri(uri));
  };

  public static getCarById = async (
    uriTemplate: string,
    id: string
  ): Promise<CarModel> => {
    return await this.resolveQuery(CarApi.getCarById(uriTemplate, id));
  };

  public static getCarReviews = async (
    uri: string
  ): Promise<PaginationModel<CarReviewModel>> => {
    return await this.resolveQuery(CarApi.getCarReviews(uri));
  };

  public static createCar = async (
    uriTemplate: string,
    data: CreateCarFormSchemaType
  ): Promise<void> => {
    const { carUri } = await this.resolveQuery(
      CarApi.createCar(uriTemplate, data)
    );
    if (data.image) {
      const car = await this.resolveQuery(CarApi.getCarByUri(carUri));
      await this.resolveQuery(CarApi.updateCarImage(car.imageUri, data.image));
    }
  };
}

export default CarService;
