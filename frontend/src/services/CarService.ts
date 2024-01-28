import Service from "@/services/Service.ts";
import CarModel from "@/models/CarModel.ts";
import CarApi from "@/api/CarApi.ts";
import UserPrivateModel from "@/models/UserPrivateModel";
import { CreateCarFormSchemaType } from "@/forms/CreateCarForm";

class CarService extends Service {
  public static getCarsByUser = async (
    user: UserPrivateModel
  ): Promise<CarModel[]> => {
    return await this.resolveQuery(CarApi.getCarsByUser(user));
  };

  public static getCarById = async (uri: string): Promise<CarModel> => {
    return await this.resolveQuery(CarApi.getCarById(uri));
  };

  public static createCar = async (
    uriTemplate: string,
    data: CreateCarFormSchemaType
  ): Promise<void> => {
    await this.resolveQuery(CarApi.createCar(uriTemplate, data));
  };
}

export default CarService;
