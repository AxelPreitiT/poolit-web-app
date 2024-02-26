import PassengerReviewsOptionModel from "@/models/PassengerReviewsOptionModel";
import Service from "./Service";
import { getPassengerReviewOptionsByRating } from "@/enums/PassengerReviewsOptions";
import { ReviewFormSchemaType } from "@/forms/ReviewForm";
import PassengerReviewsApi from "@/api/PassengerReviewsApi";

class PassengerReviewsService extends Service {
  public static getPassengerReviewOptionsByRating = async (
    rating: number
  ): Promise<PassengerReviewsOptionModel[]> => {
    return getPassengerReviewOptionsByRating(rating);
  };

  public static createReview = async (
    uriTemplate: string,
    tripId: number,
    passengerId: number,
    data: ReviewFormSchemaType
  ): Promise<void> => {
    await this.resolveQuery(
      PassengerReviewsApi.createReview(uriTemplate, tripId, passengerId, data)
    );
  };
}

export default PassengerReviewsService;
