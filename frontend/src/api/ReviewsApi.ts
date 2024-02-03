import AxiosApi from "@/api/axios/AxiosApi.ts";
import { AxiosPromise, AxiosResponse } from "axios";
import PaginationModel from "@/models/PaginationModel.tsx";
import ReviewModel from "@/models/ReviewModel";

class ReviewsApi extends AxiosApi {
  private static readonly DRIVER_REVIEW_LIST_TYPE =
    "application/vnd.driver-review.list.v1+json";
  private static readonly PASSENGER_REVIEW_LIST_TYPE =
    "application/vnd.passenger-review.list.v1+json";

  public static getReviewsByUser: (
    uri: string
  ) => AxiosPromise<PaginationModel<ReviewModel>> = (uri: string) => {
    return this.get<ReviewModel[]>(uri, {
      headers: {
        Accept: [
          ReviewsApi.DRIVER_REVIEW_LIST_TYPE,
          ReviewsApi.PASSENGER_REVIEW_LIST_TYPE,
        ],
      },
    }).then((response: AxiosResponse<ReviewModel[]>) => {
      const reviews = response.data;

      const first = response.headers.link?.match(/<([^>]*)>; rel="first"/)?.[1];
      const prev = response.headers.link?.match(/<([^>]*)>; rel="prev"/)?.[1];
      const next = response.headers.link?.match(/<([^>]*)>; rel="next"/)?.[1];
      const last = response.headers.link?.match(/<([^>]*)>; rel="last"/)?.[1];
      const total = response.headers["x-total-pages"];
      const newResponse: AxiosResponse<PaginationModel<ReviewModel>> = {
        ...response,
        data: {
          first: first,
          prev: prev,
          next: next,
          last: last,
          totalPages: total,
          data: reviews,
        },
      };
      return newResponse;
    });
  };
}

export default ReviewsApi;
