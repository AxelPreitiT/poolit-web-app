import AxiosApi from "@/api/axios/AxiosApi.ts";
import {AxiosPromise} from "axios";
import PassangerModel from "@/models/PassangerModel.ts";

class PassangerApi extends AxiosApi{
    public static getPassangerByUri: (uri: string) => AxiosPromise<PassangerModel[]> =
        (uri: string) => {
            return this.get<PassangerModel[]>(uri, {
                headers: {
                },
            });
        };
}

export default PassangerApi;
