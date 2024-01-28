import AxiosApi from "./axios/AxiosApi";

class PassengerReviewsApi extends AxiosApi {
  private static readonly PASSENGER_REVIEWS_CONTENT_TYPE =
    "application/vnd.passenger-review.v1+json";
}

export default PassengerReviewsApi;
