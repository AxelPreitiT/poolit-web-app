import Service from "@/services/Service.ts";
import ReviewsApi from "@/api/ReviewsApi.ts";

class ReviewsService extends Service {

    public static getReviewsByUri = async (uri : string): Promise<ReviewModel[]> => {
        return await this.resolveQuery(ReviewsApi.getReviewsByUser(uri));
    };

}

export default ReviewsService;
