import AxiosApi from "@/api/axios/AxiosApi.ts";
import {AxiosPromise} from "axios";
import CarModel from "@/models/CarModel.ts";

class CarApi extends AxiosApi{
    public static getCarsByUser: (uri: string) => AxiosPromise<CarModel[]> =
        (uri: string) => {
            return this.get<CarModel[]>(uri, {
                headers: {
                },
            });
        };

    public static getCarById: (uri: string) => AxiosPromise<CarModel> =
        (uri: string) => {
            return this.get<CarModel>(uri, {
                headers: {
                },
            });
        };
}

export default CarApi;
