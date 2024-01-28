import AxiosApi from "./axios/AxiosApi";

class DriverReviewsApi extends AxiosApi {
  private static readonly DRIVER_REVIEWS_CONTENT_TYPE =
    "application/vnd.driver-review.v1+json";
}

export default DriverReviewsApi;
