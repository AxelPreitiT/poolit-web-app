import AxiosApi from "@/api/axios/AxiosApi.ts";
import {AxiosPromise} from "axios";
import UserPublicModel from "@/models/UserPublicModel.ts";

class PassangerApi extends AxiosApi{
    public static getPassangerByUri: (uri: string) => AxiosPromise<UserPublicModel[]> =
        (uri: string) => {
            return this.get<UserPublicModel[]>(uri, {
                headers: {
                },
            });
        };
}

export default PassangerApi;
