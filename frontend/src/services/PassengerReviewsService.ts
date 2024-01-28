import PassengerReviewsOptionModel from "@/models/PassengerReviewsOptionModel";
import Service from "./Service";
import { getPassengerReviewOptionsByRating } from "@/enums/PassengerReviewsOptions";

class PassengerReviewsService extends Service {
  public static getPassengerReviewOptionsByRating = async (
    rating: number
  ): Promise<PassengerReviewsOptionModel[]> => {
    return getPassengerReviewOptionsByRating(rating);
  };
}

export default PassengerReviewsService;
