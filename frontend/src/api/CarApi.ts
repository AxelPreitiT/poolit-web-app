import AxiosApi from "@/api/axios/AxiosApi.ts";
import { AxiosPromise } from "axios";
import CarModel from "@/models/CarModel.ts";
import UserPrivateModel from "@/models/UserPrivateModel";

class CarApi extends AxiosApi {
  public static getCarsByUser: (
    user: UserPrivateModel
  ) => AxiosPromise<CarModel[]> = (user: UserPrivateModel) => {
    return this.get<CarModel[]>(user.carsUri);
  };

  public static getCarById: (uri: string) => AxiosPromise<CarModel> = (
    uri: string
  ) => {
    return this.get<CarModel>(uri, {
      headers: {},
    });
  };
}

export default CarApi;
