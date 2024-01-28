import AxiosApi from "@/api/axios/AxiosApi.ts";
import { AxiosPromise } from "axios";
import CarModel from "@/models/CarModel.ts";
import UserPrivateModel from "@/models/UserPrivateModel";
import { CreateCarFormSchemaType } from "@/forms/CreateCarForm";
import { parseTemplate } from "url-template";
import CreateCarModel from "@/models/CreateCarModel";
import CarReviewModel from "@/models/CarReviewModel";
import PaginationModel from "@/models/PaginationModel";
import { EditCarFormSchemaType } from "@/forms/EditCarForm";

class CarApi extends AxiosApi {
  private static readonly CAR_ID_URI_KEY = "carId";
  private static readonly CAR_CONTENT_TYPE = "application/vnd.car.v1+json";

  public static getCarsByUser: (
    uriTemplate: string,
    user: UserPrivateModel
  ) => AxiosPromise<CarModel[]> = (
    uriTemplate: string,
    user: UserPrivateModel
  ) => {
    const uri = parseTemplate(uriTemplate).expand({});
    const searchParams = new URLSearchParams({
      fromUser: user.userId.toString(),
    }).toString();
    // Todo: Concat url
    return this.get<CarModel[]>(`${uri}?${searchParams}`);
  };

  public static getCarByUri: (uri: string) => AxiosPromise<CarModel> = (
    uri: string
  ) => {
    return this.get<CarModel>(uri);
  };

  public static getCarById: (
    uriTemplate: string,
    id: string
  ) => AxiosPromise<CarModel> = (uriTemplate: string, id: string) => {
    const uri = parseTemplate(uriTemplate).expand({
      [this.CAR_ID_URI_KEY]: id,
    });
    return this.getCarByUri(uri);
  };

  public static getCarReviews: (
    uri: string
  ) => AxiosPromise<PaginationModel<CarReviewModel>> = (uri: string) => {
    return this.get<CarReviewModel[]>(uri).then(this.getPaginationModel);
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
          "Content-Type": this.CAR_CONTENT_TYPE,
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

  public static updateCar: (
    carUri: string,
    data: EditCarFormSchemaType
  ) => AxiosPromise<void> = (
    carUri: string,
    { car_description, seats, car_features = [] }
  ) => {
    return this.put(
      carUri,
      {
        carInfo: car_description,
        seats: seats,
        features: car_features,
      },
      {
        headers: {
          "Content-Type": this.CAR_CONTENT_TYPE,
        },
      }
    );
  };
}

export default CarApi;
