import AxiosApi from "@/api/axios/AxiosApi.ts";
import {AxiosPromise} from "axios";

class ReviewsApi extends AxiosApi{
    public static getReviewsByUser: (uri: string) => AxiosPromise<ReviewModel[]> =
        (uri: string) => {
            return this.get<ReviewModel[]>(uri, {
                headers: {
                },
            });
        };
}

export default ReviewsApi;
