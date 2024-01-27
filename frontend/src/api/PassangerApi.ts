import AxiosApi from "@/api/axios/AxiosApi.ts";
import {AxiosPromise, AxiosResponse} from "axios";
import PassangerModel from "@/models/PassangerModel.ts";
import PaginationModel from "@/models/PaginationModel.ts";
import {parseTemplate} from "url-template";
import PassangerStatus from "@/enums/PassangerStatus.ts";

type AcceptRejecPassangerBody = {
    passengerState: string;
};

class PassangerApi extends AxiosApi{
    public static getPassangerByUri: (uri: string) => AxiosPromise<PaginationModel<PassangerModel>> =
        (uri: string) => {
            const newUri = parseTemplate(uri).expand({});
            return this.get<PassangerModel[]>(newUri, {
                headers: {
                },
            }).then((response: AxiosResponse<PassangerModel[]>) => {
                const passangers = response.data;

                const first = response.headers.link?.match(/<([^>]*)>; rel="first"/)?.[1];
                const prev = response.headers.link?.match(/<([^>]*)>; rel="prev"/)?.[1];
                const next = response.headers.link?.match(/<([^>]*)>; rel="next"/)?.[1];
                const last = response.headers.link?.match(/<([^>]*)>; rel="last"/)?.[1];
                const total = response.headers['x-total-pages']
                const newResponse: AxiosResponse<PaginationModel<PassangerModel>> = {
                    ...response,
                    data:{
                        first: first,
                        prev: prev,
                        next: next,
                        last: last,
                        totalPages: total,
                        data: passangers
                    }
                };
                return newResponse;
            });
        };

    public static getPassangers: (uri: string) => AxiosPromise<PassangerModel[]> =
        (uri: string) => {
            return this.get<PassangerModel[]>(uri, {
                headers: {
                },
            });
        };

    public static postAcceptPassangerByUri: (uri: string) => AxiosPromise<PassangerModel> =
        (uri: string) => {
            return this.post<AcceptRejecPassangerBody, PassangerModel>(
                uri,
                { passengerState: PassangerStatus.ACCEPTED as string }
                // O simplemente { passengerState: "ACCEPTED" } si el tipo de "ACCEPTED" en tu enum es cadena
            );
        };

    public static getPassangerRole: (uri: string) => AxiosPromise<PassangerModel> =
        (uri: string) => {
            return this.get<PassangerModel>(uri, {
                headers: {
                },
            });
        };
}

export default PassangerApi;
