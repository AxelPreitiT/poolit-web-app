import AxiosApi from "@/api/axios/AxiosApi.ts";
import {AxiosPromise} from "axios";

class TripsApi extends AxiosApi{
    public static getTripById: (uri: string) => AxiosPromise<TripModel> =
        (uri: string) => {
            return this.get<TripModel>(uri, {
                headers: {
                },
            });
        };

    public static getTripsByUser: (uri: string) => AxiosPromise<TripModel[]> =
        (uri: string) => {
            return this.get<TripModel[]>(uri, {
                headers: {
                },
            });
        };
}

export default TripsApi;
