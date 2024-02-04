import Service from "@/services/Service.ts";
import ReviewsApi from "@/api/ReviewsApi.ts";
import PaginationModel from "@/models/PaginationModel.tsx";
import ReviewModel from "@/models/ReviewModel";

class ReviewsService extends Service {
  public static getReviewsByUri = async (
    uri: string
  ): Promise<PaginationModel<ReviewModel>> => {
    return await this.resolveQuery(ReviewsApi.getReviewsByUser(uri));
  };
}

export default ReviewsService;
