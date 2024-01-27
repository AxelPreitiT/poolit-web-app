import AxiosApi from "@/api/axios/AxiosApi.ts";
import { AxiosPromise } from "axios";
import CarModel from "@/models/CarModel.ts";
import UserPrivateModel from "@/models/UserPrivateModel";
import { CreateCarFormSchemaType } from "@/forms/CreateCarForm";
import { parseTemplate } from "url-template";
import CreateCarModel from "@/models/CreateCarModel";

class CarApi extends AxiosApi {
  public static getCarsByUser: (
    user: UserPrivateModel
  ) => AxiosPromise<CarModel[]> = (user: UserPrivateModel) => {
    return this.get<CarModel[]>(user.carsUri);
  };

  public static getCarByUri: (uri: string) => AxiosPromise<CarModel> = (
    uri: string
  ) => {
    return this.get<CarModel>(uri, {
      headers: {},
    });
  };

  public static createCar: (
    uriTemplate: string,
    data: CreateCarFormSchemaType
  ) => AxiosPromise<CreateCarModel> = async (
    uriTemplate: string,
    { car_plate, car_brand, car_description, seats, car_features = [] }
  ) => {
    const uri = parseTemplate(uriTemplate).expand({});
    return this.post(
      uri,
      {
        plate: car_plate,
        carBrand: car_brand,
        carInfo: car_description,
        seats: seats,
        features: car_features,
      },
      {
        headers: {
          "Content-Type": "application/vnd.car.v1+json",
        },
      }
    ).then((response) => {
      return {
        ...response,
        data: {
          carUri: response.headers.location,
        },
      };
    });
  };

  public static updateCarImage: (
    carUri: string,
    image: File
  ) => AxiosPromise<void> = (imageUri: string, image: File) => {
    const formData = new FormData();
    formData.append("image", image);
    return this.put(imageUri, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  };
}

export default CarApi;
