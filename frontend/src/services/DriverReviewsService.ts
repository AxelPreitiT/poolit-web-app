import DriverReviewsOptionModel from "@/models/DriverReviewsOptionModel";
import Service from "./Service";
import { getDriverReviewOptionsByRating } from "@/enums/DriverReviewsOptions";

class DriverReviewsService extends Service {
  public static getDriverReviewOptionsByRating = async (
    rating: number
  ): Promise<DriverReviewsOptionModel[]> => {
    return getDriverReviewOptionsByRating(rating);
  };
}

export default DriverReviewsService;
