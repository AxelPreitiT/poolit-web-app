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

  public static getCarByUri = async (uri: string): Promise<CarModel> => {
    return await this.resolveQuery(CarApi.getCarByUri(uri));
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
