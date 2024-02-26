import DriverReviewsOptionModel from "@/models/DriverReviewsOptionModel";
import Service from "./Service";
import { getDriverReviewOptionsByRating } from "@/enums/DriverReviewsOptions";
import DriverReviewsApi from "@/api/DriverReviewsApi";
import { ReviewFormSchemaType } from "@/forms/ReviewForm";

class DriverReviewsService extends Service {
  public static getDriverReviewOptionsByRating = async (
    rating: number
  ): Promise<DriverReviewsOptionModel[]> => {
    return getDriverReviewOptionsByRating(rating);
  };

  public static createReview = async (
    uriTemplate: string,
    tripId: number,
    driverId: number,
    data: ReviewFormSchemaType
  ): Promise<void> => {
    await this.resolveQuery(
      DriverReviewsApi.createReview(uriTemplate, tripId, driverId, data)
    );
  };
}

export default DriverReviewsService;
