import { ReviewFormSchemaType } from "@/forms/ReviewForm";
import AxiosApi from "./axios/AxiosApi";
import { AxiosPromise } from "axios";
import { parseTemplate } from "url-template";

class DriverReviewsApi extends AxiosApi {
  private static readonly DRIVER_REVIEWS_CONTENT_TYPE =
    "application/vnd.driver-review.v1+json";

  public static createReview: (
    uriTemplate: string,
    tripId: number,
    driverId: number,
    data: ReviewFormSchemaType
  ) => AxiosPromise<void> = (
    uriTemplate: string,
    tripId: number,
    driverId: number,
    { comment, option, rating }
  ) => {
    const uri = parseTemplate(uriTemplate).expand({});
    const body = {
      tripId,
      driverId,
      comment,
      option,
      rating,
    };
    return this.post(uri, body, {
      headers: {
        "Content-Type": this.DRIVER_REVIEWS_CONTENT_TYPE,
      },
    });
  };
}

export default DriverReviewsApi;
