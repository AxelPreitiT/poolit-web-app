import { ReviewFormSchemaType } from "@/forms/ReviewForm";
import AxiosApi from "./axios/AxiosApi";
import { AxiosPromise } from "axios";

class PassengerReviewsApi extends AxiosApi {
  private static readonly PASSENGER_REVIEWS_CONTENT_TYPE =
    "application/vnd.passenger-review.v1+json";

  public static createReview: (
    uri: string,
    tripId: number,
    passengerId: number,
    data: ReviewFormSchemaType
  ) => AxiosPromise<void> = (
    uri: string,
    tripId: number,
    passengerId: number,
    { comment, option, rating }
  ) => {
    const body = {
      tripId,
      passengerId,
      comment,
      option,
      rating,
    };
    return this.post(uri, body, {
      headers: {
        "Content-Type": this.PASSENGER_REVIEWS_CONTENT_TYPE,
      },
    });
  };
}

export default PassengerReviewsApi;
